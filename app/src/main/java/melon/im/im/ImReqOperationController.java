package melon.im.im;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import melon.im.ImActivity;
import melon.im.MyClient;
import melon.im.R;
import melon.im.UrlConstantV2;
import melon.im.im.listener.OnGetImConditionListener;
import melon.im.im.listener.OnGetImDetailListener;
import melon.im.im.listener.OnGetImEnrollListener;
import melon.im.im.listener.OnGetImMajorsListener;
import melon.im.im.listener.OnGetImMatchListener;
import melon.im.im.model.ImMajorModel;
import melon.im.im.model.ImQuestionConditionModel;
import melon.im.im.model.ImSchEnrollModel;
import melon.im.util.V2CovertUtils;

import static melon.im.UrlConstantV2.VALUE.IM_LEFT_10;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_11;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_4;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_6;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_8;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_9;

public class ImReqOperationController implements OnGetImMatchListener,OnGetImDetailListener,OnGetImConditionListener,OnGetImEnrollListener,OnGetImMajorsListener{

    private ImActivity mContext;

    public static final int VALUE_REPLY_OFFICIAL_CLOSED = -1;
    private final Integer VALUE_ZS = 1;
    private final Integer VALUE_LQ_1 = 2;
    private final Integer VALUE_LQ_2 = 3;

    private HashMap<Integer,Integer> queryTypeMap = new HashMap<>();

    private int officialQuestionType = VALUE_REPLY_OFFICIAL_CLOSED;
    private String officialQuestionName = "";
    private String officialQuestionProv = "";
    private String officialQuestionCourse = "";
    private HashMap<String,ImMajorModel.ImMajorItem> majorItemHashMap = new HashMap<>();
    private ImQuestionConditionModel imQuestionConditionModel;

    private boolean isConnectToTeacher = true;

    public ImReqOperationController(ImActivity mContext) {
        this.mContext = mContext;
        //key:  数组下标    顺序例子：1、我能考上华南理工大学吗？2、华南理工大学招生计划？3、华南理工大学历年录取分数？
        //value:接口参数    参数说明：1-从招生计划里取，2-从录取数据里去
        queryTypeMap.put(0,VALUE_LQ_1);
        queryTypeMap.put(1,VALUE_ZS);
        queryTypeMap.put(2,VALUE_LQ_2);
    }

    public void setOfficialQuestionSelect(int index,String content) {
        this.officialQuestionType = index;
        this.officialQuestionName = content;
    }

    public boolean isReplyingOfficialQuestion(){
        return officialQuestionType != VALUE_REPLY_OFFICIAL_CLOSED;
    }

    public boolean isConnectToTeacher() {
        return isConnectToTeacher;
    }

    public void setConnectToTeacher(boolean connectToTeacher) {
        isConnectToTeacher = connectToTeacher;
    }

    public void handleFail(){
        MyClient.getMyClient().getImManager().handleBroadcastMsg(IM_LEFT_6,"");
        closeOfficialReply();
    }

    private void closeOfficialReply(){
        officialQuestionType = VALUE_REPLY_OFFICIAL_CLOSED;
        officialQuestionName = "";
        officialQuestionProv = "";
        officialQuestionCourse = "";
        majorItemHashMap.clear();
        imQuestionConditionModel = null;
    }

    /**
     * 获取学校本专科-文理-批次
     * @param province
     */
    public void handleReqCondition(String province){
        if (!isReplyingOfficialQuestion()){
            handleReqMatchQuestion(province);
            return;
        }
        this.officialQuestionProv = province;
        // TODO: 2018/9/18 省份名转省份ID
        MyClient.getMyClient().getImManager().requestQuestionCondition(mContext.schId, "440000000000",queryTypeMap.get(officialQuestionType),this);
    }

