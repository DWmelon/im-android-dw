package melon.im.im;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

import melon.im.base.MyClient;
import melon.im.network.IRequest;
import melon.im.network.IRequestCallback;


public class ReqManager {

    private static final String URL_UPDATE_CHECK = "http://119.23.222.106/timediff" + "/update/check";

    public void updateCheck() {

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }

        HashMap<String,String> map = new HashMap<>();

        request.sendRequestForPostWithJson(URL_UPDATE_CHECK, map, new IRequestCallback() {
            @Override
            public void onResponseSuccess(JSONObject jsonObject) {


            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
            }
        });


    }

}
