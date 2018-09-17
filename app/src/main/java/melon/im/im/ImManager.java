package melon.im.im;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;
import melon.im.R;
import melon.im.base.MyClient;

import static cn.jpush.im.android.api.enums.ContentType.text;
import static melon.im.base.Constant.*;

public class ImManager {

    private static final String APP_KEY = "ec8f3e299ce973c671d12be2";

    private Context mContext;

    private Conversation conversation;
    private boolean isLogin;

    public ImManager(Context context){
        mContext = context;
    }

    public void loginImUser(final OnImLoginListener listener){
        JMessageClient.login("13533192222","123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0){
                    isLogin = true;
                    if (listener != null){
                        listener.onImLogin();
                    }
                }
            }
        });
    }

    public boolean isLogin(){
        return isLogin;
    }

    public void registerMsgEvent(Context context){
        JMessageClient.registerEventReceiver(context);
    }

    public void unregisterMsgEvent(Context context){
        JMessageClient.unRegisterEventReceiver(context);
    }

    public void createSession(String userName){
        conversation = JMessageClient.getSingleConversation(userName,APP_KEY);
        if (null == conversation) {
            conversation = Conversation.createSingleConversation(userName,APP_KEY);
        }
        List<Conversation> mDatas = JMessageClient.getConversationList();
//                JMessageClient.enterSingleConversation(userName);
        List<Message> messageList = conversation.getMessagesFromNewest(0, 18);
    }

    public void exitSession(){
        JMessageClient.exitConversation();
        conversation = null;
        JMessageClient.logout();
    }

    /**
     * 处理接收到的消息
     * @param message
     */
    public void handleMessage(Message message){
        if (!message.getContentType().equals(text)){
            return;
        }
        TextContent textContent = (TextContent) message.getContent();
        String content = textContent.getText();
        MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_LEFT_1,content);
    }

    /**
     * 发送编辑的消息
     * @param content
     */
    public void sendMessage(final String content){
        if (conversation == null){
            return;
        }
        Message message = conversation.createSendMessage(new TextContent(content));

        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                // TODO: 2018/9/5 显示发送成功、保存到本地
                if (i == 0){
                    handleBroadcastMsg(IM_RIGHT_1,content);
                }
            }
        });
        JMessageClient.sendMessage(message);
    }

    /**
     * 发送显示消息广播
     * @param type
     * @param content
     */
    public void handleBroadcastMsg(int type,String content){
        ImModel model = new ImModel();
        model.setContent(content);
        model.setType(type);
        handleBroadcastMsg(model);
    }

    public void handleBroadcastMsg(ImModel model){
        Intent intent = new Intent(ACTION_IM);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IM_MODEL,model);
        intent.putExtras(bundle);
        mContext.sendBroadcast(intent);
        switch (model.getContent()){
            case "1":{
                String content1 = "同学你好，我是华南理工大学招生咨询小助手，请问有什么可以帮您的吗？";
                MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_LEFT_2,content1);
                break;
            }
            case "2":{
                String content2 = "";
                MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_LEFT_3,content2);
                break;
            }
            case "3":{
                String content3 = "华南理工大学为四人间。";
                MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_LEFT_4,content3);
                break;
            }
            case "4":{
                String content4 = "问题：华南理工大学怎么样？/回答：华南理工学生宿舍的房间内配有基本的家具（不含床上用品和日用品），空调，网络端口等配套设施，公共浴室有热水供应。宿舍外有专人打扫卫生，配有宿舍管理员，维修服务等，宿舍区配有专职辅导员管理员等。";
                MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_LEFT_5,content4);
                break;
            }
            case "5":{
                String content5 = "";
                MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_LEFT_6,content5);
                break;
            }
            case "6":{
                String content6 = "";
                MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_LEFT_7,content6);
                break;
            }
            case "7":{
                String content7 = "";
                MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_LEFT_8,content7);
                break;
            }
            case "8":{
                String content8 = "";
                MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_LEFT_9,content8);
                break;
            }
            case "9":{
                ImModel model9 = new ImModel();
                List<String> keys = new ArrayList<>();
                keys.add("会计");
                keys.add("国际经济与贸易");
                keys.add("市场国际经济与贸易");
                keys.add("市场营销");
                keys.add("会计");
                keys.add("国际经济与贸易");
                keys.add("市场国际经济与贸易");
                keys.add("市场营销");
                keys.add("会计");
                keys.add("国际经济与贸易");
                keys.add("市场国际经济与贸易");
                keys.add("市场营销");
                List<String> keyList = new ArrayList<>();
                for (String key : keys){
                    keyList.add(key);
                    keyList.add("|");
                }
                keyList.remove(keyList.size()-1);
                model9.setInfoList(keyList);
                model9.setType(IM_LEFT_10);
                MyClient.getMyClient().getImManager().handleBroadcastMsg(model9);
                break;
            }
            case "10":{
                ImModel imModel11 = new ImModel();
                imModel11.setType(IM_PUSH_MSG);
                imModel11.setName("华南理工大学招生办");
                imModel11.setContent("中国人民大学诚挚邀请您前来报考！");
                MyClient.getMyClient().getImManager().handleBroadcastMsg(imModel11);
                break;
            }
            case "11":{
                ImModel imModel12 = new ImModel();
                imModel12.setType(IM_PUSH_MSG);
                imModel12.setName("完美志愿团队");
                imModel12.setContent("准大学生及家长一定注意！学籍档案和户口如何迁移！");
                imModel12.setDetail("通知内容通知内容通知内容通知内容，通知内容内容通知内容，内容通知内容，通知内");
                MyClient.getMyClient().getImManager().handleBroadcastMsg(imModel12);
                break;
            }
            case "12":{
                ImModel imModel13 = new ImModel();
                imModel13.setType(IM_PUSH_MSG);
                imModel13.setName("完美志愿团队");
                imModel13.setContent("问卷调研中奖名单大公开！");
                imModel13.setImgUrl("res://"+mContext.getPackageName()+"/"+ R.drawable.icon_temp_pic);
                imModel13.setAds(true);
                MyClient.getMyClient().getImManager().handleBroadcastMsg(imModel13);
            }
        }
    }

}
