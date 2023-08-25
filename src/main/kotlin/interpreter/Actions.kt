package emeraldwater.infernity.dev.interpreter

enum class PlayerEvent(val signText: String) {
    JOIN("Join"),
    LEAVE("Leave"),
}

enum class PlayerAction(val signText: String) {
    SEND_MESSAGE("SendMessage"),
}