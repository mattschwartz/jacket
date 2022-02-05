package com.barelyconscious.jacket.data.model;

import com.googlecode.lanterna.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JacketTask {
    private JacketTaskType type;
    private boolean isCompleted;
    private boolean isCancelled;
    private boolean isMigratedForward;
    private boolean isMigratedBackward;

    private long createdTimeMillis;
    private String text;

    public char getTaskSymbol() {
        if (type == JacketTaskType.NOTE) {
            return '─';
        }

        if (isMigratedBackward) {
            return Symbols.TRIANGLE_LEFT_POINTING_MEDIUM_BLACK;
        } else if (isMigratedForward) {
            return Symbols.TRIANGLE_RIGHT_POINTING_MEDIUM_BLACK;
        } else if (isCompleted) {
            return Symbols.BULLET;
        } else if (isCancelled) {
            return '×';
        } else {
            return Symbols.INVERSE_BULLET;
        }
    }
}
