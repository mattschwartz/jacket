package com.barelyconscious.jacket.data;

import com.barelyconscious.jacket.data.model.GetJacketPagesRequest;
import com.barelyconscious.jacket.data.model.GetJacketPagesResponse;

import java.util.List;

public interface JacketDAL {

    GetJacketPagesResponse getJacketPages(GetJacketPagesRequest request);

    List<Integer> listYears();

    List<Integer> listMonthsForYear(int year);

    List<Integer> listDaysForMonthAndYear(int year, int month);
}
