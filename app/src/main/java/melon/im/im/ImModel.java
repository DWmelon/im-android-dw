package melon.im.im;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImModel implements Serializable {

    private static final long serialVersionUID = -7241066030663820788L;
    private String questionId;
    private int type;
    private int logo;
    private String name;
    private String content = "";
    private String detail;
    private String originQuestion;
    private String imgUrl;
    private boolean isAds;
    private boolean isMe;
    private long time;
    private List<String> infoList = new ArrayList<>();

    public String getOriginQuestion() {
        return originQuestion;
    }

    public void setOriginQuestion(String originQuestion) {
        this.originQuestion = originQuestion;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isAds() {
        return isAds;
    }

    public void setAds(boolean ads) {
        isAds = ads;
    }

    public List<String> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<String> infoList) {
        this.infoList = infoList;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
