package emeraldwater.infernity.dev.plugin_channels

class ClientMod(val name: String, val version: String) {
    override fun toString(): String {
        return "$name version $version"
    }
}