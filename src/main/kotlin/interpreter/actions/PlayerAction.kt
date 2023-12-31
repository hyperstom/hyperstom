package emeraldwater.infernity.dev.interpreter.actions

import emeraldwater.infernity.dev.interpreter.Argument
import emeraldwater.infernity.dev.interpreter.Interpreter
import emeraldwater.infernity.dev.interpreter.PlayerAction
import emeraldwater.infernity.dev.interpreter.PlayerActionBlock
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.TitlePart
import kotlin.time.Duration

/**
 * Represents interpreting a player action.
 * @param block The player action block
 * @param localVariables The local variables of this scope
 * @param blockVariables The block variables unique to this block
 */
fun Interpreter.playerAction(block: PlayerActionBlock, localVariables: MutableMap<String, Argument>, blockVariables: MutableMap<String, Argument>) {
    when(block.action) {
        PlayerAction.SEND_MESSAGE -> {
            val text = block.args.getOrNull(0)
            if(text is Argument.RichText) {
                playerTargets.forEach { it.sendMessage(text.value) }
            }
        }
        PlayerAction.SEND_ACTIONBAR -> {
            val text = block.args.getOrNull(0)
            if(text is Argument.RichText) {
                playerTargets.forEach { it.sendActionBar(text.value) }
            }
        }
        PlayerAction.SEND_TITLE -> {
            val title = block.args.getOrNull(0)
            val subtitle = block.args.getOrNull(1)
            playerTargets.forEach {
                it.sendTitlePart(
                    TitlePart.TIMES,
                    Title.Times.times(
                        java.time.Duration.ofMillis(500),
                        java.time.Duration.ofMillis(3000),
                        java.time.Duration.ofMillis(500)
                    )
                )
            }
            if(title is Argument.RichText) {
                playerTargets.forEach { it.sendTitlePart(TitlePart.TITLE, title.value) }
            }
            if(subtitle is Argument.RichText) {
                playerTargets.forEach { it.sendTitlePart(TitlePart.SUBTITLE, subtitle.value) }
            }
        }
        PlayerAction.LAUNCH_UP -> {
            val num = block.args[0]
            if(num is Argument.Number) {
                playerTargets.forEach { it.velocity = it.velocity.add(0.0, num.value, 0.0) }
            }
        }


    }
}