package emeraldwater.infernity.dev.interpreter

interface Action {
    val args: List<Argument>
}

class PlayerEventBlock(
    val action: PlayerEvent,
    override val args: List<Argument>
) : Action

class PlayerActionBlock(
    val action: PlayerAction,
    override val args: List<Argument>
) : Action

