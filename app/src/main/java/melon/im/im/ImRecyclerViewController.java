package melon.im.im;

import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import melon.im.ImActivity;
import melon.im.MyClient;
import melon.im.R;
import melon.im.im.listener.OnImQuestionSolveListener;
import melon.im.im.listener.OnImSelectListener;

import static melon.im.UrlConstantV2.VALUE.IM_LEFT_10;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_11;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_2;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_3;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_4;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_5;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_7;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_8;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_9;
import static melon.im.UrlConstantV2.VALUE.IM_RIGHT_1;
import static melon.im.UrlConstantV2.VALUE.TYPE_HEADER;

public class ImRecyclerViewController implements OnImLoadMoreListener,OnImSelectListener,OnImQuestionSolveListener {

    private ImReqOperationController mImReqOperationController;

    private ImActivity mContext;
    private ImRecyclerView mRvList;
    private ImAdapter adapter;

    public ImRecyclerViewController(ImActivity mContext, ImRecyclerView view, ImReqOperationController mImReqOperationController) {
        this.mContext = mContext;
        this.mRvList = view;
        this.mImReqOperationController = mImReqOperationController;
        init();
    }

    public void init(){
//        List<ImModel> modelList = TempUtil.getImList1();
        List<ImModel> modelList = new ArrayList<>();
        boolean isNeedLoadMore = modelList.size() == 15;
        modelList = TempUtil.handleListForTime(modelList);
        if (isNeedLoadMore){
            mRvList.setNeedLoad(true);
            ImModel headerModel = new ImModel();
            headerModel.setType(TYPE_HEADER);
            headerModel.setContent("加载更多");
            modelList.add(0,headerModel);
        }
        // TODO: 2018/9/6 读取数据库，历史聊天记录

        adapter = new ImAdapter(mContext,mRvList,modelList,isNeedLoadMore);
        adapter.setOnImSelectListener(this);
        adapter.setOnImQuestionSolveListener(this);
        mRvList.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                handleAutoReply();
            }
        },300);

    }

    private void handleAutoReply(){
        mRvList.scrollToPosition(adapter.getItemCount()-1);
        List<String> questionList = new ArrayList<>();
        questionList.add(mContext.getString(R.string.im_auto_question_1,mContext.schName));
        questionList.add(mContext.getString(R.string.im_auto_question_2,mContext.schName));
        questionList.add(mContext.getString(R.string.im_auto_question_3,mContext.schName));
        ImModel imModel = new ImModel();
        imModel.setContent(mContext.getString(R.string.im_auto_reply,mContext.schName));
        imModel.setInfoList(questionList);
        imModel.setType(IM_LEFT_2);
        MyClient.getMyClient().getImManager().handleBroadcastMsg(imModel);
    }

    @Override
    public void onImLoadMore() {
        Toast.makeText(mContext,"正在加载更多",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ImModel> modelList = TempUtil.getImList2();
                boolean isNeedLoadMore = modelList.size() == 15;
                mRvList.setNeedLoad(isNeedLoadMore);
                modelList = TempUtil.handleListForTime(modelList);
                adapter.addImList(modelList);
                mRvList.notifyLoadFinish();
            }
        },4000);
    }

    public void handleAddMsg(ImModel imModel) {
        if (adapter == null){
            return;
        }
        adapter.addIm(imModel);
    }

    @Override
    public void onImSelect(int type,int index, String content) {
        switch (type){
            case IM_LEFT_2:{
                handleSelectOfficialQuestion(index,content);
                break;
            }
            case IM_LEFT_3:
            case IM_LEFT_4:
            case IM_LEFT_5:{
                handleSelectNormalQuestion(content);
                break;
            }
            case IM_LEFT_7:{
                handleSelectProvItem(content);
                break;
            }
            case IM_LEFT_8:{
                handleSelectCourseItem(content);
                break;
            }
            case IM_LEFT_9:{
                handleSelectCourseItem(content);
                break;
            }
            case IM_LEFT_10:{
                handleSelectMajorItem(content);
                break;
            }
            case IM_LEFT_11:{
                handleSelectBatchItem(index,content);
                break;
            }
        }
    }

    private void handleSelectOfficialQuestion(int index,String content){
        MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_RIGHT_1,content);
        //三大类问题，按顺序写死对应的逻辑，如果修改了问题顺序，这里就需要调整
        mImReqOperationController.setOfficialQuestionSelect(index,content);
        ImModel imModel = new ImModel();
        imModel.setType(IM_LEFT_7);
        MyClient.getMyClient().getImManager().handleBroadcastMsg(imModel);
    }

    private void handleSelectProvItem(String prov){
        MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_RIGHT_1,prov);
        mImReqOperationController.handleReqCondition(prov);
    }

    private void handleSelectCourseItem(String course){
        MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_RIGHT_1,course);
        mImReqOperationController.handleReqZSLQorMajor(course);
    }

    private void handleSelectMajorItem(String major){
        MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_RIGHT_1,major);
        mImReqOperationController.handleReqZjSchScore(major);
    }

    private void handleSelectBatchItem(int index,String batch){
        MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_RIGHT_1,batch);
        mImReqOperationController.handleReqCmSchScore(index,batch);
    }

    private void handleSelectNormalQuestion(String content){
        MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_RIGHT_1,content);
//        HashMap<String,ImHotQuestionModel> questionMap = MyClient.getMyClient().getImReqManager().getImHotQuestionModelMap();
//        if (questionMap.containsKey(content)){
//            ImHotQuestionModel model = questionMap.get(content);
//            ImModel imModel = new ImModel();
//            imModel.setType(IM_LEFT_4);
//            imModel.setOriginQuestion(content);
//            imModel.setContent(model.getAnswer());
//            mImReqOperationController.handleReqMatchQuestion(imModel);
//        }else {
            ImModel imModel = new ImModel();
            imModel.setType(IM_LEFT_3);
            imModel.setOriginQuestion(content);
            mImReqOperationController.handleReqMatchQuestion(imModel);
//        }
    }

    @Override
    public void onImQuestionSolve(String questionId,String originQuestion,boolean isSolve) {
        mImReqOperationController.handleReqMarkSolve(questionId,originQuestion,isSolve);
    }

}
