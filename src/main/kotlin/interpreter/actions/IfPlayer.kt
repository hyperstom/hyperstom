package emeraldwater.infernity.dev.interpreter.actions

import emeraldwater.infernity.dev.interpreter.*
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.TitlePart
import net.minestom.server.coordinate.Point
import net.minestom.server.instance.block.Block
import kotlin.time.Duration

/**
 * Represents interpreting an if player.
 * @param block The player action block
 * @param localVariables The local variables of this scope
 * @param blockVariables The block variables unique to this block
 */
fun Interpreter.ifPlayer(block: IfPlayerBlock, localVariables: MutableMap<String, Argument>, blockVariables: MutableMap<String, Argument>) {
    when(block.action) {
        IfPlayer.STANDING_ON -> {
            var trues = 0
            playerTargets.forEach { playerTarget ->
                var blockPos = playerTarget.position.add(0.0, -0.06, 0.0)
                val material = playerTarget.instance.getBlock(playerTarget.position.add(0.0, -0.5, 0.0)).registry().material()

                for(arg in block.args) {
                    if(arg is Argument.Item) {
                        if(arg.value.material() == material) {
                            trues++
                            continue
                        }
                    }
                }
            }
            if(trues == playerTargets.size) {
                interpretContainer(block, localVariables, blockVariables)
            }
        }
    }
}