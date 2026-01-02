package net.mine_diver.smoothbeta.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Environment(EnvType.CLIENT)
public class VertexFormats {
      public static final VertexFormatElement POSITION_ELEMENT = new VertexFormatElement(0,
                  VertexFormatElement.ComponentType.FLOAT, VertexFormatElement.Type.POSITION, 3);
      public static final VertexFormatElement COLOR_ELEMENT = new VertexFormatElement(0,
                  VertexFormatElement.ComponentType.UBYTE, VertexFormatElement.Type.COLOR, 4);
      public static final VertexFormatElement TEXTURE_0_ELEMENT = new VertexFormatElement(0,
                  VertexFormatElement.ComponentType.FLOAT, VertexFormatElement.Type.UV, 2);
      public static final VertexFormatElement NORMAL_ELEMENT = new VertexFormatElement(0,
                  VertexFormatElement.ComponentType.BYTE, VertexFormatElement.Type.NORMAL, 3);
      public static final VertexFormatElement PADDING_ELEMENT = new VertexFormatElement(0,
                  VertexFormatElement.ComponentType.BYTE, VertexFormatElement.Type.PADDING, 5);
      public static final VertexFormat POSITION_TEXTURE_COLOR_NORMAL = new VertexFormat(
                  net.mine_diver.smoothbeta.api.util.Util.make(new LinkedHashMap<>(), map -> {
                        map.put("Position", POSITION_ELEMENT);
                        map.put("UV0", TEXTURE_0_ELEMENT);
                        map.put("Color", COLOR_ELEMENT);
                        map.put("Normal", NORMAL_ELEMENT);
                        map.put("Padding", PADDING_ELEMENT);
                  }));
}
