package melon.im.im;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static melon.im.base.Constant.*;

public class TempUtil {

    public static List<ImModel> getImList1(){
        List<ImModel> modelList = new ArrayList<>();

        ImModel model = new ImModel();

        model = new ImModel();
        model.setContent("测试15");
        model.setType(IM_RIGHT_1);
        model.setTime(1536010329000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试14");
        model.setType(IM_LEFT_1);
        model.setTime(1536020529000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试13");
        model.setType(IM_RIGHT_1);
        model.setTime(1536030829000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试12");
        model.setType(IM_RIGHT_1);
        model.setTime(1536040929000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试11");
        model.setType(IM_LEFT_1);
        model.setTime(1536051029000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试10");
        model.setType(IM_LEFT_1);
        model.setTime(1536091129000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试9");
        model.setType(IM_RIGHT_1);
        model.setTime(1536111229000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试8");
        model.setType(IM_LEFT_1);
        model.setTime(1536121329000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试7");
        model.setType(IM_LEFT_1);
        model.setTime(1536141429000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试6");
        model.setType(IM_RIGHT_1);
        model.setTime(1536151529000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试5");
        model.setType(IM_LEFT_1);
        model.setTime(1536161629000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试4");
        model.setType(IM_RIGHT_1);
        model.setTime(1536181729000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试3");
        model.setType(IM_RIGHT_1);
        model.setTime(1536221759000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试2");
        model.setType(IM_LEFT_1);
        model.setTime(1536241779000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试1");
        model.setType(IM_LEFT_1);
        model.setTime(1536291789000L);
        modelList.add(model);
        return modelList;
    }

    public static List<ImModel> getImList2(){
        List<ImModel> modelList = new ArrayList<>();

        ImModel model = new ImModel();

        model = new ImModel();
        model.setContent("测试30");
        model.setType(IM_LEFT_1);
        model.setTime(1535030529000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试29");
        model.setType(IM_LEFT_1);
        model.setTime(1535050729000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试28");
        model.setType(IM_RIGHT_1);
        model.setTime(1535060829000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试27");
        model.setType(IM_RIGHT_1);
        model.setTime(1535070929000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试26");
        model.setType(IM_LEFT_1);
        model.setTime(1535081029000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试25");
        model.setType(IM_LEFT_1);
        model.setTime(1535091129000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试24");
        model.setType(IM_RIGHT_1);
        model.setTime(1535111229000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试23");
        model.setType(IM_LEFT_1);
        model.setTime(1535151329000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试22");
        model.setType(IM_LEFT_1);
        model.setTime(1535211429000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试21");
        model.setType(IM_RIGHT_1);
        model.setTime(1535221529000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试20");
        model.setType(IM_LEFT_1);
        model.setTime(1535251629000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试19");
        model.setType(IM_RIGHT_1);
        model.setTime(1535261729000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试18");
        model.setType(IM_RIGHT_1);
        model.setTime(1535271759000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试17");
        model.setType(IM_LEFT_1);
        model.setTime(1535281779000L);
        modelList.add(model);

        model = new ImModel();
        model.setContent("测试16");
        model.setType(IM_LEFT_1);
        model.setTime(1535291789000L);
        modelList.add(model);
        return modelList;
    }

    public static List<ImModel> handleListForTime(List<ImModel> modelList){
        Collections.sort(modelList, new Comparator<ImModel>() {
            @Override
            public int compare(ImModel o1, ImModel o2) {
                if (o1.getTime() > o2.getTime()){
                    return 1;
                }else if (o1.getTime() < o2.getTime()){
                    return 1;
                }else {
                    return -1;
                }
            }
        });
        List<ImModel> realList = new ArrayList<>();
        for (int i = modelList.size()-1;i >= 0;i--){
            ImModel imModel = modelList.get(i);
            ImModel timeModel;
            if (i == 0){
                realList.add(0,imModel);
                timeModel = new ImModel();
                timeModel.setTime(imModel.getTime());
                timeModel.setType(TYPE_TIME);
                realList.add(0,timeModel);
            }else{
                ImModel bModel = modelList.get(i-1);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTimeInMillis(bModel.getTime());
                calendar1.add(Calendar.MINUTE, ImAdapter.LIMIT_TIME_MINUTE);

                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTimeInMillis(imModel.getTime());

                if (calendar1.before(calendar2)){
                    realList.add(0,imModel);
                    timeModel = new ImModel();
                    timeModel.setTime(imModel.getTime());
                    timeModel.setType(TYPE_TIME);
                    realList.add(0,timeModel);
                }else{
                    realList.add(0,imModel);
                }
            }
        }
        return realList;
    }

}
