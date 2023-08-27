package emeraldwater.infernity.dev.interpreter.actions

import emeraldwater.infernity.dev.interpreter.*
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.TitlePart
import net.minestom.server.coordinate.Point
import net.minestom.server.instance.block.Block
import kotlin.time.Duration

fun Interpreter.ifPlayer(block: IfPlayerBlock) {
    when(block.action) {
        IfPlayer.STANDING_ON -> {
            var blockPos = playerTarget.position.add(0.0, -0.06, 0.0)
            var itemStack = playerTarget.instance.getBlock(playerTarget.position.add(0.0, -0.5, 0.0)).registry()!!.material()
            if (block.args.contains(itemStack as Argument.Item)){
                interpretContainer(block)
            }
        }
    }
}