package melon.im.base;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;

import melon.im.im.ImManager;
import melon.im.im.ReqManager;
import melon.im.network.HttpRequestManager;
import melon.im.network.IInterface;
import melon.im.network.IRequest;
import melon.im.network.StorageManager;


/**
 * Created by melon on 2017/1/3.
 */

public class MyClient {

    private static MyClient myClient;


    public static final String SERVICE_HTTP_REQUEST = "httpRequest";

    private HashMap<String, IInterface> mService = new HashMap<String, IInterface>();


    private Context context;

    public static synchronized MyClient getMyClient(){
        if (myClient == null){
            myClient = new MyClient();
        }
        return myClient;
    }

    public void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("IpinClient#init,the param context is null,please check again");
        }
        this.context = context;

        getStorageManager().init();
        getImManager();
        initModule();
    }

    private void initModule() {
        IRequest request = new HttpRequestManager(context);
        mService.put(MyClient.SERVICE_HTTP_REQUEST, request);
    }

    public IInterface getService(String serviceName) {

        if (TextUtils.isEmpty(serviceName)) {
            return null;
        }

        return mService.get(serviceName);

    }
    private StorageManager storageManager;
    private ReqManager reqManager;
    private ImManager imManager;

    public synchronized StorageManager getStorageManager(){
        if (storageManager == null){
            storageManager = new StorageManager(context);
        }
        return storageManager;
    }

    public synchronized ReqManager getReqManager(){
        if (reqManager == null){
            reqManager = new ReqManager();
        }
        return reqManager;
    }

    public synchronized ImManager getImManager(){
        if (imManager == null){
            imManager = new ImManager(context);
        }
        return imManager;
    }


}
