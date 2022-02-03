package com.barelyconscious.jacket.cli;

import com.barelyconscious.jacket.cli.quickactions.AddTaskQuickAction;
import com.barelyconscious.jacket.cli.quickactions.ViewTasksQuickAction;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Represents the parent command for task subcommands.
 */
@Command(name = "task", description = "Quick actions for managing tasks.",
        subcommands = {AddTaskQuickAction.class, ViewTasksQuickAction.class})
public class TaskQuickActions {

    @Option(names = {"-h", "--help"}, usageHelp = true)
    private boolean help;
}
