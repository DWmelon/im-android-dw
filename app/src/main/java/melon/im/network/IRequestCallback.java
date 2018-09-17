package melon.im.network;

import com.alibaba.fastjson.JSONObject;

public interface IRequestCallback {

    public void onResponseSuccess(JSONObject jsonObject);

    public void onResponseSuccess(String str);

    public void onResponseError(int code);

}
