package melon.im.im;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

import melon.im.R;


public class ImSelectCourseZjAdapter extends BaseAdapter {

    private Context mContext;
    private String[] dataList;
    private HashMap<Integer,View> selectMap;

    public ImSelectCourseZjAdapter(Context context){
        mContext = context;
        dataList = context.getResources().getStringArray(R.array.zj_course);
        selectMap = new HashMap<>();
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

        if (convertView == null){
            View view =  LayoutInflater.from(mContext).inflate(R.layout.layout_im_select_text_course_zj,parent,false);
            TextView tv = (TextView) view;
            tv.setText((String)getItem(position));
            view.setEnabled(false);
            convertView = view;
        }

        return convertView;
    }

    public String[] getDataList() {
        return dataList;
    }

    public HashMap<Integer, View> getSelectMap() {
        return selectMap;
    }
}
