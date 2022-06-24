/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
package onl.mdw.mathcat4j

/**
 * Node based navigation position.
 *
 * @param node The node XML.
 * @param offset The character offset into the node.
 */
data class NavigationNode(val node: String, val offset: Int)

/**
 * Node ID based navigation position.
 *
 * @param The ID of the navigation node.
 * @param The character offset from the node.
 */
data class NavigationId(val id: String, val offset: Int)

/**
 * The MathCAT API.
 */
interface MathCat {
    /**
     * Get the version of the MathCAT library.
     *
     * @return The MathCAT library version
     */
    fun getVersion(): String
    /**
     * Set the rules directory.
     *
     * Like with the MathCAT library, this should be the first function call you make when using the MathCAT library.
     * @param dir The directory containing the rules files.
     */
    fun setRulesDir(dir: String)
    /**
     * Get a preference.
     *
     * @param name The name of the preference.
     * @return The value of the preference.
     */
    fun getPreference(name: String): String
    /**
     * Set a preference.
     *
     * @param name The name of the preference.
     * @param value The value to be set.
     */
    fun setPreference(name: String, value: String)
    /**
     * Set the MathML content.
     *
     * @param mathmlStr The MathML string.
     * @return The canonical MathML representation with IDs set on elements.
     */
    fun setMathml(mathmlStr: String): String
    /**
     * Get the Braille representing an element.
     *
     * @param navigationId The ID of the element. Setting this to the empty string will get the Braille for the whole MathML.
     * @return The Braille of the requested element.
     */
    fun getBraille(navigationId: String = ""): String

    /**
     * Get the spoken text for the MathML which was set.
     *
     * @return The spoken text for the MathML.
     */
    fun getSpokenText(): String

    /**
     * Get the spoken overview text for the MathML which was set.
     *
     * @return The spoken overview text of the MathML.
     */
    fun getOverviewText(): String

    /**
     * Perform navigation by keypress.
     *
     * @param key The keycode.
     * @param shiftKey Whether shift is pressed.
     * @param controlKey Whether control key is pressed.
     * @param altKey Whether the alt key is pressed.
     * @param metaKey Whether the meta key is pressed.
     * @return The spoken text resulting from the navigation.
     */
    fun doNavigateKeypress(key: Int, shiftKey: Boolean, controlKey: Boolean, altKey: Boolean, metaKey: Boolean): String

    /**
     * Perform navigation based on a command.
     *
     * @param command The navigation command.
     * @return The spoken text resulting from the navigation.
     */
    fun doNavigateCommand(command: String): String

    /**
     * Get the MathML of the current navigation.
     *
     * @return The navigation position containing the XML of the node.
     */
    fun getNavigationMathml(): NavigationNode

    /**
     * Get the ID of the current navigation.
     *
     * @return The navigation position containing the node ID.
     */
    fun getNavigationMathmlId(): NavigationId
}