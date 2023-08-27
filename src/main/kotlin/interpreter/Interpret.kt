package emeraldwater.infernity.dev.interpreter

import emeraldwater.infernity.dev.interpreter.actions.ifPlayer
import emeraldwater.infernity.dev.interpreter.actions.playerAction
import emeraldwater.infernity.dev.playerInterpreter
import emeraldwater.infernity.dev.playerModes
import emeraldwater.infernity.dev.plots.PlotMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minestom.server.entity.Player

fun Player.interpret(event: Event) = run {
    val player = this
    if(playerModes[player.username]?.mode == PlotMode.PLAY) {
        playerInterpreter[player.username]?.interpreterScope?.launch {
            playerInterpreter[player.username]?.interpretEvent(event)
        }
    }

}

class Interpreter(
    val containers: List<ActionContainer>,
    var playerTarget: Player,
) {
    val interpreterScope = CoroutineScope(Dispatchers.Default)
    val functions: Map<String, ActionContainer>
    val localFunctions: Map<String, ActionContainer> = mutableMapOf()

    val globalVariables: Map<String, Argument> = mutableMapOf()

    init {
        functions = mutableMapOf()
        for(container in containers) {
            if(container is FunctionDefinitionBlock) {
                val arg = container.args[0]
                if(arg is Argument.FunctionReference) {
                    functions[arg.value] = container
                }
            }
        }
        playerTarget.sendMessage("containers: $containers")
    }

    /**
     * Interpret an individual container.
     * @param event The event to parse.1
     */
    fun interpretEvent(event: Event) {
        val localVariables: MutableMap<String, Argument> = mutableMapOf()
        for(container in containers) {
            if(container is PlayerEventBlock && event is PlayerEvent) {
                if(container.action == event) {
                    interpretContainer(container, localVariables)
                }
            }
        }
    }

    /**
     * Interpret an individual container.
     * @param container The container to parse.
     * @param vars Local variables in this scope
     */
    fun interpretContainer(container: ActionContainer, vars: MutableMap<String, Argument>) {
        // Create the new scope for the container
        val containerScope: MutableMap<String, Argument> = mutableMapOf()
        for(action in container.actions) {
            interpretBlock(action, vars, containerScope)
        }
    }

    /**
     * Interpret an individual block.
     * @param block The block to parse.
     * @param localVariables Local variables in this scope
     * @param blockVariables The variables unique to this container
     */
    fun interpretBlock(block: Action, localVariables: MutableMap<String, Argument>, blockVariables: MutableMap<String, Argument>) {
        try {
            if(block is PlayerActionBlock) {
                playerAction(block, localVariables, blockVariables)
            }
            if(block is IfPlayerBlock) {
                ifPlayer(block, localVariables, blockVariables)
            }

        } catch(e: Exception) {
            when(e) {
                is IndexOutOfBoundsException -> {}
                else -> {
                    println("An unknown exception occured during interpreting:")
                    e.printStackTrace()
                }
            }
        }

    }


}