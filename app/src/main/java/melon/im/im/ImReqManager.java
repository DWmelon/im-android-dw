package melon.im.im;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import melon.im.MyClient;
import melon.im.im.listener.OnGetImConditionListener;
import melon.im.im.listener.OnGetImDetailListener;
import melon.im.im.listener.OnGetImEnrollListener;
import melon.im.im.listener.OnGetImHQListener;
import melon.im.im.listener.OnGetImMajorsListener;
import melon.im.im.listener.OnGetImMatchListener;
import melon.im.im.model.ImHotQuestionModel;
import melon.im.im.model.ImMajorModel;
import melon.im.im.model.ImMatchQuestionModel;
import melon.im.im.model.ImQuestionConditionModel;
import melon.im.im.model.ImSchEnrollModel;
import melon.im.network.IRequest;
import melon.im.network.IRequestCallback;
import melon.im.util.V2ArrayUtil;

public class ImReqManager {

    private static final String URL_QUESTION_HOT = "http://m.wmzy.com/api_v2/consultant/sch_hot_question_list";
    private static final String URL_QUESTION_MATCH = "http://m.wmzy.com/api_v2/consultant/sch_question_matching";
    private static final String URL_QUESTION_FIND_BY_ID = "http://m.wmzy.com/api_v2/consultant/sch_question_info";
    private static final String URL_QUESTION_SOLVE_NOTIFY = "http://m.wmzy.com/api_v2/consultant/mark_user_question_handled";
    private static final String URL_QUESTION_CONDITION = "http://m.wmzy.com/api_v2/consultant/sch_items";
    private static final String URL_QUESTION_SCH_ENROLL = "http://m.wmzy.com/api_v2/consultant/sch_enroll";
    private static final String URL_QUESTION_SCH_SCORE = "http://m.wmzy.com/api_v2/consultant/sch_score_list";
    private static final String URL_QUESTION_SCH_ENROLL_MAJORS = "http://m.wmzy.com/api_v2/consultant/sch_enroll_majors";

    private HashMap<String,ImHotQuestionModel> imHotQuestionModelMap = new HashMap<>();
    private HashMap<String,ImMatchQuestionModel> imMatchQuestionModelMap = new HashMap<>();

