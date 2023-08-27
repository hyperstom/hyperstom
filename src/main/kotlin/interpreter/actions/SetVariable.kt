package emeraldwater.infernity.dev.interpreter.actions

import emeraldwater.infernity.dev.interpreter.Argument
import emeraldwater.infernity.dev.interpreter.SetVariable
import emeraldwater.infernity.dev.interpreter.SetVariableBlock
import emeraldwater.infernity.dev.interpreter.Interpreter

fun Interpreter.setVar(block: SetVariableBlock) {
    when(block.action) {
        SetVariable.SET_EQUALS -> {
            val variable = block.args[0]
            val value = block.args[1]
            if(variable is Argument.Variable) {

            }
        }
    }
}