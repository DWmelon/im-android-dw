package melon.im;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.jpush.im.android.api.JMessageClient;

public class MainApplication extends Application {

  private static Context context;

  @Override
  public void onCreate() {
    super.onCreate();

    context = this.getApplicationContext();

    Fresco.initialize(this);
    JMessageClient.setDebugMode(true);
    JMessageClient.init(this);

    MyClient.getMyClient().init(this);

  }

  public static Context getContext(){
    return context;
  }

}
