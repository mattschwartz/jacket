package com.barelyconscious.jacket.cli;

import picocli.*;

/**
 * Top level commands for jacket.
 */
@CommandLine.Command(subcommands = {
    TaskQuickActions.class,
    StartInteractiveTerminal.class
})
public class JacketCLI {

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true)
    private boolean help;
}
