package melon.im.im.listener;

import melon.im.im.model.ImQuestionConditionModel;

public interface OnGetImConditionListener {

    void onGetImConditionFinish(boolean isSuccess, ImQuestionConditionModel model);

}
