/*
 * Copyright (c) 2017 Marco Rebhan (the_real_farfetchd)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
 * OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package therealfarfetchd.terminator.common.term

typealias Key = Pair<Char, Set<KeyModifier>>

interface ITerminal {
  /**
   * Non-blocking. If a char is available to be read, return it, otherwise return null.
   */
  fun read(): Key?

  /**
   * Puts a key in the key buffer.
   */
  fun bufferKey(c: Key)

  /**
   * Clears the keyboard input queue
   */
  fun resetInput()

  /**
   * Resets all attributes (colors, highlight, ...)
   */
  fun resetAttrib()

  /**
   * Writes a char at the specified position. If this is out of bounds, does nothing.
   */
  fun put(x: Int, y: Int, ch: Char)

  /**
   * Gets the char at the specified position. Returns null if it's out of bounds
   */
  fun get(x: Int, y: Int): Char?

  /**
   * Set the background color at the specified position.
   */
  fun setBGCol(color: Int)

  /**
   * Gets the background color at the specified position. Returns null if it's out of bounds
   */
  fun getBGCol(x: Int, y: Int): Int?

  /**
   * Set the foreground color at the specified position.
   */
  fun setFGCol(color: Int)

  /**
   * Gets the foreground color at the specified position. Returns null if it's out of bounds
   */
  fun getFGCol(x: Int, y: Int): Int?

  /**
   * Set the highlight at the specified position.
   */
  fun setHighlight(highlight: ColorPallette.Highlight)

  /**
   * Gets the highlight at the specified position. Returns null if it's out of bounds
   */
  fun getHighlight(x: Int, y: Int): ColorPallette.Highlight?

  /**
   * Returns the width of the terminal (in chars)
   */
  fun width(): Int

  /**
   * Returns the height of the terminal (in chars)
   */
  fun height(): Int

  /**
   * Set the size of the terminal (in chars)
   */
  fun resize(x: Int, y: Int)

  /**
   * Clears the screen.
   */
  fun clear()

  /**
   * True if the cursor should be displayed.
   */
  fun cursor(): Boolean

  /**
   * Set if the cursor should be displayed.
   */
  fun cursor(value: Boolean)

  /**
   * The cursor x position.
   */
  fun cursorX(): Int

  /**
   * The cursor y position.
   */
  fun cursorY(): Int

  /**
   * Set the cursor x position.
   */
  fun cursorX(x: Int)

  /**
   * Set the cursor y position.
   */
  fun cursorY(y: Int)

  /**
   * Scrolls the terminal up by n characters.
   */
  fun scroll(n: Int = 1)

  /**
   * Scrolls the terminal down by n characters.
   */
  fun scrollDown(n: Int = 1)

  /**
   * Scrolls the terminal left by n characters.
   */
  fun scrollLeft(n: Int = 1)

  /**
   * Scrolls the terminal right by n characters.
   */
  fun scrollRight(n: Int = 1)
}

/**
 * Moves the cursor to the right.
 */
fun ITerminal.moveCursor() {
  if (cursorX() + 1 < width()) {
    cursorX(cursorX() + 1)
  } else {
    newLine()
  }
}

/**
 * Moves the cursor to the beginning of the next line.
 */
fun ITerminal.newLine() {
  cursorX(0)
  if (cursorY() + 1 < height()) {
    cursorY(cursorY() + 1)
  } else {
    scroll()
  }
}