package melon.im.im.listener;

import java.util.HashMap;

import melon.im.im.model.ImMajorModel;

public interface OnGetImMajorsListener {

    void onGetImMajorsFinish(boolean isSuccess, HashMap<String, ImMajorModel.ImMajorItem> majorItemHashMap);

}
