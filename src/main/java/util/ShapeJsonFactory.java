package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import shape.*;

/**
 * @author Yangzhe Xie
 * @date 17/9/19
 */
public class ShapeJsonFactory {
    private static Gson gson = new GsonBuilder()
                .registerTypeAdapter(IShape.class, (JsonDeserializer<IShape>)
                        (jsonElement, type, jsonDeserializationContext) -> {
                    int shapeType = jsonElement.getAsJsonObject().get("type").getAsInt();
                    switch (shapeType) {
                        case 1:
                            return jsonDeserializationContext.deserialize(jsonElement, Line.class);
                        case 2:
                            return jsonDeserializationContext.deserialize(jsonElement, Rectangle.class);
                        case 3:
                            return jsonDeserializationContext.deserialize(jsonElement, Circle.class);
                        case 4:
                            return jsonDeserializationContext.deserialize(jsonElement, Oval.class);
                        case 5:
                            return jsonDeserializationContext.deserialize(jsonElement, FreeDraw.class);
                        case 6:
                            return jsonDeserializationContext.deserialize(jsonElement, Text.class);
                        default:
                            return null;
                    }
                }).create();

    public static <T extends IShape> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    public static IShape fromJson(String json) {
        return gson.fromJson(json, IShape.class);
    }

    public static <T> String toJson(T object) {
        return gson.toJson(object);
    }
}
