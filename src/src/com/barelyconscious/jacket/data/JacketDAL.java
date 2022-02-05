package com.barelyconscious.jacket.data;

import com.barelyconscious.jacket.data.model.*;

import java.time.*;
import java.util.*;

public interface JacketDAL {

    JacketPage getJacketPage(LocalDate localDate);

    GetJacketPagesResponse getJacketPages(GetJacketPagesRequest request);

    List<Integer> listYears();

    List<Integer> listMonthsForYear(int year);

    List<Integer> listDaysForMonthAndYear(int year, int month);
}
