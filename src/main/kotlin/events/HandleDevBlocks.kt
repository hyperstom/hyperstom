package emeraldwater.infernity.dev.events

import net.minestom.server.coordinate.Pos
import net.minestom.server.event.player.PlayerBlockBreakEvent
import net.minestom.server.event.player.PlayerBlockPlaceEvent
import net.minestom.server.instance.block.Block
import net.minestom.server.tag.Tag
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTType

fun placeDevBlock(event: PlayerBlockPlaceEvent) {
    val x = event.blockPosition.blockX()
    val y = event.blockPosition.blockY()
    val z = event.blockPosition.blockZ()

    if(x !in -1000 downTo -1020) return
    if(z !in 0..50) return
    if(event.player.instance.getBlock(Pos(x.toDouble(), y-1.0, z.toDouble())) != Block.WHITE_STAINED_GLASS) return

    when(event.block) {
        Block.DIAMOND_BLOCK -> {
            event.player.instance.setBlock(x, y, z, Block.DIAMOND_BLOCK.withTag(Tag.String("codeBlockType"), "block"))
            event.player.instance.setBlock(x, y, z+1, Block.STONE)
            event.player.instance.setBlock(x-1, y, z, signWithLine("PLAYER EVENT"))
        }
        Block.COBBLESTONE -> {
            event.player.instance.setBlock(x, y, z, Block.COBBLESTONE.withTag(Tag.String("codeBlockType"), "block"))
            event.player.instance.setBlock(x, y, z+1, Block.STONE)
            event.player.instance.setBlock(x, y+1, z, Block.BARREL)
            event.player.instance.setBlock(x-1, y, z, signWithLine("PLAYER ACTION"))
        }
        else -> {
            event.isCancelled = true
        }
    }
}

fun breakDevBlock(event: PlayerBlockBreakEvent) {
    val x = event.blockPosition.blockX()
    val y = event.blockPosition.blockY()
    val z = event.blockPosition.blockZ()

    if(x !in -1000 downTo -1020) return
    if(z !in 0..50) return
    if(event.player.instance.getBlock(Pos(x.toDouble(), y-1.0, z.toDouble())) != Block.WHITE_STAINED_GLASS) return
    when(event.block.getTag(Tag.String("codeBlockType"))) {
        "block" -> {
            event.player.instance.setBlock(x, y, z, Block.AIR)
            event.player.instance.setBlock(x, y, z+1, Block.AIR)
            event.player.instance.setBlock(x, y+1, z, Block.AIR)
            event.player.instance.setBlock(x-1, y, z, Block.AIR)
        }
        "sign" -> {
            event.player.instance.setBlock(x+1, y, z, Block.AIR)
            event.player.instance.setBlock(x+1, y, z+1, Block.AIR)
            event.player.instance.setBlock(x+1, y+1, z, Block.AIR)
            event.player.instance.setBlock(x, y, z, Block.AIR)
        }
        else -> {}
    }
}
fun signWithLine(line: String): Block {
    return Block.OAK_WALL_SIGN
        .withNbt(NBT.Compound(mapOf(
            "is_waxed" to NBT.Boolean(true),
            "front_text" to NBT.Compound(mapOf(
                "color" to NBT.String("black"),
                "has_glowing_text" to NBT.Boolean(false),
                "messages" to NBT.List(NBTType.TAG_String, NBT.String("""{"text":"$line"}"""), NBT.String("""{"text":""}"""), NBT.String("""{"text":""}"""), NBT.String("""{"text":""}"""))
            )),
            "back_text" to NBT.Compound(mapOf(
                "color" to NBT.String("black"),
                "has_glowing_text" to NBT.Boolean(false),
                "messages" to NBT.List(NBTType.TAG_String, NBT.String("""{"text":""}"""), NBT.String("""{"text":""}"""), NBT.String("""{"text":""}"""), NBT.String("""{"text":""}"""))
            ))
        )))
        .withProperty("facing", "west")
        .withTag(Tag.String("codeBlockType"), "sign")
}