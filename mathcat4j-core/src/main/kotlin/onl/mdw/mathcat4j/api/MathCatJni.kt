/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
package onl.mdw.mathcat4j.api

/**
 * Java side API for the JNI bindings to MathCAT.
 *
 * This exposes the MathCAT functions to JVM based applications. This does not deal with any thread safety or other concerns which will be dealt with by subclasses of this class.
 */
abstract class MathCatJni : MathCat {
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
    external override fun getNavigationMathml(): NavigationNode
    external override fun getNavigationMathmlId(): NavigationId
}

/**
 * Exception to indicate when there is a panic in the rust code.
 *
 * If the JNI bindings detect a panic in the MathCAT rust code then an exception of this type will be thrown. In many cases it is said that you should not use panic for control flow, so the main reason for this exception type is to allow JVM code catch the panic and close down the JVM in a controlled way.
 */
class PanicException(message: String) : RuntimeException(message)