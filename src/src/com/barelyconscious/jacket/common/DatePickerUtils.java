package com.barelyconscious.jacket.common;

import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.regex.*;

public final class DatePickerUtils {

    private static final Map<Pattern, DateTimeFormatter> formattersByPattern = new HashMap<>() {{
        put(Pattern.compile("[0-9]+-[0-9]+-[0-9]+"),
            DateTimeFormatter.BASIC_ISO_DATE);
        put(Pattern.compile("[0-9]+/[0-9]+/[0-9]+"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }};

    public static LocalDate parseDateString(final String dateStr) {
        if (dateStr == null) {
            return LocalDate.now();
        }
        var relativeDate = RelativeDate.parse(dateStr);
        if (relativeDate != null) {
            return relativeDate.toLocalDate();
        }

        for (final Map.Entry<Pattern, DateTimeFormatter> entry : formattersByPattern.entrySet()) {
            final boolean isMatch = entry.getKey().matcher(dateStr).matches();
            if (isMatch) {
                return LocalDate.parse(dateStr, entry.getValue());
            }
        }

        throw new RuntimeException("Invalid date. Could not parse date=\"" + dateStr + "\"");
    }

    private DatePickerUtils() {
    }
}
