package melon.im.im.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import melon.im.BaseModel;

public class ImSchEnrollModel extends BaseModel {

    private List<ImSchEnrollItem> schEnrollItemList = new ArrayList<>();

    public void decode(JSONObject object){
        super.decode(object);
        if (object == null){
            return;
        }
        if (getCode() != 0){
            return;
        }
        JSONArray array = object.getJSONArray(KEY_DATA);
        if (array == null){
            return;
        }
        for (int i = 0;i < array.size();i++){
            ImSchEnrollItem item = new ImSchEnrollItem();
            item.decode(array.getJSONObject(i));
            schEnrollItemList.add(item);
        }
    }

    public static class ImSchEnrollItem{
        private final String KEY_PROVINCE_ID = "province_id";//"440000000000"
        private final String KEY_WENLI = "wenli";//2
        private final String KEY_ENROLL_YEAR = "enroll_year";//2015
        private final String KEY_BATCH = "batch";//2
        private final String KEY_BATCH_EX = "batch_ex";//"A"
        private final String KEY_SCH_ID = "sch_id";//"52ac2e97747aec013fcf49e3"
        private final String KEY_ENROLL_SCH_TYPE = "enroll_sch_type";//"深圳大学"
        private final String KEY_ENROLL_SCH_CODE_PROV = "enroll_sch_code_prov";//"10955"
        private final String KEY_ENROLL_SCH_TYPE_ID = "enroll_sch_type_id";//"52ac2e97747aec013fcf49e3"
        private final String KEY_ENROLL_PLAN_COUNT = "enroll_plan_count";//1350
        private final String KEY_SELECT_1 = "select1";//"-"
        private final String KEY_SELECT_2 = "select2";//"-"

        private final String KEY_PEOPLE_COUNT = "people_count";//2703
        private final String KEY_MIN_SCORE = "min_score";//546
        private final String KEY_MAX_SCORE = "max_score";//-1
        private final String KEY_AVG_SCORE = "avg_score";//-1
        private final String KEY_BATCH_SCORE = "batch_score";//null
        private final String KEY_MIN_SCORE_RANK = "min_score_rank";//null

        private String provinceId;
        private int wenli;
        private int enrollYear;
        private int batch;
        private String batchEx;
        private String schId;
        private String enrollSchType;
        private String enrollSchCodeProv;
        private String enrollSchTypeId;
        private int enrollPlanCount;
        private String select1;
        private String select2;

        private int peopleCount;
        private int minScore;
        private int maxScore;
        private int avgScore;
        private Integer batchScore;

        public void decode(JSONObject object){
            if (object == null){
                return;
            }
            provinceId = object.getString(KEY_PROVINCE_ID);
            wenli = object.getIntValue(KEY_WENLI);
            enrollYear = object.getIntValue(KEY_ENROLL_YEAR);
            batch = object.getIntValue(KEY_BATCH);
            batchEx = object.getString(KEY_BATCH_EX);
            schId = object.getString(KEY_SCH_ID);
            enrollSchType = object.getString(KEY_ENROLL_SCH_TYPE);
            enrollSchCodeProv = object.getString(KEY_ENROLL_SCH_CODE_PROV);
            enrollSchTypeId = object.getString(KEY_ENROLL_SCH_TYPE_ID);
            enrollPlanCount = object.getIntValue(KEY_ENROLL_PLAN_COUNT);
            select1 = object.getString(KEY_SELECT_1);
            select2 = object.getString(KEY_SELECT_2);

            peopleCount = object.getIntValue(KEY_PEOPLE_COUNT);
            minScore = object.getIntValue(KEY_MIN_SCORE);
            maxScore = object.getIntValue(KEY_MAX_SCORE);
            avgScore = object.getIntValue(KEY_AVG_SCORE);
            batchScore = object.getInteger(KEY_BATCH_SCORE);
        }

        public int getPeopleCount() {
            return peopleCount;
        }

        public void setPeopleCount(int peopleCount) {
            this.peopleCount = peopleCount;
        }

        public int getMinScore() {
            return minScore;
        }

        public void setMinScore(int minScore) {
            this.minScore = minScore;
        }

        public int getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(int maxScore) {
            this.maxScore = maxScore;
        }

        public int getAvgScore() {
            return avgScore;
        }

        public void setAvgScore(int avgScore) {
            this.avgScore = avgScore;
        }

        public Integer getBatchScore() {
            return batchScore;
        }

        public void setBatchScore(Integer batchScore) {
            this.batchScore = batchScore;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public int getWenli() {
            return wenli;
        }

        public void setWenli(int wenli) {
            this.wenli = wenli;
        }

        public int getEnrollYear() {
            return enrollYear;
        }

        public void setEnrollYear(int enrollYear) {
            this.enrollYear = enrollYear;
        }

        public int getBatch() {
            return batch;
        }

        public void setBatch(int batch) {
            this.batch = batch;
        }

        public String getBatchEx() {
            return batchEx;
        }

        public void setBatchEx(String batchEx) {
            this.batchEx = batchEx;
        }

        public String getSchId() {
            return schId;
        }

        public void setSchId(String schId) {
            this.schId = schId;
        }

        public String getEnrollSchType() {
            return enrollSchType;
        }

        public void setEnrollSchType(String enrollSchType) {
            this.enrollSchType = enrollSchType;
        }

        public String getEnrollSchCodeProv() {
            return enrollSchCodeProv;
        }

        public void setEnrollSchCodeProv(String enrollSchCodeProv) {
            this.enrollSchCodeProv = enrollSchCodeProv;
        }

        public String getEnrollSchTypeId() {
            return enrollSchTypeId;
        }

        public void setEnrollSchTypeId(String enrollSchTypeId) {
            this.enrollSchTypeId = enrollSchTypeId;
        }

        public int getEnrollPlanCount() {
            return enrollPlanCount;
        }

        public void setEnrollPlanCount(int enrollPlanCount) {
            this.enrollPlanCount = enrollPlanCount;
        }

        public String getSelect1() {
            return select1;
        }

        public void setSelect1(String select1) {
            this.select1 = select1;
        }

        public String getSelect2() {
            return select2;
        }

        public void setSelect2(String select2) {
            this.select2 = select2;
        }
    }

    public List<ImSchEnrollItem> getSchEnrollItemList() {
        return schEnrollItemList;
    }

    public void setSchEnrollItemList(List<ImSchEnrollItem> schEnrollItemList) {
        this.schEnrollItemList = schEnrollItemList;
    }
}
