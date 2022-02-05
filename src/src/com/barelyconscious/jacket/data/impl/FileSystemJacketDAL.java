package com.barelyconscious.jacket.data.impl;

import com.barelyconscious.jacket.data.*;
import com.barelyconscious.jacket.data.model.*;
import com.google.common.collect.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

public final class FileSystemJacketDAL implements JacketDAL {

    private final List<JacketPage> testData;

    public FileSystemJacketDAL() {
        testData = Lists.newArrayList(
            JacketPage.builder()
                .date(LocalDate.of(2019, 1, 1))
                .tasks(Lists.newArrayList(
                    JacketTask.builder()
                        .type(JacketTaskType.TASK)
                        .text("Do a thing and this is a really long paragraph that says a lot of things but no one really cares about it we're just wrapping some words here.")
                        .build()
                ))
                .build(),
            JacketPage.builder()
                .date(LocalDate.of(2019, 1, 2))
                .build(),
            JacketPage.builder()
                .date(LocalDate.of(2019, 1, 3))
                .build(),
            JacketPage.builder()
                .date(LocalDate.of(2019, 2, 7))
                .build(),
            JacketPage.builder()
                .date(LocalDate.of(2019, 2, 8))
                .build(),
            JacketPage.builder()
                .date(LocalDate.of(2020, 3, 9))
                .build(),
            JacketPage.builder()
                .date(LocalDate.of(2021, 4, 10))
                .build(),
            JacketPage.builder()
                .date(LocalDate.of(2021, 4, 11))
                .build(),
            JacketPage.builder()
                .date(LocalDate.of(2021, 4, 12))
                .build());
    }

    @Override
    public GetJacketPageResponse getJacketPage(final GetJacketPageRequest request) {
        for (final var page : testData) {
            if (page.getDate().isEqual(request.exactDate())) {
                return new GetJacketPageResponse(page);
            }
        }
        return new GetJacketPageResponse(null);
    }

    @Override
    public GetJacketPagesResponse getJacketPages(final GetJacketPagesRequest request) {
        final List<JacketPage> results = new ArrayList<>();

        for (final JacketPage page : testData) {
            boolean betweenYear = request.getBetweenYears().inBetween(page.getYear());
            boolean betweenMonth = request.getBetweenMonths().inBetween(page.getMonth());
            boolean betweenDay = request.getBetweenDays().inBetween(page.getDay());

            if (betweenYear && betweenMonth && betweenDay) {
                results.add(page);
            }
        }

        return new GetJacketPagesResponse(results);
    }

    @Override
    public List<Integer> listYears() {
        return testData.stream().map(JacketPage::getYear).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Integer> listMonthsForYear(int year) {
        return testData.stream()
            .filter(t -> t.getYear() == year)
            .map(JacketPage::getMonth)
            .distinct()
            .collect(Collectors.toList());
    }

    @Override
    public List<Integer> listDaysForMonthAndYear(int year, int month) {
        return testData.stream()
            .filter(t -> t.getYear() == year && t.getMonth() == month)
            .map(JacketPage::getDay)
            .distinct()
            .collect(Collectors.toList());
    }
}
