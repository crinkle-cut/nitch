package net.mine_diver.smoothbeta.api.util;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Util {
    public static <T> T make(T object, Consumer<T> initializer) {
        initializer.accept(object);
        return object;
    }

    public static <T> T make(Supplier<T> initializer) {
        return initializer.get();
    }

    public static OperatingSystem getOperatingSystem() {
        String string = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (string.contains("win"))
            return OperatingSystem.WINDOWS;
        if (string.contains("mac"))
            return OperatingSystem.OSX;
        if (string.contains("linux") || string.contains("unix"))
            return OperatingSystem.LINUX;
        if (string.contains("solaris") || string.contains("sunos"))
            return OperatingSystem.SOLARIS;
        return OperatingSystem.UNKNOWN;
    }

    public enum OperatingSystem {
        LINUX("linux"),
        SOLARIS("solaris"),
        WINDOWS("windows"),
        OSX("mac"),
        UNKNOWN("unknown");

        private final String name;

        OperatingSystem(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
