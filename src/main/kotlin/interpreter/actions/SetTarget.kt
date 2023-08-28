package emeraldwater.infernity.dev.interpreter.actions

import emeraldwater.infernity.dev.interpreter.Argument
import emeraldwater.infernity.dev.interpreter.Interpreter
import emeraldwater.infernity.dev.interpreter.SetTarget
import emeraldwater.infernity.dev.interpreter.SetTargetBlock
import emeraldwater.infernity.dev.playerModes
import emeraldwater.infernity.dev.players
import emeraldwater.infernity.dev.plots.PlotMode
import emeraldwater.infernity.dev.plots.PlotState

/**
 * Represents interpreting an set target.
 * @param block The set target block
 * @param localVariables The local variables of this scope
 * @param blockVariables The block variables unique to this block
 */
fun Interpreter.setTarget(
    block: SetTargetBlock,
    localVariables: MutableMap<String, Argument>,
    blockVariables: MutableMap<String, Argument>
) {
    when (block.action) {
        SetTarget.DEFAULT -> {
            if (defaultPlayer != null) {
                playerTargets = listOf(defaultPlayer)
            } else {
                playerTargets = listOf()
            }
        }

        SetTarget.ALL_PLAYERS -> {
            playerTargets = players.filter {
                playerModes[it.username] == PlotState(plotID, PlotMode.PLAY)
            }
        }
    }
}