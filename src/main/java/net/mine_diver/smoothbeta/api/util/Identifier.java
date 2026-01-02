package net.mine_diver.smoothbeta.api.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class Identifier implements Comparable<@NotNull Identifier> {
    public static final char NAMESPACE_SEPARATOR = ':';

    private static final Map<String, Identifier> CACHE = new HashMap<>();

    public static @NotNull Identifier of(@NotNull final String identifier) {
        Identifier id = CACHE.get(identifier);
        if (id != null) {
            return id;
        }
        final int i = identifier.indexOf(NAMESPACE_SEPARATOR);
        final String namespace;
        final String path;
        if (i < 0) {
            namespace = Namespace.MINECRAFT.toString();
            path = identifier;
        } else {
            namespace = identifier.substring(0, i);
            path = identifier.substring(i + 1);
        }
        id = of(Namespace.of(namespace), path);
        CACHE.put(identifier, id);
        return id;
    }

    public static @NotNull Identifier of(@NotNull final Namespace namespace, @NotNull final String id) {
        String key = namespace + ":" + id;
        return CACHE.computeIfAbsent(key, k -> new Identifier(namespace, id));
    }

    public static @Nullable Identifier tryParse(@NotNull final String string) {
        try {
            return of(string);
        } catch (final Exception e) {
            return null;
        }
    }

    @NotNull
    public final Namespace namespace;
    @NotNull
    public final String path;
    @NotNull
    private final String toString;
    private final int hashCode;

    private Identifier(@NotNull final Namespace namespace, @NotNull final String path) {
        this.namespace = namespace;
        this.path = path;
        this.toString = namespace + String.valueOf(NAMESPACE_SEPARATOR) + path;
        this.hashCode = toString.hashCode();
    }

    @Override
    public int compareTo(@NotNull final Identifier o) {
        return toString.compareTo(o.toString);
    }

    @Override
    public @NotNull String toString() {
        return toString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Identifier that = (Identifier) o;
        return toString.equals(that.toString);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
