package net.mine_diver.smoothbeta.api.resource;

import java.io.InputStream;

public interface Resource {
    InputStream getInputStream();

    String getResourcePackName();
}
