package net.mine_diver.smoothbeta.api.util;

import com.google.gson.*;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;

public class JsonHelper {
    private static final Gson GSON = (new GsonBuilder()).create();

    public static boolean hasString(JsonObject object, String element) {
        return hasPrimitive(object, element) && object.getAsJsonPrimitive(element).isString();
    }

    public static boolean hasArray(JsonObject object, String element) {
        return hasElement(object, element) && object.get(element).isJsonArray();
    }

    public static boolean hasPrimitive(JsonObject object, String element) {
        return hasElement(object, element) && object.get(element).isJsonPrimitive();
    }

    public static boolean hasElement(JsonObject object, String lement) {
        return object != null && object.get(lement) != null;
    }

    public static String asString(JsonElement element, String name) {
        if (element.isJsonPrimitive()) {
            return element.getAsString();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be a string, was " + getType(element));
        }
    }

    public static String getString(JsonObject object, String element) {
        if (object.has(element)) {
            return asString(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find a string");
        }
    }

    public static String getString(JsonObject object, String element, String defaultStr) {
        return object.has(element) ? asString(object.get(element), element) : defaultStr;
    }

    public static boolean asBoolean(JsonElement element, String name) {
        if (element.isJsonPrimitive()) {
            return element.getAsBoolean();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be a Boolean, was " + getType(element));
        }
    }

    public static boolean getBoolean(JsonObject object, String element) {
        if (object.has(element)) {
            return asBoolean(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find a Boolean");
        }
    }

    public static boolean getBoolean(JsonObject object, String element, boolean defaultBoolean) {
        return object.has(element) ? asBoolean(object.get(element), element) : defaultBoolean;
    }

    public static float asFloat(JsonElement element, String name) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsFloat();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be a Float, was " + getType(element));
        }
    }

    public static float getFloat(JsonObject object, String element) {
        if (object.has(element)) {
            return asFloat(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find a Float");
        }
    }

    public static float getFloat(JsonObject object, String element, float defaultFloat) {
        return object.has(element) ? asFloat(object.get(element), element) : defaultFloat;
    }

    public static int asInt(JsonElement element, String name) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            return element.getAsInt();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be a Int, was " + getType(element));
        }
    }

    public static int getInt(JsonObject object, String element) {
        if (object.has(element)) {
            return asInt(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find a Int");
        }
    }

    public static int getInt(JsonObject object, String element, int defaultInt) {
        return object.has(element) ? asInt(object.get(element), element) : defaultInt;
    }

    public static JsonObject asObject(JsonElement element, String name) {
        if (element.isJsonObject()) {
            return element.getAsJsonObject();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be a JsonObject, was " + getType(element));
        }
    }

    public static JsonObject getObject(JsonObject object, String element) {
        if (object.has(element)) {
            return asObject(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find a JsonObject");
        }
    }

    public static JsonObject getObject(JsonObject object, String element, @Nullable JsonObject defaultObject) {
        return object.has(element) ? asObject(object.get(element), element) : defaultObject;
    }

    public static JsonArray asArray(JsonElement element, String name) {
        if (element.isJsonArray()) {
            return element.getAsJsonArray();
        } else {
            throw new JsonSyntaxException("Expected " + name + " to be a JsonArray, was " + getType(element));
        }
    }

    public static JsonArray getArray(JsonObject object, String element) {
        if (object.has(element)) {
            return asArray(object.get(element), element);
        } else {
            throw new JsonSyntaxException("Missing " + element + ", expected to find a JsonArray");
        }
    }

    public static JsonArray getArray(JsonObject object, String element, @Nullable JsonArray defaultArray) {
        return object.has(element) ? asArray(object.get(element), element) : defaultArray;
    }

    public static String getType(JsonElement element) {
        if (element == null)
            return "null (missing)";
        if (element.isJsonNull())
            return "null (json)";
        if (element.isJsonArray())
            return "an array";
        if (element.isJsonObject())
            return "an object";
        if (element.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = element.getAsJsonPrimitive();
            if (jsonPrimitive.isNumber())
                return "a number";
            if (jsonPrimitive.isBoolean())
                return "a boolean";
            if (jsonPrimitive.isString())
                return "a string";
        }
        return "unknown";
    }

    public static JsonObject deserialize(Reader reader) {
        return GSON.fromJson(reader, JsonObject.class);
    }

    public static JsonObject deserialize(String content) {
        return GSON.fromJson(content, JsonObject.class);
    }
}
