package melon.im.network;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MelonStringRequest extends StringRequest {

    private String mToken;

    public MelonStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public MelonStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String token) {
        super(method, url, listener, errorListener);
        this.mToken = token;

    }

    public MelonStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public MelonStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String token) {
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
