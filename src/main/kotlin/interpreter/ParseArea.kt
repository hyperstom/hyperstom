package emeraldwater.infernity.dev.interpreter

import net.minestom.server.coordinate.Point
import net.minestom.server.coordinate.Vec
import net.minestom.server.instance.Instance
import net.minestom.server.instance.block.Block
import net.minestom.server.item.ItemStack
import net.minestom.server.tag.Tag

data class ParseBlockResult(val point: Point, val action: List<Action>)

fun parseDevArea(instance: Instance): List<ActionContainer> {
    val actionContainers = mutableListOf<ActionContainer>()
    for(x in 0 downTo -20 step 3) {
        for(z in 0..0) {
            for(y in 2..255 step 5) {
                val block = instance.getBlock(x, y, z)
                if(block.name() == "minecraft:diamond_block") {
                    val event = instance.getBlock(x-1, y, z).getTag(Tag.String("line2"))
                    if(event != null && findEntryByString(event, PlayerEvent.entries) != null) {
                        val (_, actions) = parseBlock(instance, Vec(x.toDouble(), y.toDouble(), z.toDouble()))
                        actionContainers.add(PlayerEventBlock(
                            findEntryByString(event, PlayerEvent.entries)!!,
                            actions
                        ))
                    }
                }
                if(block.name() == "minecraft:lapis_block") {
                    val event = instance.getBlock(x-1, y, z).getTag(Tag.String("line2"))
                    if(event != null) {
                        val (_, actions) = parseBlock(instance, Vec(x.toDouble(), y.toDouble(), z.toDouble()))
                        actionContainers.add(FunctionDefinitionBlock(
                            actions,
                            listOf()
                        ))
                    }
                }
            }
        }
    }
    return actionContainers
}

fun parseBlock(instance: Instance, getPoint: Point): ParseBlockResult {
    var point = getPoint
    val actions = mutableListOf<Action>()
    var counter = 1
    while(true) {
        point = point.withZ(point.z() + 2.0)
        val block = instance.getBlock(point)
        val stone = instance.getBlock(point.withZ(point.z() + 1.0))
        val chest = instance.getBlock(point.withY(point.y() + 1.0))
        val sign = instance.getBlock(point.withX(point.x() - 1.0))
        if(stone == Block.PISTON.withProperty("facing", "north")) counter--
        if(stone == Block.PISTON.withProperty("facing", "south")) counter++
        if(stone == Block.AIR || (counter == 0 && stone == Block.PISTON.withProperty("facing", "north")))
            return ParseBlockResult(point, actions)
        val line2 = sign.getTag(Tag.String("line2"))

        val arguments = mutableListOf<Argument>()
        for(slot in 0..53) {
            val item = chest.getTag(Tag.ItemStack("barrel.slot$slot"))
            if(item != null && item != ItemStack.AIR) {
                when(item.getTag(Tag.String("varitem.id"))) {
                    "txt" -> arguments.add(Argument.Text(item.getTag(Tag.String("varitem.value"))))
                    "rtxt" -> arguments.add(Argument.RichText(item.getTag(Tag.Component("varitem.value"))))
                    "num" -> arguments.add(Argument.Number(item.getTag(Tag.Double("varitem.value"))))
                    "var" -> arguments.add(Argument.Variable(item.getTag(Tag.String("varitem.value"))))
                    "func" -> arguments.add(Argument.FunctionReference(item.getTag(Tag.String("varitem.value"))))
                    else -> arguments.add(Argument.Item(item))
                }
            }
        }


        if(block.name() == "minecraft:cobblestone") {
            if(line2 != null && findEntryByString(line2, PlayerAction.entries) != null) {
                actions.add(PlayerActionBlock(
                    findEntryByString(line2, PlayerAction.entries)!!,
                    arguments
                ))
            }
        }
        if(block.name() == "minecraft:iron_block") {
            if(line2 != null && findEntryByString(line2, SetVariable.entries) != null) {
                actions.add(SetVariableBlock(
                    findEntryByString(line2, SetVariable.entries)!!,
                    arguments
                ))
            }
        }
        if(block.name() == "minecraft:lapis_block") {
            val (point2, actions2) = parseBlock(instance, point)
            val func = FunctionDefinitionBlock(
                actions2,
                arguments,
            )
            actions.add(func)
            point = point2
        }
        if(block.name() == "minecraft:target_block") {
            val (point2, actions2) = parseBlock(instance, point)
            if(line2 != null && findEntryByString(line2, SetTarget.entries) != null) {
                actions.add(SetTargetBlock(
                    findEntryByString(line2, SetTarget.entries)!!,
                    actions2,
                    arguments
                ))
                point = point2
            }
        }
        if(block.name() == "minecraft:oak_planks") {
            val (point2, actions2) = parseBlock(instance, point)
            if(line2 != null && findEntryByString(line2, IfPlayer.entries) != null) {
                val func = IfPlayerBlock(
                    findEntryByString(line2, IfPlayer.entries)!!,
                    actions2,
                    arguments,
                )
                actions.add(func)
            }
            point = point2
        }
    }

}