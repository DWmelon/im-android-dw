package melon.im.im;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class ImRecyclerScrollListener extends RecyclerView.OnScrollListener {

    //声明一个LinearLayoutManager
    private LinearLayoutManager mLinearLayoutManager;

    public ImRecyclerScrollListener(LinearLayoutManager mLinearLayoutManager) {
        this.mLinearLayoutManager = mLinearLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);


        //停留在了头部，判断并执行相关操作
        if (newState == RecyclerView.SCROLL_STATE_IDLE && mLinearLayoutManager.findFirstVisibleItemPosition() == 0){
            int y = (int) mLinearLayoutManager.findViewByPosition(0).getY();
            //滑动到了顶部，需要触发"拉取更多"操作
            if (y == 0){
                if (((ImRecyclerView)recyclerView).isNeedLoad && ((ImRecyclerView)recyclerView).notifyLoadMore()){
                    return;
                }
            }

            //没有滑动到顶部，执行回弹
            ((ImRecyclerView)recyclerView).scrollToFirstView();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //在自由滑动过程中，触及头部，则回弹
        if (mLinearLayoutManager.findFirstVisibleItemPosition() == 0){
            if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING){
                ((ImRecyclerView)recyclerView).scrollToFirstView();
            }
        }

    }
}
