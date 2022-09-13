package Netty.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class JsonMessage {
    private int id;
    private String content;

    public String convertToJson() {
        return JSON.toJSONString(this);
    }

    public JsonMessage parseJsonString(String messageStr) {
        return JSONObject.parseObject(messageStr, JsonMessage.class);
    }
}
