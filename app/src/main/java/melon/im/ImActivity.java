package melon.im;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Message;
import melon.im.im.ImEditTextController;
import melon.im.im.ImModel;
import melon.im.im.ImRecyclerView;
import melon.im.im.ImRecyclerViewController;
import melon.im.im.ImReqOperationController;
import melon.im.im.ImSendController;
import melon.im.im.ImBaseActivity;
import melon.im.im.OnImLoginListener;

public class ImActivity extends ImBaseActivity implements OnImLoginListener {

    private ImRecyclerView mRvList;

    private EditText mEtInput;
    private TextView mTvSend;

    private ImReqOperationController imReqOperationController;
    private ImRecyclerViewController imRecyclerViewController;

    private String targetUser;
    private String userAccount;

    public String schId = "52ac2e97747aec013fcf49e3";
    public String schName = "深圳大学";
    public String userUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            finish();
            return;
        }

        targetUser = bundle.getString("target_user","13533192332");
        userAccount = bundle.getString("user_account","13533192222");


        handlePageIm();

    }

    private void handlePageIm(){
        imReqOperationController = new ImReqOperationController(this);

        mRvList = (ImRecyclerView) findViewById(R.id.rv_im_list);
        imRecyclerViewController = new ImRecyclerViewController(this,mRvList,imReqOperationController);
        mRvList.setOnImLoadMoreListener(imRecyclerViewController);

        findViewById(R.id.ll_im_input_layout).setVisibility(View.VISIBLE);
        mEtInput = (EditText)findViewById(R.id.et_im_input);
        ImEditTextController imEditTextController = new ImEditTextController(mRvList);
        mEtInput.setOnClickListener(imEditTextController);

        mTvSend = (TextView)findViewById(R.id.tv_im_me_send);
        ImSendController imSendController = new ImSendController(this,imReqOperationController,mEtInput);
        mTvSend.setOnClickListener(imSendController);

        MyClient.getMyClient().getImManager().registerMsgEvent(this);
        MyClient.getMyClient().getImManager().loginImUser(userAccount,"123456",this);
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
