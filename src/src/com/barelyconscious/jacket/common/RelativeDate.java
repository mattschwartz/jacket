package com.barelyconscious.jacket.common;

import java.time.*;
import java.util.regex.*;

public record RelativeDate(Matcher matcher) {
    private enum ParseRelativeDateDimension {
        d,
        m,
        y,
    }

    private static final String dimensionGroupName = "dim";
    private static final String deltaGroupName = "delta";
    private static final Pattern relativeDatePattern = Pattern.compile(
        "(?<dim>[dmy])(?<delta>[-?0-9]+)");

    public static RelativeDate parse(final String text) {
        var matcher = relativeDatePattern.matcher(text);
        if (matcher.find()) {
            matcher.reset();
            return new RelativeDate(matcher);
        }
        return null;
    }

    public LocalDate toLocalDate() {
        LocalDate result = LocalDate.now();

        boolean isMatch = matcher.find();
        while (isMatch) {
            final ParseRelativeDateDimension mod = ParseRelativeDateDimension.valueOf(matcher.group(dimensionGroupName));
            final int delta = Integer.parseInt(matcher.group(deltaGroupName));

            result = switch (mod) {
                case d -> result.plusDays(delta);
                case m -> result.plusMonths(delta);
                case y -> result.plusYears(delta);
            };

            isMatch = matcher.find();
        }

        return result;
    }
}
