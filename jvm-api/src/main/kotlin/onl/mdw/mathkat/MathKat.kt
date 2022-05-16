package onl.mdw.mathkat

import fr.stardustenterprises.yanl.NativeLoader

object MathKat {
    external fun hello(s: String): String
    external fun getVersion(): String
    external fun setRulesDir(dir: String)
    external fun setMathml(mathmlStr: String): String
    init {
        val loader = NativeLoader.Builder().root("/META-INF/native").build()
        loader.loadLibrary("mathkat")
    }
}