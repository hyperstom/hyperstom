package emeraldwater.infernity.dev.plots

fun filterPlot(id: Int): Plot {
    val filtered = plots.filter { it.id == id }
    if(filtered.size != 1) {
        throw IndexOutOfBoundsException()
    } else {
        return filtered[0]
    }
}