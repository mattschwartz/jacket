package com.barelyconscious.jacket.data;

import com.barelyconscious.jacket.data.model.*;

import java.util.List;

public interface JacketDAL {

    GetJacketPageResponse getJacketPage(GetJacketPageRequest request);

    GetJacketPagesResponse getJacketPages(GetJacketPagesRequest request);

    List<Integer> listYears();

    List<Integer> listMonthsForYear(int year);

    List<Integer> listDaysForMonthAndYear(int year, int month);
}
