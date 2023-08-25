package emeraldwater.infernity.dev.interpreter

enum class PlayerEvent(val signText: String) {
    JOIN("Join"),
    LEAVE("Leave");
}

fun playerEventFromString(string: String): PlayerEvent? {
    for(entry in PlayerEvent.entries) {
        if(string == entry.signText) {
            return entry
        }
    }
    return null
}

enum class PlayerAction(val signText: String) {
    SEND_MESSAGE("SendMessage");
}

fun playerActionFromString(string: String): PlayerAction? {
    for(entry in PlayerAction.entries) {
        if(string == entry.signText) {
            return entry
        }
    }
    return null
}