package melon.im.im;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.Serializable;
import java.util.Date;

import static melon.im.base.Constant.ACTION_IM;
import static melon.im.base.Constant.IM_MODEL;


public abstract class BaseImActivity extends Activity {

    private class ImReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || !TextUtils.equals(intent.getAction(), ACTION_IM)){
                return;
            }
            Bundle bundle = intent.getExtras();
            if (bundle == null){
                return;
            }
            Serializable serializable = bundle.getSerializable(IM_MODEL);
            if (serializable == null){
                return;
            }
            ImModel imModel = (ImModel)serializable;
            imModel.setLogo("http://ww1.sinaimg.cn/large/71d1678cgw1elwrspnlicj20go0goq4a.jpg");
            imModel.setTime(new Date().getTime());
            handleAddMsg(imModel);
        }
    }

    private ImReceiver imReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imReceiver = new ImReceiver();
        IntentFilter filter = new IntentFilter(ACTION_IM);
        registerReceiver(imReceiver,filter);
    }

    protected abstract void handleAddMsg(ImModel imModel);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imReceiver);
    }

}
