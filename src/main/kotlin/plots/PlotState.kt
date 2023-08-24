package emeraldwater.infernity.dev.plots

enum class PlotMode {
    PLAY,
    BUILD,
    DEV,
    IN_HUB;

    override fun toString(): String {
        return when(this) {
            PLAY -> "Play"
            BUILD -> "Build"
            DEV -> "Dev"
            IN_HUB -> "In Hub"
        }
    }
}

class PlotState(val id: Int, val mode: PlotMode) {
    override fun toString(): String {
        return "On Plot #$id ($mode Mode)"
    }
}