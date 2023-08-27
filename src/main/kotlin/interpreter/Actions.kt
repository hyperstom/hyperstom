package emeraldwater.infernity.dev.interpreter

sealed class Event {
    class PlayerEvent(val event: emeraldwater.infernity.dev.interpreter.PlayerEvent) : Event()
}
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
    SEND_MESSAGE("SendMessage"),
    LAUNCH_UP("LaunchUp");
}

fun playerActionFromString(string: String): PlayerAction? {
    for(entry in PlayerAction.entries) {
        if(string == entry.signText) {
            return entry
        }
    }
    return null
}

enum class SetVariable(val signText: String) {
    SET_EQUALS("="),;
}

fun setVariableFromString(string: String): SetVariable? {
    for(entry in SetVariable.entries) {
        if(string == entry.signText) {
            return entry
        }
    }
    return null
}

enum class SetTarget(val signText: String) {
    SET_EQUALS("="),;
}

fun setTargetFromString(string: String): SetTarget? {
    for(entry in SetTarget.entries) {
        if(string == entry.signText) {
            return entry
        }
    }
    return null
}