package emeraldwater.infernity.dev.interpreter

import emeraldwater.infernity.dev.interpreter.actions.playerAction
import emeraldwater.infernity.dev.playerInterpreter
import emeraldwater.infernity.dev.playerModes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minestom.server.entity.Player

fun Player.interpret(event: Event) = run {
    val player = this
    playerInterpreter[player.username]?.interpreterScope?.launch {
        playerInterpreter[player.username]?.interpretEvent(event)
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

    fun interpretContainer(container: ActionContainer, vars: MutableMap<String, Argument>) {
        val containerScope: MutableMap<String, Argument> = mutableMapOf()
        for(action in container.actions) {
            interpretBlock(action, vars, containerScope)
        }
    }

    fun interpretBlock(block: Action, localVariables: MutableMap<String, Argument>, blockVariables: MutableMap<String, Argument>) {
        try {
            if(block is PlayerActionBlock) {
                playerAction(block)
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