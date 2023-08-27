package emeraldwater.infernity.dev.interpreter

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
        for(container in containers) {
            if(container is PlayerEventBlock && event is PlayerEvent) {
                if(container.action == event) {
                    interpretContainer(container)
                }
            }
        }
    }

    fun interpretContainer(container: ActionContainer) {
        for(action in container.actions) {
            interpretBlock(action)
        }
    }

    fun interpretBlock(block: Action) {
        if(block is PlayerActionBlock) {
            try {
                when(block.action) {
                    PlayerAction.SEND_MESSAGE -> {
                        val text = block.args[0]
                        if(text is Argument.RichText) {
                            playerTarget.sendMessage(text.value)
                        }
                    }
                    PlayerAction.LAUNCH_UP -> {
                        val num = block.args[0]
                        if(num is Argument.Number) {
                            playerTarget.velocity = playerTarget.velocity.add(0.0, num.value, 0.0)
                        }
                    }
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
}