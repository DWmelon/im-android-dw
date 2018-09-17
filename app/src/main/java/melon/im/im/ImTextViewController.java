package melon.im.im;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import melon.im.base.MyClient;

public class ImTextViewController implements View.OnClickListener {

    private EditText mEtInput;

    public ImTextViewController(EditText mEtInput) {
        this.mEtInput = mEtInput;
    }

    @Override
    public void onClick(View v) {
        String content = mEtInput.getText().toString();
        if (TextUtils.isEmpty(content)){
            return;
        }
        MyClient.getMyClient().getImManager().sendMessage(content);
        mEtInput.setText("");
    }

}
