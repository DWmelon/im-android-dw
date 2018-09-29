package melon.im.im.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import melon.im.BaseModel;

public class ImQuestionConditionModel extends BaseModel {

    private final String KEY_DIPLOMA_ITEM = "diploma_item";
    private final String KEY_WENLI_ITEM = "wenli_item";
    private final String KEY_BATCH_ITEM = "batch_item";

    private List<Integer> diplomaItemList = new ArrayList<>();
    private List<Integer> wenliItemList = new ArrayList<>();
    private List<Integer> batchItemList = new ArrayList<>();

    public void decode(JSONObject object){
        super.decode(object);
        if (object == null){
            return;
        }

        object = object.getJSONObject(KEY_DATA);
        if (object == null){
            return;
        }

        JSONArray array = object.getJSONArray(KEY_DIPLOMA_ITEM);
        if (array!=null){
            for (int i = 0;i < array.size();i++){
                diplomaItemList.add(array.getInteger(i));
            }
        }

        array = object.getJSONArray(KEY_WENLI_ITEM);
        if (array!=null){
            for (int i = 0;i < array.size();i++){
                wenliItemList.add(array.getInteger(i));
            }
        }

        array = object.getJSONArray(KEY_BATCH_ITEM);
        if (array!=null){
            for (int i = 0;i < array.size();i++){
                batchItemList.add(array.getInteger(i));
            }
        }

    }

    public List<Integer> getDiplomaItemList() {
        return diplomaItemList;
    }

    public void setDiplomaItemList(List<Integer> diplomaItemList) {
        this.diplomaItemList = diplomaItemList;
    }

    public List<Integer> getWenliItemList() {
        return wenliItemList;
    }

    public void setWenliItemList(List<Integer> wenliItemList) {
        this.wenliItemList = wenliItemList;
    }

    public List<Integer> getBatchItemList() {
        return batchItemList;
    }

    public void setBatchItemList(List<Integer> batchItemList) {
        this.batchItemList = batchItemList;
    }
}
