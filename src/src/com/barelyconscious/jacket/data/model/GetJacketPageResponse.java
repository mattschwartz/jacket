package com.barelyconscious.jacket.data.model;

import java.util.Objects;

public final class GetJacketPageResponse {
    private final JacketPage jacketPage;

    GetJacketPageResponse(JacketPage jacketPage) {
        this.jacketPage = jacketPage;
    }

    public JacketPage jacketPage() {
        return jacketPage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GetJacketPageResponse) obj;
        return Objects.equals(this.jacketPage, that.jacketPage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jacketPage);
    }

    @Override
    public String toString() {
        return "GetJacketPageResponse[" +
            "jacketPage=" + jacketPage + ']';
    }


}
