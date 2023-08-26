package emeraldwater.infernity.dev.interpreter

interface Action {
    val args: List<Argument>
}

interface Header {
    val actions: List<Action>
}

class PlayerEventBlock(
    val action: PlayerEvent,
    override val actions: List<Action>
) : Header {
    override fun toString(): String {
        return """PlayerEvent: ${this.action.signText} { ${this.actions} }"""
    }
}

class PlayerActionBlock(
    val action: PlayerAction,
    override val args: List<Argument>
) : Action {
    override fun toString(): String {
        return """PlayerAction: ${this.action.signText} $args;"""
    }
}

class SetVariableBlock(
    val action: SetVariable,
    override val args: List<Argument>
) : Action {
    override fun toString(): String {
        return """SetVariable: ${this.action.signText} $args;"""
    }
}
