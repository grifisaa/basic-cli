/**
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
package edu.isu.cs.grifisaa.production;

import org.apache.commons.cli.*;

/**
 * Package Private Class containing the logic for the CLI. This class works with
 * the Apache Commons CLI
 *
 * @author Isaac Griffith
 * @version 1.0
 */
class CLI {

    private Options options;
    private CommandLine cmd;

    private CLI() {}

    /**
     * Method which processes the provided command line arguments through the
     * Commons CLI components
     *
     * @param args Command line arguments supplied by the client.
     */
    void process(String[] args) {
        defineOptions();
        try {
            parseOptions(args);
            interrogateOptions();
        } catch (ParseException e) {
            System.out.println("Unknown options.");
            printHelp();
        }
    }

    /**
     * The third stage of the process which interrogates the field cmd to
     * determine which flags and options have been set, and sets the appropriate
     * fields of the class to support this. This method also handles when help
     * or other options which exit the application are set and calls System.exit in
     * those cases.
     */
    private void interrogateOptions() {
        if (cmd == null || options == null)
            return;

        if (cmd.hasOption('h')) {
            printHelp();
            System.exit(0);
        }

        if (cmd.hasOption('v')) {
            System.out.printf("app version %s\n", Constants.VERSION);
            System.exit(0);
        }
    }

    /**
     * This method consists of the second stage of command line processing
     * which is to actually parse the provided command line arguments with\
     * a Commons CLI CommandLineParser. This method will return the command
     * line component which can then be interrogated in the third stage. Note
     * that if the options field has not been initialized, this method does
     * nothing.
     *
     * @param args Array of strings representing command line arguments
     * @throws ParseException Thrown when an error occurs while parsing the
     * provided command line arguments
     */
    private void parseOptions(String[] args) throws ParseException {
        if (options == null)
            return;

        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);
    }

    /**
     * This method contains the first stage, definition, of the process of using the Apache
     * Commons CLI. Here we initialize the options field and add options to it.
     */
    private void defineOptions() {
        options = new Options();

        // The following options are boolean flags (that is they are on or off) and cannot
        // take any arguments.
        // For more information on using Commons CLI to define options for different use
        // cases. Please go to: https://commons.apache.org/proper/commons-cli/usage.html
        options.addOption("h", "help", false, "print this message");
        options.addOption("v", "version", false, "print version information");
    }

    /**
     * This method prints the help message which consists of the usage statement, and the short
     * and long options and their descriptions. This message is then followed by a copyright notice.
     */
    private void printHelp() {
        if (options == null)
            return;

        HelpFormatter formatter = new HelpFormatter();
        String header = "\nOptions:";
        String footer = "\n" + Constants.COPYRIGHT;
        formatter.printHelp(80, "app", header, options, footer, true);
    }

    /**
     * The factory method supplying the Singleton Instance
     * @return Singleton instance of CLI
     */
    public static CLI getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * An internal class holding the singleton instance. Uses the Bill Pugh approach
     * to singletons in Java
     *
     * @author Isaac Griffith
     * @version 1.0
     */
    private static class Holder {
        private static CLI INSTANCE = new CLI();
    }
}
