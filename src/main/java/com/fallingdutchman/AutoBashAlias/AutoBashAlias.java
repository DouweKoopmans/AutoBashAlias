package com.fallingdutchman.AutoBashAlias;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Douwe Koopmans on 18-1-16.
 */
public class AutoBashAlias {

    @Option(name = "--verbose", handler = BooleanOptionHandler.class, aliases = {"-v"} ,
        usage = "verbosely print the process")
    private boolean verbose = false;

    @Option(name = "--dry_run", handler = BooleanOptionHandler.class, aliases = {"-d"},
        usage = "run through the program without actually adding an alias")
    private boolean dry_run = false;

    @Option(name = "--target-file", aliases = {"-f"}, metaVar = "<file>",
            usage = "the path to the target file (the bash file where new alias is supposed to go)")
    private String targetFilePath = System.getProperty("user.home") + '/' + ".bash_aliases";


    @Option(name = "--help", aliases = {"-h"}, handler = BooleanOptionHandler.class, help = true,
            usage = "this help list")
    private boolean help = false;

    @Argument(usage = "the preferred name for this alias", metaVar = "<name>",required = true, index = 0)
    private String aliasName;

    @Argument(usage = "path to executable", metaVar = "<path>", required = true,
            index = 1)
    private String executionFilePath;

    public AutoBashAlias(String[] args) throws CmdLineException {
        processArgs(args);
    }

    public static void main(String[] args) {
        AutoBashAlias main;
        try {
            main = new AutoBashAlias(args);
        } catch (CmdLineException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println("usage:");
            e.getParser().printSingleLineUsage(System.out);
            System.out.println();
            return;
        }
        main.go();
    }

    public void go() {
        if (!checkPreConditions()) {
            return;
        }

        if (verbose) {
            System.out.println("target file: " + targetFilePath);
            System.out.println("alias name: " + aliasName);
            System.out.println("executable path: " + executionFilePath);
        }

        try(FileWriter fw = new FileWriter(new File(targetFilePath), true)) {
            final String output = "alias " + aliasName + "=\"" + executionFilePath + "\"";
            if (!dry_run) {
                fw.write("\n");
                fw.write(output);
            }
            if (verbose) {
                System.out.println("output: " + output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPreConditions() {
        if (help) {
            return false;
        }

        if (!System.getProperty("os.name").equals("Linux")) {
            System.out.println("this is a linux only program");
            return false;
        }

        if (!new File(targetFilePath).exists()) {
            System.out.println("the provided path for the target file doesn't exist, exiting");
            System.out.println("target file path: " + targetFilePath);
            return false;
        }
        if (!new File(executionFilePath).exists()) {
            System.out.println("the provided execution file doesn't exist, exiting");
            System.out.println("execution file path: " + executionFilePath);
            return false;
        }
        return true;
    }

    private void processArgs(String args[]) throws CmdLineException {
        CmdLineParser clp = new CmdLineParser(this);

        clp.parseArgument(args);

        if (help) {
            System.out.print("Usage: ");
            clp.printSingleLineUsage(System.out);
            System.out.println();
            clp.printUsage(System.out);
            return;
        }

        if (dry_run) {
            verbose = true;
        }
    }
}
