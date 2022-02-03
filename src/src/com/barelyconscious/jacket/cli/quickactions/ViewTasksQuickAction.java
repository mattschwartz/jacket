package com.barelyconscious.jacket.cli.quickactions;

import com.barelyconscious.jacket.common.*;
import picocli.CommandLine.*;

import java.time.*;

@Command(name = "view",
    description = "View tasks for a specified date. If no date specified, 'view' will show today's tasks.")
public class ViewTasksQuickAction extends QuickAction {

    @Option(names = "-d",
        description = "Which date to view tasks for. Default is today's date. " +
            "\n\tCan specify a value relative to today's date, eg '-d d-10, m-1' which will show the tasks " +
            "for 1 month and 10 days ago.")
    private String dateStr;

    @Override
    public void run() {
        final LocalDate date = DatePickerUtils.parseDateString(dateStr);

        System.out.println("Viewing tasks for date=" + date);
    }

}