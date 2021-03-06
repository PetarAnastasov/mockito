package org.mockito.internal.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Platform {

    private static final Pattern JAVA_8_RELEASE_VERSION_SCHEME = Pattern.compile("1\\.8\\.0_(\\d+)(?:-ea)?(?:-b\\d+)?");
    private static final Pattern JAVA_8_DEV_VERSION_SCHEME = Pattern.compile("1\\.8\\.0b\\d+_u(\\d+)");
    public static final String JAVA_VERSION = System.getProperty("java.specification.version");
    public static final String JVM_VERSION = System.getProperty("java.runtime.version");
    public static final String JVM_VENDOR = System.getProperty("java.vm.vendor");
    public static final String JVM_VENDOR_VERSION = System.getProperty("java.vm.version");
    public static final String JVM_NAME = System.getProperty("java.vm.name");
    public static final String JVM_INFO = System.getProperty("java.vm.info");
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String OS_VERSION = System.getProperty("os.version");

    private Platform() {}

    public static String describe() {
        return String.format("Java               : %s\n" +
                             "JVM vendor name    : %s\n" +
                             "JVM vendor version : %s\n" +
                             "JVM name           : %s\n" +
                             "JVM version        : %s\n" +
                             "JVM info           : %s\n" +
                             "OS name            : %s\n" +
                             "OS version         : %s\n",
                             JAVA_VERSION,
                             JVM_VENDOR,
                             JVM_VENDOR_VERSION,
                             JVM_NAME,
                             JVM_VERSION,
                             JVM_INFO,
                             OS_NAME,
                             OS_VERSION);
    }

    public static boolean isJava8BelowUpdate45() {
        return isJava8BelowUpdate45(JVM_VERSION);
    }

    static boolean isJava8BelowUpdate45(String jvmVersion) {
        Matcher matcher = JAVA_8_RELEASE_VERSION_SCHEME.matcher(jvmVersion);
        if (matcher.matches()) {
            int update = Integer.parseInt(matcher.group(1));
            return update < 45;
        }

        matcher = JAVA_8_DEV_VERSION_SCHEME.matcher(jvmVersion);
        if (matcher.matches()) {
            int update = Integer.parseInt(matcher.group(1));
            return update < 45;
        }

        matcher = Pattern.compile("1\\.8\\.0-b\\d+").matcher(jvmVersion);
        if (matcher.matches()) {
            return true;
        }

        return false;
    }
}
