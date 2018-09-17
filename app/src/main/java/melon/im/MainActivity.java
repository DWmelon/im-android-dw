package melon.im;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Message;
import melon.im.base.MyClient;
import melon.im.im.BaseImActivity;
import melon.im.im.ImEditTextController;
import melon.im.im.ImModel;
import melon.im.im.ImRecyclerView;
import melon.im.im.ImRecyclerViewController;
import melon.im.im.ImTextViewController;
import melon.im.im.OnImLoginListener;

public class MainActivity extends BaseImActivity implements OnImLoginListener {

    private ImRecyclerView mRvList;

    private EditText mEtInput;
    private TextView mTvSend;

    private ImRecyclerViewController imRecyclerViewController;

    private boolean isShowEdit = true;
    private String targetUser = "13533192332";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle == null){
//            finish();
//        }else{
//            isShowEdit = bundle.getBoolean("is_im",true);
//            targetUser = bundle.getString("target_user","13533192332");
//        }


        mRvList = (ImRecyclerView) findViewById(R.id.rv_im_list);
        imRecyclerViewController = new ImRecyclerViewController(this,mRvList);
        mRvList.setOnImLoadMoreListener(imRecyclerViewController);

        if (isShowEdit){
            handlePageIm();
        }else{
            handlePageMsg();
        }

    }

    private void handlePageMsg(){
        findViewById(R.id.ll_im_input_layout).setVisibility(View.GONE);
    }

    private void handlePageIm(){
        findViewById(R.id.ll_im_input_layout).setVisibility(View.VISIBLE);
        mEtInput = (EditText)findViewById(R.id.et_im_input);
        ImEditTextController imEditTextController = new ImEditTextController(mRvList);
        mEtInput.setOnClickListener(imEditTextController);

        mTvSend = (TextView)findViewById(R.id.tv_im_me_send);
        ImTextViewController imTextViewController = new ImTextViewController(mEtInput);
        mTvSend.setOnClickListener(imTextViewController);

        MyClient.getMyClient().getImManager().registerMsgEvent(this);
        MyClient.getMyClient().getImManager().loginImUser(this);
    }

    @Override
    protected void onDestroy() {
        MyClient.getMyClient().getImManager().exitSession();
        MyClient.getMyClient().getImManager().unregisterMsgEvent(this);
        super.onDestroy();
    }

    /**
     * 新增一条聊天消息
     * @param imModel
     */
    @Override
    protected void handleAddMsg(ImModel imModel) {
        imRecyclerViewController.handleAddMsg(imModel);
    }

    /**
     * 登录成功回调
     */
    @Override
    public void onImLogin() {
        MyClient.getMyClient().getImManager().createSession(targetUser);
        Toast.makeText(this,"开始会话",Toast.LENGTH_LONG).show();
    }



    /**
     * 在线信息接收回调（子线程）
     * @param event
     */
    public void onEvent(MessageEvent event){
        //do your own business
        MyClient.getMyClient().getImManager().handleMessage(event.getMessage());
    }

    /**
     * 在线信息接收回调（主线程）
     * @param event
     */
    public void onEventMainThread(MessageEvent event){
        //do your own business
    }

    /**
     * 离线消息接收回调（子线程）
     * @param event
     */
    public void onEvent(OfflineMessageEvent event){
        //do your own business
        for (Message e : event.getOfflineMessageList()){
            MyClient.getMyClient().getImManager().handleMessage(e);
        }
    }

    /**
     * 离线消息接收回调（主线程）
     * @param event
     */
    public void onEventMainThread(OfflineMessageEvent event){
        //do your own business
    }

}
