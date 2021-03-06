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

package therealfarfetchd.terminator.common.interpreter

import therealfarfetchd.terminator.common.interpreter.forth.ForthInterpreter
import therealfarfetchd.terminator.common.term.ITerminal
import therealfarfetchd.terminator.common.term.impl.RedirectableTerminal
import kotlin.concurrent.thread

object InterpreterManager {
  private lateinit var interpreterThread: Thread
  private val staticTerminal = RedirectableTerminal()

  private var started = false

  fun start() {
    if (started) error("Already started!")
    started = true
    startTerminal()
  }

  private fun startTerminal() {
    interpreterThread = thread(isDaemon = true, start = false, name = "terminal") {
      ForthInterpreter().start(Environment(staticTerminal))
      throw ExitMarker()
    }

    interpreterThread.setUncaughtExceptionHandler { _: Thread, e: Throwable ->
      if (e !is ExitMarker) e.printStackTrace()
      startTerminal()
    }

    interpreterThread.start()
  }

  fun setOutputTerminal(term: ITerminal) {
    staticTerminal.termImpl = term
  }

  private class ExitMarker : RuntimeException()
}