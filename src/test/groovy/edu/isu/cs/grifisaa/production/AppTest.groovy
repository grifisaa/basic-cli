/*
 * MIT License
 *
 * XRESE Example Command Line Processing
 * Copyright (c) 2020 Isaac D Griffith and Idaho State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/*
 * This Spock specification was generated by the Gradle 'init' task.
 */
package edu.isu.cs.grifisaa.production

import spock.lang.Specification

class AppTest extends Specification {

    ByteArrayOutputStream consoleText
    PrintStream oldOut
    PrintStream console
    File config

    def setup() {
        oldOut = System.out
        consoleText = new ByteArrayOutputStream()
        console = new PrintStream(consoleText)
        System.setOut(console)
    }

    def cleanup() {
        System.setOut(oldOut)
    }

    def "application -h"() {
        given:
        String[] args = ['-h']
        String expected = """\
usage: app [-h] [-v]

Options:
 -h,--help      print this message
 -v,--version   print version information

Copyright (C) 2020 Isaac Griffith and Idaho State University
"""
        MySecurityManager secManager = new MySecurityManager()
        System.setSecurityManager(secManager)

        when:
        App.main(args)

        then:
        thrown SecurityException
        consoleText.toString() == expected
    }

    def "application -v"() {
        given:
        String[] args = ['-v']
        String expected = """\
app version 1.0
"""
        MySecurityManager secManager = new MySecurityManager()
        System.setSecurityManager(secManager)

        when:
        App.main(args)

        then:
        thrown SecurityException
        consoleText.toString() == expected
    }
}
