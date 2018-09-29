package melon.im.im;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import melon.im.R;


public class ImSelectBatchAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> dataList;

    public ImSelectBatchAdapter(Context context, List<String> dataList){
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.layout_im_select_text_course_cm,parent,false);
        tv.setText((String)getItem(position));
        return tv;
    }

    public List<String> getDataList() {
        return dataList;
    }

}
