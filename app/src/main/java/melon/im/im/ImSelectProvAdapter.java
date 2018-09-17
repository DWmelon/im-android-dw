package melon.im.im;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import melon.im.R;

public class ImSelectProvAdapter extends BaseAdapter {

    private Context mContext;
    private String[] dataList;

    public ImSelectProvAdapter(Context context){
        mContext = context;
        dataList = context.getResources().getStringArray(R.array.all_province);
    }

    @Override
    public int getCount() {
        return dataList.length;
    }

    @Override
    public Object getItem(int position) {
        return dataList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.layout_im_select_text_prov,parent,false);
        tv.setText((String)getItem(position));
        return tv;
    }

    public String[] getDataList() {
        return dataList;
    }

}