    /**
     * 根据条件，获取学校招生或学校录取或学校下专业
     * @param course
     */
    public void handleReqZSLQorMajor(String course){
        if (!isReplyingOfficialQuestion()){
            handleReqMatchQuestion(course);
            return;
        }
        officialQuestionCourse = course;
        if ((queryTypeMap.get(officialQuestionType).equals(VALUE_LQ_1) || queryTypeMap.get(officialQuestionType).equals(VALUE_LQ_2))
                && officialQuestionProv.equals(mContext.getString(R.string.prov_zhejiang))){
            // TODO: 2018/9/18 省份名转省份ID
            MyClient.getMyClient().getImManager().requestQuestionSchEnrollMajors(mContext.schId,"440000000000", Arrays.asList(officialQuestionCourse.split("/")),this);
            return;
        }
        if (queryTypeMap.get(officialQuestionType).equals(VALUE_ZS)){
            // TODO: 2018/9/18 省份名转省份ID
            MyClient.getMyClient().getImManager().requestQuestionSchEnroll(mContext.schId,"440000000000",V2CovertUtils.convertWenli(officialQuestionCourse),this);
        }else if (queryTypeMap.get(officialQuestionType).equals(VALUE_LQ_1)){
            MyClient.getMyClient().getImManager().requestQuestionSchScore(mContext.schId,"440000000000",V2CovertUtils.convertWenli(officialQuestionCourse),-1,"",this);
        }else if (queryTypeMap.get(officialQuestionType).equals(VALUE_LQ_2)){
            // TODO: 2018/9/18 批次转换
            List<String> infoList = new ArrayList<>();
            infoList.add("一批");
            infoList.add("二批");
            ImModel imModel = new ImModel();
            imModel.setType(IM_LEFT_11);
            imModel.setInfoList(infoList);
            MyClient.getMyClient().getImManager().handleBroadcastMsg(imModel);
        }
    }


    /**
     * 获取学校录取数据（浙江，需要学校下的专业）
     * @param major
     */
    public void handleReqZjSchScore(String major){
        if (!isReplyingOfficialQuestion()){
            handleReqMatchQuestion(major);
            return;
        }
        if (!majorItemHashMap.containsKey(major)){
            handleFail();
            return;
        }
        String majorId = majorItemHashMap.get(major).getMajorId();
        MyClient.getMyClient().getImManager().requestQuestionSchScore(mContext.schId,"440000000000",-1,-1,majorId,this);
    }

    /**
     * 获取学校录取数据（VALUE_LQ_2情况，一般省份，需要批次）
     * @param batch
     */
    public void handleReqCmSchScore(int batchIndex,String batch){
        if (!isReplyingOfficialQuestion()){
            handleReqMatchQuestion(batch);
            return;
        }
        int batchInt = imQuestionConditionModel.getBatchItemList().get(batchIndex);
        MyClient.getMyClient().getImManager().requestQuestionSchScore(mContext.schId,"440000000000",V2CovertUtils.convertWenli(officialQuestionCourse),batchInt,"",this);
    }

    /**
     * 获取相似问题
     * @param model
     */
    public void handleReqMatchQuestion(ImModel model){
        MyClient.getMyClient().getImManager().requestQuestionMatch(mContext.schId,model.getOriginQuestion(),model,this);
    }

    public void handleReqMatchQuestion(String content){
        ImModel imModel = new ImModel();
        imModel.setType(UrlConstantV2.VALUE.IM_LEFT_3);
        imModel.setOriginQuestion(content);
        handleReqMatchQuestion(imModel);
    }

    /**
     * 标记问题是否解决用户需求
     * @param questionId
     * @param originQuestion
     * @param isSolve
     */
    public void handleReqMarkSolve(String questionId,String originQuestion,boolean isSolve){
        MyClient.getMyClient().getImManager().requestQuestionSolve(mContext.schId,questionId,originQuestion,isSolve);
    }

