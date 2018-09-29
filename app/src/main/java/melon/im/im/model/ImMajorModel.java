package melon.im.im.model;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import melon.im.BaseModel;

public class ImMajorModel extends BaseModel{

    private List<ImMajorItem> imMajorItemList = new ArrayList<>();

    public void decode(JSONObject object){
        super.decode(object);
        if (object == null){
            return;
        }
        JSONArray array = object.getJSONArray(KEY_DATA);
        if (array == null){
            return;
        }
        for (int i = 0;i < array.size();i++){
            ImMajorItem item = new ImMajorItem();
            item.decode(array.getJSONObject(i));
            imMajorItemList.add(item);
        }
    }

    public static class ImMajorItem{
        private final String KEY_MAJOR_ID = "major_id";//"52aedf5b747aec1cfc8415c8"
        private final String KEY_MAJOR_NAME = "major_name";//"英语"

        private String majorId;
        private String majorName;

        public void decode(JSONObject object){
            if (object == null){
                return;
            }
            majorId = object.getString(KEY_MAJOR_ID);
            majorName = object.getString(KEY_MAJOR_NAME);
        }

        public String getMajorId() {
            return majorId;
        }

        public void setMajorId(String majorId) {
            this.majorId = majorId;
        }

        public String getMajorName() {
            return majorName;
        }

        public void setMajorName(String majorName) {
            this.majorName = majorName;
        }
    }

    public List<ImMajorItem> getImMajorItemList() {
        return imMajorItemList;
    }

    public void setImMajorItemList(List<ImMajorItem> imMajorItemList) {
        this.imMajorItemList = imMajorItemList;
    }
}
