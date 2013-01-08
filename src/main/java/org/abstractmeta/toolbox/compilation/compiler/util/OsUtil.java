package org.abstractmeta.toolbox.compilation.compiler.util;

/**
 * Represents OsUtil
 * <p>
 * </p>
 *
 * @author Adrian Witas
 */
public class OsUtil {
    private static String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return OS.contains("win");
    }

}
