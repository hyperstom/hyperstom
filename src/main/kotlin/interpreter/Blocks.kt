package emeraldwater.infernity.dev.interpreter

interface Action {
    val args: List<Argument>
}

interface ActionContainer : Action {
    val actions: List<Action>
}

data class PlayerEventBlock(
    val action: PlayerEvent,
    override val actions: List<Action>,
    override val args: List<Argument> = listOf(),
) : ActionContainer {
    override fun toString(): String {
        return """PlayerEvent ${this.action.signText}${this.args} ${this.actions}"""
    }
}

data class FunctionDefinitionBlock(
    override val actions: List<Action>,
    override val args: List<Argument> = listOf(),
) : ActionContainer {
    override fun toString(): String {
        return """Function ${this.args} ${this.actions}"""
    }
}

data class PlayerActionBlock(
    val action: PlayerAction,
    override val args: List<Argument>
) : Action {
    override fun toString(): String {
        return """PlayerAction: ${this.action.signText} $args"""
    }
}

data class SetVariableBlock(
    val action: SetVariable,
    override val args: List<Argument>
) : Action {
    override fun toString(): String {
        return """SetVariable ${this.action.signText} $args"""
    }
}

data class SetTargetBlock(
    val action: SetTarget,
    override val args: List<Argument>
) : Action {
    override fun toString(): String {
        return """SetTarget ${this.action.signText} $args"""
    }
}

data class IfPlayerBlock(
    val action: IfPlayer,
    override val actions: List<Action>,
    override val args: List<Argument>,
) : ActionContainer {
    override fun toString(): String {
        return """IfPlayer ${this.action.signText} $args ${this.actions}"""
    }
}