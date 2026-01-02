package net.mine_diver.smoothbeta.api.util;

public class PathUtil {
    public static String getPosixFullPath(String path) {
        return path.replace('\\', '/');
    }

    public static String normalizeToPosix(String path) {
        return path.replace('\\', '/');
    }
}
