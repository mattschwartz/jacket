package com.barelyconscious.jacket.data.model;

import com.barelyconscious.jacket.common.Between;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetJacketPagesRequest {

    private Between.IntegerBetween betweenYears;
    private Between.IntegerBetween betweenMonths;
    private Between.IntegerBetween betweenDays;
}
