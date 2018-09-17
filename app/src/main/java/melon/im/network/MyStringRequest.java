package melon.im.network;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

public class MyStringRequest extends com.android.volley.toolbox.StringRequest {

    private String mToken;

    public MyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public MyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String token) {
        super(method, url, listener, errorListener);
        this.mToken = token;

    }

    public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String token) {
        super(url, listener, errorListener);
        this.mToken = token;

    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        if(TextUtils.isEmpty(mToken)){
            return super.getHeaders();
        }
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "token="+mToken);

        return super.getHeaders();
    }



}
