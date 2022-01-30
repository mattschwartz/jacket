package com.barelyconscious.jacket.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetJacketPagesResponse {

    private final List<JacketPage> pages;
}
