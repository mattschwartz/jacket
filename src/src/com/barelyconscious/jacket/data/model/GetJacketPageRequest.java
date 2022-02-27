package com.barelyconscious.jacket.data.model;

import java.time.*;
import java.util.Objects;

public final class GetJacketPageRequest {
    private final LocalDate exactDate;

    GetJacketPageRequest(LocalDate exactDate) {
        this.exactDate = exactDate;
    }

    public LocalDate exactDate() {
        return exactDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GetJacketPageRequest) obj;
        return Objects.equals(this.exactDate, that.exactDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exactDate);
    }

    @Override
    public String toString() {
        return "GetJacketPageRequest[" +
            "exactDate=" + exactDate + ']';
    }

}
