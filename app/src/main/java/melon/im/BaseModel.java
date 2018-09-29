package melon.im;

import com.alibaba.fastjson.JSONObject;

public class BaseModel {

    private String KEY_CODE = "code";
    protected String KEY_DATA = "data";

    private int code;

    public void decode(JSONObject object){
        if (object == null){
            return;
        }
        code = object.getIntValue(KEY_CODE);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
