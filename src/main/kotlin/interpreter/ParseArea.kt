package emeraldwater.infernity.dev.interpreter

import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.tag.Tag

fun parseDevArea(instance: Instance): List<Header> {
    val headers = mutableListOf<Header>()
    for(x in -0 downTo -20 step 3) {
        for(z in 0..128) {
            for(y in 2..255 step 10) {
                val block = instance.getBlock(x, y, z)
                if(x < 10 && y < 10 && z < 10 ) {
                    println("block @ $x $y $z is $block (${block.name()})")
                }
                if(block.name() == "minecraft:diamond_block") {
                    val event = instance.getBlock(x-1, y, z).getTag(Tag.String("line2"))
                    println("event: $event | playerEventFromString: ${playerEventFromString(event)}")
                    if(event != null && playerEventFromString(event) != null) {
                        val actions = parseLine(instance, Vec(x.toDouble(), y.toDouble(), z.toDouble()))
                        headers.add(PlayerEventBlock(
                            playerEventFromString(event)!!,
                            actions
                        ))
                    }
                }
            }
        }
    }
    return headers
}

fun parseLine(instance: Instance, getPoint: Point): List<Action> {
    var point = getPoint
    val actions = mutableListOf<Action>()
    while(true) {
        point = point.withZ(point.z() + 2.0)
        val block = instance.getBlock(point)
        val stone = instance.getBlock(point.withZ(point.z() + 1.0))
        val chest = instance.getBlock(point.withY(point.y() + 1.0))
        val sign = instance.getBlock(point.withX(point.x() - 1.0))
        if(stone == Block.AIR) break
        val line2 = sign.getTag(Tag.String("line2"))
        if(block.name() == "minecraft:cobblestone") {
            println("line2: $line2 | playerActionFromString: ${playerActionFromString(line2)}")
            if(line2 != null && playerActionFromString(line2) != null) {
                actions.add(PlayerActionBlock(
                    playerActionFromString(line2)!!,
                    listOf()
                ))
            }
        }
    }
    return actions

}