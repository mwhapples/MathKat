/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
package onl.mdw.mathkat

import com.sun.jna.Native
import com.sun.jna.Platform
import java.io.File
import java.io.IOException

/**
 * Basic access to the MathCAT library.
 *
 * This object gives you access to the MathCAT functions. This API is designed to be basic, other libraries may build upon this to create higher-level APIs.
 */
object MathKat {
    /**
     * Get the version of the MathCAT library.
     *
     * @return The MathCAT library version
     */
    external fun getVersion(): String

    /**
     * Set the rules directory.
     *
     * Like with the MathCAT library, this should be the first function call you make when using the MathCAT library.
     * @param dir The directory containing the rules files.
     */
    external fun setRulesDir(dir: String)

    /**
     * Get a preference.
     *
     * @param name The name of the preference.
     * @return The value of the preference.
     */
    external fun getPreference(name: String): String

    /**
     * Set a preference.
     *
     * @param name The name of the preference.
     * @param value The value to be set.
     */
    external fun setPreference(name: String, value: String)

    /**
     * Set the MathML content.
     *
     * @param mathmlStr The MathML string.
     * @return The canonical MathML representation with IDs set on elements.
     */
    external fun setMathml(mathmlStr: String): String

    /**
     * Get the Braille representing a element.
     *
     * @param navigationId The ID of the element. Setting this to the empty string will get the Braille for the whole MathML.
     * @return The Braille of the requested element.
     */
    @JvmOverloads
    external fun getBraille(navigationId: String = ""): String

    init {
        val baseLibName = "mathcat4j"
        val attemptLibraries = listOf("$baseLibName-${Platform.RESOURCE_PREFIX}", baseLibName)
        val libraryFile = attemptLibraries.firstNotNullOfOrNull(::extractLibrary) ?: throw java.lang.RuntimeException("Unable to extract library, tried ${attemptLibraries.joinToString()}")
        System.load(libraryFile.absolutePath)
        System.getProperty("onl.mdw.mathcat4j.rulesDir")?.let { setRulesDir(it) }
    }
}

private fun extractLibrary(libraryResource: String): File? = try {
    Native.extractFromResourcePath("/META-INF/native/${System.mapLibraryName(libraryResource)}")
} catch (e: IOException) {
    null
}