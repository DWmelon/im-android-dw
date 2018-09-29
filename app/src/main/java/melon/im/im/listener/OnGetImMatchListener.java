package melon.im.im.listener;

import melon.im.im.ImModel;

public interface OnGetImMatchListener {

    void onGetImMatchFinish(boolean isSuccess, ImModel imModel, String firstMatchId, double firstMatchRatio);

}
