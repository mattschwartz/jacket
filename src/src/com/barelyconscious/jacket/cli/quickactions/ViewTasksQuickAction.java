package com.barelyconscious.jacket.cli.quickactions;

import com.barelyconscious.jacket.common.*;
import com.barelyconscious.jacket.data.*;
import com.barelyconscious.jacket.gui.*;
import com.barelyconscious.jacket.guice.*;
import lombok.*;
import picocli.CommandLine.*;

import java.time.*;

import static com.google.inject.Guice.*;

@Command(name = "view",
    description = "View tasks for a specified date. If no date specified, 'view' will show today's tasks.")
public class ViewTasksQuickAction extends QuickAction {

    @Option(names = {"--date", "-d"},
        description = "Which date to view tasks for. Default is today's date. " +
            "\n\tCan specify a value relative to today's date, eg '-d d-10, m-1' which will show the tasks " +
            "for 1 month and 10 days ago.")
    private String dateStr;

    @Option(names = {"--month", "-m"})
    private String byMonthStr;

    @Option(names = {"--year", "-y"})
    private String byYearStr;

    @SneakyThrows
    @Override
    public void run() {
        final var injector = createInjector(new AppModule());
        final var dal = injector.getInstance(JacketDAL.class);
        final LocalDate date = DatePickerUtils.parseDateString(dateStr);

        new TasksForDayView(dal).startOnPage(date);
    }

}