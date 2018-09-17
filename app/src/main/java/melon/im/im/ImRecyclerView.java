package melon.im.im;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import melon.im.R;


public class ImRecyclerView extends RecyclerView {

    /**
     * 列表滑动速度，不宜太快，定一个默认值
     */
    private float scale = 0.5f;

    /**
     * 顶部"加载更多"高度
     */
    private int barHeight;

    /**
     * 滑动监听，用于处理滑动到顶部的回弹和加载处理
     */
    private ImRecyclerScrollListener imRecyclerScrollListener;

    /**
     * 加载监听，处理加载事件
     */
    private OnImLoadMoreListener onImLoadMoreListener;

    /**
     * 是否开启"加载更多"
     */
    public boolean isNeedLoad = false;

    /**
     * 是否锁住列表，不能滑动或点击（加载数据时）
     */
    public boolean isLockView = false;

    public ImRecyclerView(Context context) {
        super(context);
        init();
    }

    public ImRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        barHeight = getResources().getDimensionPixelOffset(R.dimen.height_im_header_more);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(linearLayoutManager);

        imRecyclerScrollListener = new ImRecyclerScrollListener(linearLayoutManager);
        addOnScrollListener(imRecyclerScrollListener);



    }

    public void setNeedLoad(boolean needLoad) {
        isNeedLoad = needLoad;
        if (!isNeedLoad){
            if (getAdapter().getItemCount()>0){
                try {
                    ImAdapter.ViewHolderHeader viewHolder = (ImAdapter.ViewHolderHeader) findViewHolderForAdapterPosition(0);
                    if (viewHolder != null){
                        viewHolder.mTvContent.setText("");
                    }
                }catch (Exception e){
                }
            }
        }
    }

    public void setScale(float value){
        if (value >0.5f){
            value = 0.5f;
        }
        scale = value;
    }

    public void setOnImLoadMoreListener(OnImLoadMoreListener listener){
        onImLoadMoreListener = listener;
    }

    public boolean notifyLoadMore(){
        if (onImLoadMoreListener == null){
            return false;
        }
        isLockView = true;
        onImLoadMoreListener.onImLoadMore();
        return true;
    }

    public void notifyLoadFinish(){
        scrollToFirstView();
        isLockView = false;
    }

    public void scrollToFirstView(){
        LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) getLayoutManager();
        if (mLinearLayoutManager.findFirstVisibleItemPosition() != 0){
            return;
        }
        int y = (int) mLinearLayoutManager.findViewByPosition(0).getY();
        smoothScrollBy(0,barHeight+y);
    }

    private boolean isNeedHideKeyWord(float xFrom,float yFrom,float xTo,float yTo){
        float xDiff = Math.abs(xTo-xFrom);
        float yDiff = Math.abs(yTo-yFrom);
        return xDiff < 10 && yDiff < 10;
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityY *= scale;
        return super.fling(velocityX, velocityY);
    }

    float x,y;
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:{
                x = e.getX();
                y = e.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                break;
            }
            case MotionEvent.ACTION_UP:{
                if (isNeedHideKeyWord(x,y,e.getX(),e.getY())){
                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null){
                        inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(),0);
                    }
                }
                break;
            }
        }

        return super.onTouchEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return isLockView || super.dispatchTouchEvent(ev);
    }
}
