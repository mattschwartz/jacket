package com.barelyconscious.jacket.data.impl;

import com.barelyconscious.jacket.data.JacketDAL;
import com.barelyconscious.jacket.data.model.GetJacketPagesRequest;
import com.barelyconscious.jacket.data.model.GetJacketPagesResponse;
import com.barelyconscious.jacket.data.model.JacketPage;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class FileSystemJacketDAL implements JacketDAL {

    private final List<JacketPage> testData;

    public FileSystemJacketDAL() {
        testData = Lists.newArrayList(
            JacketPage.builder()
                .year(2019).month(1).day(1)
                .build(),
            JacketPage.builder()
                .year(2019).month(1).day(2)
                .build(),
            JacketPage.builder()
                .year(2019).month(1).day(3)
                .build(),
            JacketPage.builder()
                .year(2019).month(2).day(7)
                .build(),
            JacketPage.builder()
                .year(2019).month(2).day(8)
                .build(),
            JacketPage.builder()
                .year(2020).month(3).day(9)
                .build(),
            JacketPage.builder()
                .year(2021).month(4).day(10)
                .build(),
            JacketPage.builder()
                .year(2021).month(4).day(11)
                .build(),
            JacketPage.builder()
                .year(2021).month(4).day(12)
                .build());
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
