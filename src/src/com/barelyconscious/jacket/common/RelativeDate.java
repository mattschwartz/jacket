package com.barelyconscious.jacket.common;

import java.time.*;
import java.util.Objects;
import java.util.regex.*;

public final class RelativeDate {
    private final Matcher matcher;

    RelativeDate(Matcher matcher) {
        this.matcher = matcher;
    }

    public Matcher matcher() {
        return matcher;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RelativeDate) obj;
        return Objects.equals(this.matcher, that.matcher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matcher);
    }

    @Override
    public String toString() {
        return "RelativeDate[" +
            "matcher=" + matcher + ']';
    }

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
