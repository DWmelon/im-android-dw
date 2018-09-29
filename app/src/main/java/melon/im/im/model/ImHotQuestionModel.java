package melon.im.im.model;


import com.alibaba.fastjson.JSONObject;

public class ImHotQuestionModel {

    private final String KEY_QUESTION_ID = "question_id";//"5ab1ca928fdde4dc1fac4066"
    private final String KEY_SCH_ID = "sch_id";//"52ac2e97747aec013fcf49e3"
    private final String KEY_TYPE_ID = "type_id";//"1"
    private final String KEY_QUESTION = "question";//"报考贵校需要什么条件？"
    private final String KEY_ANSWER = "answer";//"相关政策详见我校当年招生章程，往年录取情况详见我校本科招生网http://zs.szu.edu.cn/index/。"
    private final String KEY_SHOW_TIME = "show_count";//6
    private final String KEY_C_TIME = "create_time";//"1521601087000"
    private final String KEY_M_TIME = "modify_time";//"1521601087000"

    private String questionId;
    private String schId;
    private String typeId;
    private String question;
    private String answer;
    private int showCount;
    private long cTime;
    private long mTime;

    public void decode(JSONObject object){
        if (object == null){
            return;
        }

        questionId = object.getString(KEY_QUESTION_ID);
        schId = object.getString(KEY_SCH_ID);
        typeId = object.getString(KEY_TYPE_ID);
        question = object.getString(KEY_QUESTION);
        answer = object.getString(KEY_ANSWER);
        showCount = object.getIntValue(KEY_SHOW_TIME);
        cTime = object.getLongValue(KEY_C_TIME);
        mTime = object.getLongValue(KEY_M_TIME);

    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getSchId() {
        return schId;
    }

    public void setSchId(String schId) {
        this.schId = schId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getShowCount() {
        return showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public long getcTime() {
        return cTime;
    }

    public void setcTime(long cTime) {
        this.cTime = cTime;
    }

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }
}
