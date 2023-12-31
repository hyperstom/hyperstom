package emeraldwater.infernity.dev.interpreter

import kotlin.enums.EnumEntries

interface EnumAction {
    val signText: String
}

interface Event : EnumAction {

}
enum class PlayerEvent(override val signText: String) : EnumAction, Event {
    JOIN("Join"),
    LEAVE("Leave"),
    SNEAK("Sneak"),
    TICK("Tick"),
    SWAP_HANDS("SwapHands");
}

enum class PlayerAction(override val signText: String) : EnumAction {
    SEND_MESSAGE("SendMessage"),
    SEND_ACTIONBAR("ActionBar"),
    SEND_TITLE("Title"),
    LAUNCH_UP("LaunchUp");
}

enum class SetVariable(override val signText: String) : EnumAction {
    SET_EQUALS("="),
    ;
}

enum class SetTarget(override val signText: String) : EnumAction {
    DEFAULT("Default"),
    ALL_PLAYERS("AllPlayers");
}

enum class IfPlayer(override val signText: String) : EnumAction {
    STANDING_ON("StandingOn");
}

enum class IfVariable(override val signText: String) : EnumAction {

}

fun <T : EnumAction> findEntryByString(string: String, entries: List<T>): T? {
    for (entry in entries) {
        if (string == entry.signText) {
            return entry
        }
    }
    return null
}