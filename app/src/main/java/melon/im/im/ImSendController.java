package melon.im.im;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

import melon.im.ImActivity;
import melon.im.MyClient;
import melon.im.R;
import melon.im.UrlConstantV2;
import melon.im.im.listener.OnImSendMsgListener;

public class ImSendController implements View.OnClickListener,OnImSendMsgListener {

    private ImReqOperationController mImReqOperationController;
    private ImActivity mContext;
    private EditText mEtInput;


    public ImSendController(ImActivity mContext, ImReqOperationController mImReqOperationController, EditText mEtInput) {
        this.mContext = mContext;
        this.mImReqOperationController = mImReqOperationController;
        this.mEtInput = mEtInput;
    }

    @Override
    public void onClick(View v) {
        String content = mEtInput.getText().toString();
        if (TextUtils.isEmpty(content)){
            return;
        }
        mEtInput.setText("");
        //处于跟老师聊天的状态，发送IM消息，不调用接口
        if (mImReqOperationController.isConnectToTeacher()){
            MyClient.getMyClient().getImManager().sendMessage(content,this);
            return;
        }
        //if.   处于正在回答官方问题的状态，不调用获取相似问题接口
        //else. 不处于正在回答官方问题的状态，调用获取相似问题接口
        if (mImReqOperationController.isReplyingOfficialQuestion()){
            MyClient.getMyClient().getImManager().handleBroadcastMsg(UrlConstantV2.VALUE.IM_RIGHT_1,content);
            handleDealWithUserInput(content);
        }else{
            MyClient.getMyClient().getImManager().handleBroadcastMsg(UrlConstantV2.VALUE.IM_RIGHT_1,content);
            mImReqOperationController.handleReqMatchQuestion(content);
        }
    }

    /**
     * 当处于"回复官方问题"状态时，处理用户手动输入的内容，看是否满足回复条件，否则结束"回复官方问题"的状态
     * @param content
     */
    public void handleDealWithUserInput(String content){
        List<String> provinceList = Arrays.asList(mContext.getResources().getStringArray(R.array.all_province));
        String province = "";
        for (String prov : provinceList){
            if (content.contains(prov)){
                province = prov;
            }
        }
        if (!TextUtils.isEmpty(province)){
            mImReqOperationController.handleReqCondition(province);
            return;
        }
        mImReqOperationController.handleFail();
    }

    @Override
    public void onImSendMsgListener(String content) {

    }
}
