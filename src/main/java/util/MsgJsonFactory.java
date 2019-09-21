package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import msg.IMessage;
import shape.*;

/**
 * @author Yangzhe Xie
 * @date 17/9/19
 */
public class MsgJsonFactory {
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(IMessage.class, (JsonDeserializer<IMessage>)
                    (jsonElement, type, jsonDeserializationContext) -> {
                        int msgType = jsonElement.getAsJsonObject().get("type").getAsInt();
                        switch (msgType) {
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

    public static <T extends IMessage> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

    public static IMessage fromJson(String json) {
        return gson.fromJson(json, IMessage.class);
    }

    public static <T> String toJson(T object) {
        return gson.toJson(object);
    }
}
