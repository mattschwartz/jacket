package com.barelyconscious.jacket.common;

import java.time.*;
import java.util.regex.*;

public final class DatePickerUtils {

    private static final Pattern STD_YEAR_PATTERN =
        Pattern.compile("(?<year>[0-9]+)[-/](?<month>[0-9]+)[-/](?<day>[0-9]+)");

    public static LocalDate parseDateString(final String dateStr) {
        if (dateStr == null) {
            return LocalDate.now();
        }
        var relativeDate = RelativeDate.parse(dateStr);
        if (relativeDate != null) {
            return relativeDate.toLocalDate();
        }

        var matcher = STD_YEAR_PATTERN.matcher(dateStr);
        if (matcher.matches()) {
            final int year = Integer.parseInt(matcher.group("year"));
            final int month = Integer.parseInt(matcher.group("month"));
            final int day = Integer.parseInt(matcher.group("day"));
            return LocalDate.of(year, month, day);
        }

        throw new RuntimeException("Invalid date. Could not parse date=\"" + dateStr + "\"");
    }

    private DatePickerUtils() {
    }
}
