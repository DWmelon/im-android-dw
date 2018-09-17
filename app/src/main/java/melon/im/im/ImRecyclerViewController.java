package melon.im.im;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.List;

import static melon.im.base.Constant.TYPE_HEADER;
public class ImRecyclerViewController implements OnImLoadMoreListener {

    private final int VALUE_LOAD_PAGE_COUNT = 15;

    private Context mContext;
    private ImRecyclerView mRvList;
    private ImAdapter adapter;

    public ImRecyclerViewController(Context context, ImRecyclerView view) {
        mContext = context;
        mRvList = view;
        init();
    }

    public void init(){
        List<ImModel> modelList = TempUtil.getImList1();
        boolean isNeedLoadMore = modelList.size() == VALUE_LOAD_PAGE_COUNT;
        modelList = TempUtil.handleListForTime(modelList);
        if (isNeedLoadMore){
            mRvList.setNeedLoad(true);
            ImModel headerModel = new ImModel();
            headerModel.setType(TYPE_HEADER);
            headerModel.setContent("加载更多");
            modelList.add(0,headerModel);
        }
        // TODO: 2018/9/6 读取数据库，历史聊天记录

        adapter = new ImAdapter(mContext,mRvList,modelList,isNeedLoadMore);
        mRvList.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRvList.scrollToPosition(adapter.getItemCount()-1);
            }
        },300);

    }

    @Override
    public void onImLoadMore() {
        Toast.makeText(mContext,"正在加载更多",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ImModel> modelList = TempUtil.getImList2();
                boolean isNeedLoadMore = modelList.size() == VALUE_LOAD_PAGE_COUNT;
                mRvList.setNeedLoad(isNeedLoadMore);
                modelList = TempUtil.handleListForTime(modelList);
                adapter.addImList(modelList);
                mRvList.notifyLoadFinish();
            }
        },2000);
    }

    public void handleAddMsg(ImModel imModel) {
        if (adapter == null){
            return;
        }
        adapter.addIm(imModel);
    }

}
