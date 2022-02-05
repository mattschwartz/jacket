package com.barelyconscious.jacket.data.model;


import lombok.*;

import java.time.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private LocalDate date;
    private List<JacketTask> tasks;
}
