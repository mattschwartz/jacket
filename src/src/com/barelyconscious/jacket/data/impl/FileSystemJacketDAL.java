package com.barelyconscious.jacket.data.impl;

import com.barelyconscious.jacket.common.OSHelper;
import com.barelyconscious.jacket.data.*;
import com.barelyconscious.jacket.data.model.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.*;
import lombok.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.time.*;
import java.util.*;
import java.util.stream.*;

import static com.barelyconscious.jacket.common.OSHelper.SYSTEM_OS;

public final class FileSystemJacketDAL implements JacketDAL {

    private final List<JacketPage> data;
    private final ObjectMapper objectMapper;

    public FileSystemJacketDAL() {
        try {
            objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            data = loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DB_FILEPATH = switch (SYSTEM_OS) {
        case WINDOWS -> "%APPDATA%";
        case MAC_OS_X, LINUX -> "/tmp/";
        default -> throw new OSHelper.SystemNotSupportedException("System not supported. OS name=" + SYSTEM_OS);
    } + "jacketdb.json";

    @Data
    private static class JacketPages {
        private List<JacketPage> pages;
    }

    private List<JacketPage> loadData() throws IOException {
        var file = new File(DB_FILEPATH);
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("Failed to create file=" + DB_FILEPATH);
            } else {
                return new ArrayList<>();
            }
        }

        try (var inputStream = new FileInputStream(file)) {
            final var jsonString = IOUtils.toString(inputStream, Charset.defaultCharset());
            if (StringUtils.isBlank(jsonString)) {
                return new ArrayList<>();
            }
        }

        var jacketPages = objectMapper.readValue(new File(DB_FILEPATH), JacketPages.class);
        return jacketPages.pages;
    }

    @Override
    public JacketPage getJacketPage(final LocalDate localDate) {
        for (final var page : data) {
            if (page.getDate().isEqual(localDate)) {
                return page;
            }
        }
        return null;
    }

    @Override
    public GetJacketPagesResponse getJacketPages(final GetJacketPagesRequest request) {
        final List<JacketPage> results = new ArrayList<>();

        for (final JacketPage page : data) {
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
        return data.stream().map(JacketPage::getYear).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Integer> listMonthsForYear(int year) {
        return data.stream()
            .filter(t -> t.getYear() == year)
            .map(JacketPage::getMonth)
            .distinct()
            .collect(Collectors.toList());
    }

    @Override
    public List<Integer> listDaysForMonthAndYear(int year, int month) {
        return data.stream()
            .filter(t -> t.getYear() == year && t.getMonth() == month)
            .map(JacketPage::getDay)
            .distinct()
            .collect(Collectors.toList());
    }
}
