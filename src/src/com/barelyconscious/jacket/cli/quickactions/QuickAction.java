package com.barelyconscious.jacket.cli.quickactions;


import com.barelyconscious.jacket.cli.TaskQuickActions;
import picocli.CommandLine;

public abstract class QuickAction implements Runnable {

    @CommandLine.Option(names = {"-h", "--help"}, description = "Prints usage help.", usageHelp = true)
    private boolean help;

    @CommandLine.ParentCommand
    private TaskQuickActions parentCommand;

}