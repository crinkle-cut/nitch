package net.mine_diver.smoothbeta.api.resource;

import net.mine_diver.smoothbeta.api.util.Identifier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ResourceManager implements ResourceFactory {
    public static final ResourceManager INSTANCE = new ResourceManager();

    @Override
    public Resource getResourceOrThrow(Identifier identifier) throws IOException {
        String path = "/assets/" + identifier.namespace + "/" + identifier.path;
        InputStream stream = ResourceManager.class.getResourceAsStream(path);
        if (stream == null)
            throw new FileNotFoundException(path);
        return new Resource() {
            @Override
            public InputStream getInputStream() {
                return stream;
            }

            @Override
            public String getResourcePackName() {
                return "SmoothBeta internal";
            }
        };
    }
}
