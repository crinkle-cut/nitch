package net.mine_diver.smoothbeta.api.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class Namespace implements Comparable<@NotNull Namespace> {
    private static final Map<String, Namespace> CACHE = new HashMap<>();

    @NotNull
    public static final Namespace MINECRAFT = of("minecraft");

    public static @NotNull Namespace of(@NotNull final String namespace) {
        return CACHE.computeIfAbsent(namespace, Namespace::new);
    }

    @NotNull
    private final String namespace;
    private final int hashCode;

    private Namespace(@NotNull final String namespace) {
        this.namespace = namespace;
        hashCode = toString().hashCode();
    }

    public @NotNull Identifier id(@NotNull final String id) {
        return Identifier.of(this, id);
    }

    @Override
    public boolean equals(@NotNull final Object other) {
        if (this == other)
            return true;
        if (other instanceof Namespace otherNamespace) {
            return namespace.equals(otherNamespace.namespace);
        }
        return false;
    }

    @Override
    public @NotNull String toString() {
        return namespace;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public int compareTo(@NotNull final Namespace o) {
        return namespace.compareTo(o.namespace);
    }
}
