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

import melon.im.UrlConstantV2;


public abstract class ImBaseActivity extends Activity {

    private class ImReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || !TextUtils.equals(intent.getAction(), UrlConstantV2.RECEIVER.ACTION_IM)){
                return;
            }
            Bundle bundle = intent.getExtras();
            if (bundle == null){
                return;
            }
            Serializable serializable = bundle.getSerializable(UrlConstantV2.BUNDLE.IM_MODEL);
            if (serializable == null){
                return;
            }
            ImModel imModel = (ImModel)serializable;
            imModel.setTime(new Date().getTime());
            handleAddMsg(imModel);
        }
    }

    private ImReceiver imReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imReceiver = new ImReceiver();
        IntentFilter filter = new IntentFilter(UrlConstantV2.RECEIVER.ACTION_IM);
        registerReceiver(imReceiver,filter);
    }

    protected abstract void handleAddMsg(ImModel imModel);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(imReceiver);
    }

}
