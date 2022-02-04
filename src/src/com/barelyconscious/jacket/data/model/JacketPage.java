package com.barelyconscious.jacket.data.model;


import lombok.*;

import java.time.*;
import java.util.*;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class JacketPage {

    public int getYear() {
        return date.getYear();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    private final LocalDate date;
    private final List<JacketTask> tasks;
}
