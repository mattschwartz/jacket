package com.barelyconscious.jacket.common;

public final class OSHelper {

    public static class SystemNotSupportedException extends RuntimeException {
        public SystemNotSupportedException(final String msg) {
            super(msg);
        }
    }

    public enum SystemOS {
        WINDOWS,
        MAC_OS_X,
        LINUX,
        UNKNOWN,
    }

    public static final SystemOS SYSTEM_OS;

    static {
        final String osName = System.getProperty("os.name");
        if (osName == null) {
            SYSTEM_OS = SystemOS.UNKNOWN;
        } else {
            System.out.println("Detected OS: " + osName);

            if ("Mac OS X".equals(osName)) {
                SYSTEM_OS = SystemOS.MAC_OS_X;
            } else if (osName.startsWith("Windows")) {
                SYSTEM_OS = SystemOS.WINDOWS;
            } else {
                SYSTEM_OS = SystemOS.UNKNOWN;
            }
        }
    }
}