    public void requestQuestionHot(String schId, final OnGetImHQListener listener) {

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("sch_id",schId);
        map.put("top_n","3");
        request.sendRequestForGetWithJson(URL_QUESTION_HOT, map, new IRequestCallback() {
            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (jsonObject == null){
                    if (listener != null){
                        listener.onGetImHQFinish(false);
                    }
                    return;
                }

                if (jsonObject.getIntValue("code") != 0){
                    if (listener != null){
                        listener.onGetImHQFinish(false);
                    }
                    return;
                }

                JSONArray array = jsonObject.getJSONArray("data");
                if (array == null){
                    if (listener != null){
                        listener.onGetImHQFinish(false);
                    }
                    return;
                }

                imHotQuestionModelMap.clear();
                for (int i = 0;i < array.size();i++){
                    ImHotQuestionModel model = new ImHotQuestionModel();
                    model.decode(array.getJSONObject(i));
                    imHotQuestionModelMap.put(model.getQuestion(),model);
                }
                listener.onGetImHQFinish(true);
            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                if (listener!=null){
                    listener.onGetImHQFinish(false);
                }
            }
        });
    }

    public void requestQuestionMatch(String schId, String question, final ImModel model, final OnGetImMatchListener listener) {

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("sch_id",schId);
        map.put("user_q",question);
        request.sendRequestForGetWithJson(URL_QUESTION_MATCH, map, new IRequestCallback() {
            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (jsonObject == null){
                    if (listener != null){
                        listener.onGetImMatchFinish(false,null,null,-1);
                    }
                    return;
                }

                if (jsonObject.getIntValue("code") != 0){
                    if (listener != null){
                        listener.onGetImMatchFinish(false,null,null,-1);
                    }
                    return;
                }

                JSONArray array = jsonObject.getJSONArray("data");
                if (array == null){
                    if (listener != null){
                        listener.onGetImMatchFinish(false,null,null,-1);
                    }
                    return;
                }

                List<String> questionList = new ArrayList<>();
                String firstMatchId = null;
                double firstMatchRatio = -1;
                for (int i = 0;i < array.size();i++){
                    ImMatchQuestionModel model = new ImMatchQuestionModel();
                    model.decode(array.getJSONObject(i));
                    imMatchQuestionModelMap.put(model.getQuestion(),model);
                    questionList.add(model.getQuestion());
                    if (i == 0){
                        firstMatchId = model.getQuestionId();
                        firstMatchRatio = model.getMatchingRatio();
                    }
                }
                model.setInfoList(questionList);
                listener.onGetImMatchFinish(true,model,firstMatchId,firstMatchRatio);
            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                if (listener!=null){
                    listener.onGetImMatchFinish(false,null,null,-1);
                }
            }
        });
    }

    public void requestQuestionById(String schId, final String questionId, final ImModel model, final OnGetImDetailListener listener) {

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("sch_id",schId);
        map.put("question_id",questionId);
        request.sendRequestForGetWithJson(URL_QUESTION_FIND_BY_ID, map, new IRequestCallback() {
            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (jsonObject == null){
                    if (listener != null){
                        listener.onGetImDetailFinish(false,null);
                    }
                    return;
                }

                if (jsonObject.getIntValue("code") != 0){
                    if (listener != null){
                        listener.onGetImDetailFinish(false,null);
                    }
                    return;
                }

                JSONObject object = jsonObject.getJSONObject("data");
                if (object == null){
                    if (listener != null){
                        listener.onGetImDetailFinish(false,null);
                    }
                    return;
                }

                ImHotQuestionModel mo = new ImHotQuestionModel();
                mo.decode(object);
                model.setQuestionId(questionId);
                model.setContent(mo.getAnswer());
                listener.onGetImDetailFinish(true,model);
            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                if (listener!=null){
                    listener.onGetImDetailFinish(false,null);
                }
            }
        });
    }

    public void requestQuestionSolve(String schId, final String questionId, final String originQuestion,boolean isSolve) {

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        // TODO: 2018/9/18
        map.put("token","");
        map.put("sch_id",schId);
        map.put("question_id",questionId);
        map.put("user_q",originQuestion);
        map.put("is_handled",isSolve+"");
        request.sendRequestForGetWithJson(URL_QUESTION_SOLVE_NOTIFY, map, new IRequestCallback() {
            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
            }
        });
    }

    public void requestQuestionCondition(String schId, String provinceId, int queryType, final OnGetImConditionListener listener) {

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("sch_id",schId);
        map.put("province_id",provinceId);
        map.put("query_type",String.valueOf(queryType));//1-从招生计划里取，2-从录取数据里去（不传的话默认从录取数据里取）

        request.sendRequestForGetWithJson(URL_QUESTION_CONDITION, map, new IRequestCallback() {
            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (jsonObject == null){
                    if (listener != null){
                        listener.onGetImConditionFinish(false,null);
                    }
                    return;
                }

                ImQuestionConditionModel model = new ImQuestionConditionModel();
                model.decode(jsonObject);
                if (listener!=null){
                    listener.onGetImConditionFinish(model.getCode()==0,model);
                }

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                if (listener!=null){
                    listener.onGetImConditionFinish(false,null);
                }
            }
        });
    }

    public void requestQuestionSchEnroll(String schId, String provinceId, int wenli, final OnGetImEnrollListener listener) {

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("sch_id",schId);
        map.put("province_id",provinceId);
        if (wenli != -1){
            map.put("wenli",String.valueOf(wenli));
        }

        request.sendRequestForGetWithJson(URL_QUESTION_SCH_ENROLL, map, new IRequestCallback() {
            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (jsonObject == null){
                    if (listener != null){
                        listener.onGetImEnrollFinish(false,null);
                    }
                    return;
                }

                ImSchEnrollModel model = new ImSchEnrollModel();
                model.decode(jsonObject);
                if (listener!=null){
                    listener.onGetImEnrollFinish(model.getCode()==0,model);
                }

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                if (listener!=null){
                    listener.onGetImEnrollFinish(false,null);
                }
            }
        });
    }

    public void requestQuestionSchScore(String schId, String provinceId, int wenli,int batch,String majorId, final OnGetImEnrollListener listener) {

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("sch_id",schId);
        map.put("province_id",provinceId);
        if (wenli != -1){
            map.put("wenli",String.valueOf(wenli));
        }
        if (batch != -1){
            map.put("batch",String.valueOf(batch));
        }
        if (!TextUtils.isEmpty(majorId)){
            map.put("major_id",majorId);
        }

        request.sendRequestForGetWithJson(URL_QUESTION_SCH_SCORE, map, new IRequestCallback() {
            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (jsonObject == null){
                    if (listener != null){
                        listener.onGetImEnrollFinish(false,null);
                    }
                    return;
                }

                ImSchEnrollModel model = new ImSchEnrollModel();
                model.decode(jsonObject);
                if (listener!=null){
                    listener.onGetImEnrollFinish(model.getCode()==0,model);
                }

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                if (listener!=null){
                    listener.onGetImEnrollFinish(false,null);
                }
            }
        });
    }

    public void requestQuestionSchEnrollMajors(String schId, String provinceId,List<String> courseList, final OnGetImMajorsListener listener) {

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("sch_id",schId);
        map.put("province_id",provinceId);
        map.put("course_ids", V2ArrayUtil.getJsonArrData(courseList));

        request.sendRequestForGetWithJson(URL_QUESTION_SCH_ENROLL_MAJORS, map, new IRequestCallback() {
            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (jsonObject == null){
                    if (listener != null){
                        listener.onGetImMajorsFinish(false,null);
                    }
                    return;
                }

                HashMap<String,ImMajorModel.ImMajorItem> majorItemHashMap = new HashMap<>();
                ImMajorModel model = new ImMajorModel();
                model.decode(jsonObject);
                if (model.getCode() == 0){
                    for (ImMajorModel.ImMajorItem item : model.getImMajorItemList()){
                        majorItemHashMap.put(item.getMajorName(),item);
                    }
                }
                if (listener!=null){
                    listener.onGetImMajorsFinish(model.getCode()==0,majorItemHashMap);
                }

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                if (listener!=null){
                    listener.onGetImMajorsFinish(false,null);
                }
            }
        });
    }

    public HashMap<String, ImHotQuestionModel> getImHotQuestionModelMap() {
        return imHotQuestionModelMap;
    }

    public HashMap<String, ImMatchQuestionModel> getImMatchQuestionModelMap() {
        return imMatchQuestionModelMap;
    }
}
