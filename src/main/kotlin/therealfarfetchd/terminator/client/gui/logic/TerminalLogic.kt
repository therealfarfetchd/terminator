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

package therealfarfetchd.terminator.client.gui.logic

import org.lwjgl.input.Keyboard
import therealfarfetchd.quacklib.client.api.gui.AbstractGuiLogic
import therealfarfetchd.terminator.client.gui.element.Terminal
import therealfarfetchd.terminator.common.term.ITerminal
import therealfarfetchd.terminator.common.term.Key
import therealfarfetchd.terminator.common.term.KeyModifier

class TerminalLogic : AbstractGuiLogic() {
  val term: Terminal by component()

  val termImpl: ITerminal by params()

  override fun init() {
    term.terminal = termImpl

    root.key { char, _ ->
      if (char != '\u0000') {
        var mods = emptySet<KeyModifier>()
        if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
          mods += KeyModifier.KeyCtrl
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
          mods += KeyModifier.KeyShift
        if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU))
          mods += KeyModifier.KeyAlt
        termImpl.bufferKey(Key(char, mods))
      }
    }
  }
}