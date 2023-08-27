package emeraldwater.infernity.dev.interpreter.actions

import emeraldwater.infernity.dev.interpreter.Argument
import emeraldwater.infernity.dev.interpreter.SetVariable
import emeraldwater.infernity.dev.interpreter.SetVariableBlock
import emeraldwater.infernity.dev.interpreter.Interpreter

/**
 * Represents interpreting an set variable.
 * @param block The player action block
 * @param localVariables The local variables of this scope
 * @param blockVariables The block variables unique to this block
 */
fun Interpreter.setVar(block: SetVariableBlock, localVariables: MutableMap<String, Argument>, blockVariables: MutableMap<String, Argument>) {
    when(block.action) {
        SetVariable.SET_EQUALS -> {
            val variable = block.args[0]
            val value = block.args[1]
            if(variable is Argument.Variable) {

            }
        }
    }
}