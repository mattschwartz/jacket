package com.barelyconscious.jacket.data.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class JacketPage {

    private final int year;
    private final int month;
    private final int day;
    private final List<JacketTask> tasks;
}
