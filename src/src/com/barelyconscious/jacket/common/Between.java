package com.barelyconscious.jacket.common;

import lombok.Data;
import lombok.Getter;

@Getter
public abstract class Between<T> {

    private final boolean inclusiveEnd;
    private final T from;
    private final T to;

    public Between(T from, T to) {
        this(from, to, false);
    }

    public Between(T from, T to, boolean inclusiveEnd) {
        this.from = from;
        this.to = to;
        this.inclusiveEnd = inclusiveEnd;
    }

    public abstract boolean inBetween(T other);

    public final class IntegerBetween extends Between<Integer> {

        public IntegerBetween(int from, int to, boolean inclusiveEnd) {
            super(from, to, inclusiveEnd);
        }

        @Override
        public boolean inBetween(final Integer other) {
            if (other.compareTo((Integer) from) < 0) {
                return false;
            } else if (inclusiveEnd) {
                return other.compareTo((Integer) to) <= 0;
            } else {
                return other.compareTo((Integer) to) < 0;
            }
        }
    }
}
