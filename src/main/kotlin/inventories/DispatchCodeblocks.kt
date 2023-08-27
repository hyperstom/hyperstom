import emeraldwater.infernity.dev.inventories.items.displayIfPlayerMenu
import emeraldwater.infernity.dev.inventories.items.displayPlayerActionMenu
import emeraldwater.infernity.dev.inventories.items.displayPlayerEventMenu
import emeraldwater.infernity.dev.inventories.items.displaySetVariableMenu
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
        if(block.getTag(Tag.String("line1")) == "SET TARGET") {
            displaySetTargetMenu(player)
        }
        if(block.getTag(Tag.String("line1")) == "IF PLAYER") {
            displayIfPlayerMenu(player)
        }
    }
}