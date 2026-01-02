package net.mine_diver.smoothbeta.api.resource;

import net.mine_diver.smoothbeta.api.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public interface ResourceFactory {
    Resource getResourceOrThrow(Identifier identifier) throws IOException;

    default BufferedReader openAsReader(Identifier identifier) throws IOException {
        return new BufferedReader(
                new InputStreamReader(this.getResourceOrThrow(identifier).getInputStream(), StandardCharsets.UTF_8));
    }
}
