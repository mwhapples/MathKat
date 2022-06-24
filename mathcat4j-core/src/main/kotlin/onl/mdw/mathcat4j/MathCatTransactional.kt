/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
@file:JvmName("MathCatTransactional")
package onl.mdw.mathcat4j

import com.sun.jna.Native
import com.sun.jna.Platform
import java.io.File
import java.io.IOException

/**
 * Basic access to the MathCAT library.
 *
 * This object gives you access to the MathCAT functions. This API is designed to be basic, other libraries may build upon this to create higher-level APIs.
 */
private object MathCatImpl : MathCat {
    external override fun getVersion(): String

    external override fun setRulesDir(dir: String)

    external override fun getPreference(name: String): String

    external override fun setPreference(name: String, value: String)

    external override fun setMathml(mathmlStr: String): String

    external override fun getBraille(navigationId: String): String

    external override fun getSpokenText(): String

    external override fun getOverviewText(): String

    external override fun doNavigateKeypress(
        key: Int,
        shiftKey: Boolean,
        controlKey: Boolean,
        altKey: Boolean,
        metaKey: Boolean
    ): String

    external override fun doNavigateCommand(command: String): String

    external override fun getNavigationMathml(): NavigationPosition

    private fun extractLibrary(libraryResource: String): File? = try {
        Native.extractFromResourcePath("/META-INF/native/${System.mapLibraryName(libraryResource)}")
    } catch (e: IOException) {
        null
    }

    init {
        val baseLibName = "mathcat4j"
        val attemptLibraries = listOf("$baseLibName-${Platform.RESOURCE_PREFIX}", baseLibName)
        val libraryFile = attemptLibraries.firstNotNullOfOrNull(::extractLibrary) ?: throw java.lang.RuntimeException("Unable to extract library, tried ${attemptLibraries.joinToString()}")
        System.load(libraryFile.absolutePath)
        System.getProperty("onl.mdw.mathcat4j.rulesDir")?.let { setRulesDir(it) }
    }
}

fun <T> mathCAT(block: MathCat.() -> T): T = synchronized(MathCatImpl) {
    MathCatImpl.block()
}