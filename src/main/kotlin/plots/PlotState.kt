package emeraldwater.infernity.dev.plots

enum class PlotMode {
    PLAY,
    BUILD,
    DEV,
    IN_HUB,
}

class PlotState(val id: Int, val mode: PlotMode)