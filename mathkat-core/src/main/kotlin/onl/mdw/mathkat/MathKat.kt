/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
package onl.mdw.mathkat

import fr.stardustenterprises.yanl.NativeLoader

object MathKat {
    external fun getVersion(): String
    external fun setRulesDir(dir: String)
    external fun getPreference(name: String): String
    external fun setPreference(name: String, value: String)
    external fun setMathml(mathmlStr: String): String
    @JvmOverloads
    external fun getBraille(navigationId: String = ""): String
    init {
        val loader = NativeLoader.Builder().root("/META-INF/native").build()
        loader.loadLibrary("mathkat")
    }
}