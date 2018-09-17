package melon.im.im;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ImEditTextController implements View.OnClickListener {

    private RecyclerView mRvList;

    public ImEditTextController(RecyclerView mRvList) {
        this.mRvList = mRvList;
    }

    @Override
    public void onClick(View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRvList.scrollToPosition(mRvList.getAdapter().getItemCount()-1);
            }
        },100);
    }

}
