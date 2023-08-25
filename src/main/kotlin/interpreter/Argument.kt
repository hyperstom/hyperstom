package emeraldwater.infernity.dev.interpreter

sealed class Argument {
    class Text(val value: String) : Argument()
    class RichText(val value: String) : Argument()
    class Number(val value: Double) : Argument()
}