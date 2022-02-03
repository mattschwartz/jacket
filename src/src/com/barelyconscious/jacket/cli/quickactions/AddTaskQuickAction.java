package com.barelyconscious.jacket.cli.quickactions;


import picocli.CommandLine;

@CommandLine.Command(name = "add", description = "Adds a new task with the specified context")
public class AddTaskQuickAction extends QuickAction {

    @CommandLine.Parameters(index = "0", description = "The task to add.")
    private String content;

    @Override
    public void run() {
        System.out.println("Doin a lil add task with content=\"" + content + "\"");
    }
}
