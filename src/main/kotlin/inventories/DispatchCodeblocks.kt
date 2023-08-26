import emeraldwater.infernity.dev.inventories.displayPlayerActionMenu
import emeraldwater.infernity.dev.inventories.displayPlayerEventMenu
import emeraldwater.infernity.dev.inventories.displaySetVariableMenu
import net.minestom.server.entity.Player
import net.minestom.server.tag.Tag

fun dispatchInventory(player: Player) {
    val target = player.getTargetBlockPosition(100) ?: return
    val block = player.instance.getBlock(target)
    if(block.getTag(Tag.String("codeBlockType")) == "sign") {
        if(block.getTag(Tag.String("line1")) == "PLAYER EVENT") {
            displayPlayerEventMenu(player)
        }
        if(block.getTag(Tag.String("line1")) == "PLAYER ACTION") {
            displayPlayerActionMenu(player)
        }
        if(block.getTag(Tag.String("line1")) == "SET VARIABLE") {
            displaySetVariableMenu(player)
        }
    }
}