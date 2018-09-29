package melon.im.im.model;

import com.alibaba.fastjson.JSONObject;

public class ImMatchQuestionModel {

    private final String KEY_QUESTION_ID = "question_id";//"5ab1ca928fdde4dc1fac413e"
    private final String KEY_QUESTION = "question";//"宿舍是几人一间？"
    private final String KEY_MATCHING_RATIO = "matching_ratio";//30.79505205154419

    private String questionId;
    private String question;
    private double matchingRatio;

    public void decode(JSONObject object){
        if (object == null){
            return;
        }
        questionId = object.getString(KEY_QUESTION_ID);
        question = object.getString(KEY_QUESTION);
        matchingRatio = object.getDoubleValue(KEY_MATCHING_RATIO);
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public double getMatchingRatio() {
        return matchingRatio;
    }

    public void setMatchingRatio(double matchingRatio) {
        this.matchingRatio = matchingRatio;
    }
}