    @Override
    public void onGetImMatchFinish(boolean isSuccess, ImModel model, String firstMatchId, double firstMatchRatio) {
        if (!isSuccess){
            handleFail();
            return;
        }
        //具体问题没有，相似问题也没有，返回
        if (TextUtils.isEmpty(model.getContent())&&model.getInfoList().size()==0){
            handleFail();
            return;
        }
        //具体问题已有，不管相似问题有无，直接显示
        if (!TextUtils.isEmpty(model.getContent())){
            if (model.getInfoList().size()>0 && model.getOriginQuestion().equals(model.getInfoList().get(0))){
                model.getInfoList().remove(0);
            }
            MyClient.getMyClient().getImManager().handleBroadcastMsg(model);
            return;
        }
        //具体问题没有，相似问题有，执行以下判断
        //如果第一条相似问题的相似度达到100%，则需获取该问题作为具体问题
        //如果第一条相似问题的相似度小于100%，则直接显示相似问题列表
        if ((int)(firstMatchRatio - 100) == 0){
            model.setType(IM_LEFT_4);
            model.getInfoList().remove(0);
            MyClient.getMyClient().getImManager().requestQuestionById(mContext.schId,firstMatchId,model,this);
        }else{
            MyClient.getMyClient().getImManager().handleBroadcastMsg(model);
        }
    }

    @Override
    public void onGetImDetailFinish(boolean isSuccess, ImModel imModel) {
        if (!isSuccess){
            handleFail();
            return;
        }
        MyClient.getMyClient().getImManager().handleBroadcastMsg(imModel);
    }

    @Override
    public void onGetImConditionFinish(boolean isSuccess, ImQuestionConditionModel model) {
        if (!isSuccess){
            handleFail();
            return;
        }
        if (queryTypeMap.get(officialQuestionType).equals(VALUE_ZS) && officialQuestionProv.equals(mContext.getString(R.string.prov_zhejiang))){
            MyClient.getMyClient().getImManager().requestQuestionSchEnroll(mContext.schId,"440000000000",-1,this);
            return;
        }
        imQuestionConditionModel = model;
        List<String> wenliList = new ArrayList<>();
        for (Integer wenli : imQuestionConditionModel.getWenliItemList()){
            wenliList.add(V2CovertUtils.convertWenliStr(wenli));
        }
        if (officialQuestionProv.equals(mContext.getString(R.string.prov_zhejiang))){
            ImModel mo = new ImModel();
            mo.setType(IM_LEFT_9);
            MyClient.getMyClient().getImManager().handleBroadcastMsg(mo);
        }else{
            ImModel mo = new ImModel();
            mo.setType(IM_LEFT_8);
            mo.setInfoList(wenliList);
            MyClient.getMyClient().getImManager().handleBroadcastMsg(mo);
        }
    }

    @Override
    public void onGetImMajorsFinish(boolean isSuccess, HashMap<String,ImMajorModel.ImMajorItem> majorItemHashMap) {
        if (!isSuccess){
            handleFail();
            return;
        }
        if (majorItemHashMap.isEmpty()){
            handleFail();
            return;
        }
        List<String> infoList = new ArrayList<>(majorItemHashMap.keySet());
        ImModel imModel = new ImModel();
        imModel.setType(IM_LEFT_10);
        imModel.setInfoList(infoList);
        MyClient.getMyClient().getImManager().handleBroadcastMsg(imModel);
    }

    @Override
    public void onGetImEnrollFinish(boolean isSuccess, ImSchEnrollModel model) {
        if (!isSuccess){
            handleFail();
            return;
        }
        if (model.getSchEnrollItemList().isEmpty()){
            handleFail();
            return;
        }
        // TODO: 2018/9/18 根据类型把数据列表整理成表格型文字
        String content = "挖掘技术哪家强";
        for (ImSchEnrollModel.ImSchEnrollItem item : model.getSchEnrollItemList()){
            content += item.getEnrollYear();
        }
        ImModel imModel = new ImModel();
        imModel.setType(IM_LEFT_4);
        imModel.setOriginQuestion(officialQuestionName);
        imModel.setContent(content);
        handleReqMatchQuestion(imModel);
        closeOfficialReply();
    }

}
