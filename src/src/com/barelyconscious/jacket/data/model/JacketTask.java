package com.barelyconscious.jacket.data.model;

import lombok.Data;

@Data
public class JacketTask {
    private JacketTaskType type;
    private boolean isCompleted;
    private boolean isCancelled;
    private boolean isMigratedForward;
    private boolean isMigratedBackward;

    private long createdTimeMillis;
    private String text;
}
