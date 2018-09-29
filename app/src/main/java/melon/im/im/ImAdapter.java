package melon.im.im;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import melon.im.MyClient;
import melon.im.R;
import melon.im.UrlConstantV2;
import melon.im.im.listener.OnImQuestionSolveListener;
import melon.im.im.listener.OnImSelectListener;

import static melon.im.UrlConstantV2.VALUE.IM_LEFT_1;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_10;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_11;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_2;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_3;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_4;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_5;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_6;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_7;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_8;
import static melon.im.UrlConstantV2.VALUE.IM_LEFT_9;
import static melon.im.UrlConstantV2.VALUE.IM_PUSH_MSG;
import static melon.im.UrlConstantV2.VALUE.IM_RIGHT_1;
import static melon.im.UrlConstantV2.VALUE.TYPE_HEADER;
import static melon.im.UrlConstantV2.VALUE.TYPE_TIME;


public class ImAdapter extends RecyclerView.Adapter<ImAdapter.ViewHolder> {


    private final int TYPE_MSG_TIP = 102;

    private final HashSet<Integer> SET_TIME_IGNORE = new HashSet<Integer>(){{add(TYPE_HEADER);add(TYPE_TIME);add(TYPE_MSG_TIP);}};

    public  static final int LIMIT_TIME_MINUTE = 5;

    private Context mContext;
    private List<ImModel> mDataList;
    private RecyclerView mRvList;
    private boolean isFirstInsertTime = false;

    private ImSelectProvAdapter mAdapterProv;
    private ImSelectCourseCmAdapter mAdapterWl;
    private ImSelectCourseZjAdapter mAdapterZj;
    private ImSelectCourseCmAdapter mAdapterBatch;

    private boolean isHadHeader;

    private OnImSelectListener onImSelectListener;
    private OnImQuestionSolveListener onImQuestionSolveListener;

    public ImAdapter(Context context, RecyclerView mRvList, List<ImModel> list, boolean isHadHeader){
        this.mContext = context;
        this.mRvList = mRvList;
        this.mDataList = list;
        this.isHadHeader = isHadHeader;
        this.mAdapterProv = new ImSelectProvAdapter(context);
        this.mAdapterZj = new ImSelectCourseZjAdapter(context);
    }

    public void setOnImSelectListener(OnImSelectListener onImSelectListener) {
        this.onImSelectListener = onImSelectListener;
    }

    public void setOnImQuestionSolveListener(OnImQuestionSolveListener onImQuestionSolveListener) {
        this.onImQuestionSolveListener = onImQuestionSolveListener;
    }

    /**
     * 添加多个聊天记录
     * @param modelList
     */
    public void addImList(List<ImModel> modelList){
        mDataList.addAll(isHadHeader?1:0,modelList);
        notifyItemRangeInserted(isHadHeader?1:0,modelList.size());
    }

    /**
     * 添加单个聊天记录
     * @param model
     */
    public void addIm(ImModel model){
        judgeTimeInsert(model);
        mDataList.add(model);
        int position = mDataList.size()-1;
        notifyItemInserted(position);
//        notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRvList.smoothScrollToPosition(mDataList.size()-1);
            }
        },100);

    }

    /**
     * 判断是否要插入时间行
     * @param imModel
     */
    private void judgeTimeInsert(ImModel imModel){
        //如果是不需要插入时间的信息类型，则返回
        if (SET_TIME_IGNORE.contains(imModel.getType())){
            return;
        }
        if (!isFirstInsertTime){
            isFirstInsertTime = true;
            handleTimeInsert(imModel);
            return;
        }
        ImModel model = mDataList.get(mDataList.size()-1);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(model.getTime());

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(imModel.getTime());

        //跟上一个聊天记录对比，如果日期相等，则不用再次添加时间控件
//        if (calendar1.get(Calendar.MONTH)==calendar2.get(Calendar.MONTH)
//                &&calendar1.get(Calendar.DAY_OF_MONTH)==calendar2.get(Calendar.DAY_OF_MONTH)){
//
//            return;
//        }

        //跟上一个聊天记录对比，相距时间超过LIMIT_TIME_MINUTE，则添加时间显示UI
        calendar1.add(Calendar.MINUTE,LIMIT_TIME_MINUTE);
        if (calendar1.before(calendar2)){
            handleTimeInsert(imModel);
        }

    }

    /**
     * 插入时间行数据
     * @param imModel
     */
    private void handleTimeInsert(ImModel imModel){
        ImModel model = new ImModel();
        model.setType(TYPE_TIME);
        model.setTime(imModel.getTime());
        mDataList.add(model);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        switch (viewType){
            case TYPE_HEADER:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_header_more,parent,false);
                holder = new ViewHolderHeader(view);
                break;
            }
            case TYPE_TIME:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_time,parent,false);
                holder = new ViewHolderTime(view);
                break;
            }
            case IM_LEFT_1:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_left_1,parent,false);
                holder = new ViewHolderLeftOne(view);
                break;
            }
            case IM_LEFT_2:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_left_2,parent,false);
                holder = new ViewHolderLeftSecond(view);
                break;
            }
            case IM_LEFT_3:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_left_3,parent,false);
                holder = new ViewHolderLeftThird(view);
                break;
            }
            case IM_LEFT_4:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_left_4,parent,false);
                holder = new ViewHolderLeftFourth(view);
                break;
            }
            case IM_LEFT_5:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_left_5,parent,false);
                holder = new ViewHolderLeftFifth(view);
                break;
            }
            case IM_LEFT_6:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_left_6,parent,false);
                holder = new ViewHolderLeftSixth(view);
                break;
            }
            case IM_LEFT_7:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_left_7,parent,false);
                holder = new ViewHolderLeftSeventh(view);
                break;
            }
            case IM_LEFT_8:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_left_8,parent,false);
                holder = new ViewHolderLeftEighth(view);
                break;
            }
            case IM_LEFT_9:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_left_8,parent,false);
                holder = new ViewHolderLeftNinth(view);
                break;
            }
            case IM_LEFT_10:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_left_10,parent,false);
                holder = new ViewHolderLeftTenth(view);
                break;
            }
            case IM_LEFT_11:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_left_8,parent,false);
                holder = new ViewHolderLeftEleventh(view);
                break;
            }
            case IM_RIGHT_1:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_right_1,parent,false);
                holder = new ViewHolderRightOne(view);
                break;
            }
            case IM_PUSH_MSG:{
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_center_1,parent,false);
                holder = new ViewHolderCenterOne(view);
                break;
            }
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = getItemViewType(position);

        ImModel imModel = mDataList.get(position);

        switch (type){
            case TYPE_HEADER:{
                handleHeader(holder,imModel);
                break;
            }
            case TYPE_TIME:{
                handleTime(holder,imModel);
                break;
            }
            case IM_LEFT_1:{
                handleLeftOne(holder,imModel);
                break;
            }
            case IM_LEFT_2:{
                handleLeftSecond(holder,imModel);
                break;
            }
            case IM_LEFT_3:{
                handleLeftThird(holder,imModel);
                break;
            }
            case IM_LEFT_4:{
                handleLeftFourth(holder,imModel);
                break;
            }
            case IM_LEFT_5:{
                handleLeftFifth(holder,imModel);
                break;
            }
            case IM_LEFT_6:{
                handleLeftSixth(holder,imModel);
                break;
            }
            case IM_LEFT_7:{
                handleLeftSeventh(holder,imModel);
                break;
            }
            case IM_LEFT_8:{
                handleLeftEighth(holder,imModel);
                break;
            }
            case IM_LEFT_9:{
                handleLeftNinth(holder,imModel);
                break;
            }
            case IM_LEFT_10:{
                handleLeftTenth(holder,imModel);
                break;
            }
            case IM_LEFT_11:{
                handleLeftEleventh(holder,imModel);
                break;
            }
            case IM_RIGHT_1:{
                handleRight(holder,imModel);
                break;
            }
            case IM_PUSH_MSG:{
                handleCenterOne(holder,imModel);
                break;
            }
        }
    }

    private void handleLeftOne(ViewHolder holder,ImModel imModel){
        ViewHolderLeftOne viewholder = (ViewHolderLeftOne) holder;
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_robot);
        viewholder.mTvLeftOne.setText(imModel.getContent());
    }

    private void handleLeftSecond(ViewHolder holder, final ImModel imModel){
        ViewHolderLeftSecond viewholder = (ViewHolderLeftSecond) holder;
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_robot);
        viewholder.mTvLeftContent.setText(imModel.getContent());
        viewholder.mLlLeftList.removeAllViews();
        for (int i = 0;i < imModel.getInfoList().size();i++){
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_select_text_question,viewholder.mLlLeftList,false);
            TextView tv = (TextView)view.findViewById(R.id.tv_im_select_text);
            final String info = imModel.getInfoList().get(i);
            tv.setText(info);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImSelectListener.onImSelect(IM_LEFT_2, finalI,info);
                }
            });
            viewholder.mLlLeftList.addView(view);
        }
    }

    private void handleLeftThird(ViewHolder holder,ImModel imModel){
        ViewHolderLeftThird viewholder = (ViewHolderLeftThird) holder;
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_robot);
        viewholder.mLlLeftList.removeAllViews();
        for (int i = 0;i < imModel.getInfoList().size();i++){
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_select_text_question,viewholder.mLlLeftList,false);
            TextView tv = (TextView)view.findViewById(R.id.tv_im_select_text);
            final String info = imModel.getInfoList().get(i);
            tv.setText(info);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImSelectListener.onImSelect(IM_LEFT_3,-1,info);
                }
            });
            viewholder.mLlLeftList.addView(view);
        }
    }

    private void handleLeftFourth(ViewHolder holder, final ImModel imModel){
        final ViewHolderLeftFourth viewholder = (ViewHolderLeftFourth) holder;
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_robot);
        viewholder.mTvLeftContent.setText(imModel.getContent());
        viewholder.mLlLeftList.removeAllViews();
        for (int i = 0;i < imModel.getInfoList().size();i++){
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_select_text_question,viewholder.mLlLeftList,false);
            TextView tv = (TextView)view.findViewById(R.id.tv_im_select_text);
            final String info = imModel.getInfoList().get(i);
            tv.setText(info);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImSelectListener.onImSelect(IM_LEFT_4,-1,info);
                }
            });
            viewholder.mLlLeftList.addView(view);
        }
        viewholder.mLlYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status1 = viewholder.mLlYes.isSelected();
                boolean status2 = viewholder.mLlNo.isSelected();
                if (!status1 && !status2){
                    viewholder.mIvYes.setSelected(true);
                    viewholder.mTvYes.setSelected(true);
                    viewholder.mLlYes.setSelected(true);
                    // TODO: 2018/9/13 标记已解决
                    onImQuestionSolveListener.onImQuestionSolve(imModel.getQuestionId(),imModel.getOriginQuestion(),true);
                }
            }
        });
        viewholder.mLlNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status1 = viewholder.mLlYes.isSelected();
                boolean status2 = viewholder.mLlNo.isSelected();
                if (!status1 && !status2){
                    viewholder.mIvNo.setSelected(true);
                    viewholder.mTvNo.setSelected(true);
                    viewholder.mLlNo.setSelected(true);
                    // TODO: 2018/9/13 标记未解决
                    onImQuestionSolveListener.onImQuestionSolve(imModel.getQuestionId(),imModel.getOriginQuestion(),false);
                }
            }
        });
    }

    private void handleLeftFifth(ViewHolder holder, final ImModel imModel){
        final ViewHolderLeftFifth viewholder = (ViewHolderLeftFifth) holder;
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_robot);
        viewholder.mTvLeftQuestion.setText(imModel.getContent().split("/")[0]);
        viewholder.mTvLeftAnswer.setText(imModel.getContent().split("/")[1]);
        viewholder.mLlLeftList.removeAllViews();
        for (int i = 0;i < imModel.getInfoList().size();i++){
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_im_select_text_question,viewholder.mLlLeftList,false);
            TextView tv = (TextView)view.findViewById(R.id.tv_im_select_text);
            final String info = imModel.getInfoList().get(i);
            tv.setText(info);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onImSelectListener.onImSelect(IM_LEFT_5,-1,info);
                }
            });
            viewholder.mLlLeftList.addView(view);
        }
        viewholder.mLlYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status1 = viewholder.mLlYes.isSelected();
                boolean status2 = viewholder.mLlNo.isSelected();
                if (!status1 && !status2){
                    viewholder.mIvYes.setSelected(true);
                    viewholder.mTvYes.setSelected(true);
                    viewholder.mLlYes.setSelected(true);
                    // TODO: 2018/9/13 标记已解决
                    onImQuestionSolveListener.onImQuestionSolve(imModel.getQuestionId(),imModel.getOriginQuestion(),true);
                }
            }
        });
        viewholder.mLlNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status1 = viewholder.mLlYes.isSelected();
                boolean status2 = viewholder.mLlNo.isSelected();
                if (!status1 && !status2){
                    viewholder.mIvNo.setSelected(true);
                    viewholder.mTvNo.setSelected(true);
                    viewholder.mLlNo.setSelected(true);
                    // TODO: 2018/9/13 标记未解决
                    onImQuestionSolveListener.onImQuestionSolve(imModel.getQuestionId(),imModel.getOriginQuestion(),false);
                }
            }
        });
    }

    private void handleLeftSixth(ViewHolder holder,ImModel imModel){
        final ViewHolderLeftSixth viewholder = (ViewHolderLeftSixth) holder;
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_robot);
        viewholder.mTvLeftGoSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/9/13 跳转至留言板H5
            }
        });
    }

    private void handleLeftSeventh(ViewHolder holder,ImModel imModel){
        final ViewHolderLeftSeventh viewholder = (ViewHolderLeftSeventh) holder;
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_robot);
        viewholder.mGvProv.setAdapter(mAdapterProv);
        adjustGridView(viewholder.mGvProv,mContext.getResources().getDimensionPixelOffset(R.dimen.margin_27),4,0,false);
        viewholder.mGvProv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onImSelectListener.onImSelect(IM_LEFT_7,position,mAdapterProv.getDataList()[position]);
            }
        });
    }

    private void handleLeftEighth(ViewHolder holder,ImModel imModel){
        final ViewHolderLeftEighth viewholder = (ViewHolderLeftEighth) holder;
        viewholder.mTvTip.setText(mContext.getString(R.string.im_choose_course_1));
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_robot);
        mAdapterWl = new ImSelectCourseCmAdapter(mContext,imModel.getInfoList());
        viewholder.mGvWl.setAdapter(mAdapterWl);
        adjustGridView(viewholder.mGvWl,mContext.getResources().getDimensionPixelOffset(R.dimen.margin_37),2,mContext.getResources().getDimensionPixelOffset(R.dimen.margin_10),true);
        viewholder.mGvWl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onImSelectListener.onImSelect(IM_LEFT_8,position,mAdapterWl.getDataList().get(position));
            }
        });
    }

    private void handleLeftNinth(ViewHolder holder,ImModel imModel){
        final ViewHolderLeftNinth viewholder = (ViewHolderLeftNinth) holder;
        viewholder.mTvTip.setText(mContext.getString(R.string.im_choose_course_2));
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_robot);
        viewholder.mGvZj.setAdapter(mAdapterZj);
        adjustGridView(viewholder.mGvZj,mContext.getResources().getDimensionPixelOffset(R.dimen.margin_37),2,mContext.getResources().getDimensionPixelOffset(R.dimen.margin_10),true);
        viewholder.mGvZj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.isEnabled()){
                    view.setEnabled(false);
                    mAdapterZj.getSelectMap().remove(position);
                }else{
                    if (mAdapterZj.getSelectMap().size() == 2){
                        String str = "";
                        for (int index : mAdapterZj.getSelectMap().keySet()){
                            str += mAdapterZj.getItem(index) + "/";
                            mAdapterZj.getSelectMap().get(index).setEnabled(false);
                        }
                        str += mAdapterZj.getItem(position);
                        onImSelectListener.onImSelect(IM_LEFT_9,position,str);
                        mAdapterZj.getSelectMap().clear();
                    }else{
                        view.setEnabled(true);
                        mAdapterZj.getSelectMap().put(position,view);
                    }
                }
            }
        });
    }

    private void handleLeftTenth(ViewHolder holder,ImModel imModel){
        final ViewHolderLeftTenth viewholder = (ViewHolderLeftTenth) holder;
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_robot);

        viewholder.mAwlMajor.removeAllViews();
        int lr = mContext.getResources().getDimensionPixelOffset(R.dimen.margin_10);
        for (final String key : imModel.getInfoList()){
            if (TextUtils.isEmpty(key)){
                continue;
            }
            View item = LayoutInflater.from(mContext).inflate(R.layout.layout_im_select_text_major,viewholder.mAwlMajor,false);
            TextView tv = (TextView) item;
            tv.setText(key);
//            CommonUtil.setViewMargin(tv,0,lr,lr,0);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            viewholder.mAwlMajor.addView(item,params);
            if (!key.equals("|")){
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2018/9/14
                        sendOwnMessage(key);
                    }
                });
            }
        }
    }

    private void handleLeftEleventh(ViewHolder holder,ImModel imModel){
        final ViewHolderLeftEleventh viewholder = (ViewHolderLeftEleventh) holder;
        viewholder.mTvTip.setText(mContext.getString(R.string.im_choose_batch));
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_robot);
        mAdapterBatch = new ImSelectCourseCmAdapter(mContext,imModel.getInfoList());
        viewholder.mGvBatch.setAdapter(mAdapterBatch);
        adjustGridView(viewholder.mGvBatch,mContext.getResources().getDimensionPixelOffset(R.dimen.margin_37),2,mContext.getResources().getDimensionPixelOffset(R.dimen.margin_10),true);
        viewholder.mGvBatch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onImSelectListener.onImSelect(IM_LEFT_11,position,mAdapterBatch.getDataList().get(position));
            }
        });
    }

    private void handleRight(ViewHolder holder,ImModel imModel){
        ViewHolderRightOne viewholder = (ViewHolderRightOne) holder;
        viewholder.mIvLogo.setImageResource(R.drawable.icon_im_user);
        viewholder.mTvRight.setText(imModel.getContent());
    }

    private void handleCenterOne(ViewHolder holder,ImModel imModel){
        ViewHolderCenterOne viewholder = (ViewHolderCenterOne) holder;
        if (!TextUtils.isEmpty(imModel.getName())){
            viewholder.mLlUser.setVisibility(View.VISIBLE);
            viewholder.mTvTeamName.setText(imModel.getName());
            viewholder.mVAds.setVisibility(imModel.isAds()?View.VISIBLE:View.GONE);
        }
        if (!TextUtils.isEmpty(imModel.getImgUrl())){
            viewholder.mSdvPic.setVisibility(View.VISIBLE);
            viewholder.mSdvPic.setAspectRatio(2.1f);
            Uri uri = Uri.parse(imModel.getImgUrl());
            viewholder.mSdvPic.setImageURI(uri);
        }
        if (!TextUtils.isEmpty(imModel.getContent())){
            viewholder.mTvTitle.setVisibility(View.VISIBLE);
            viewholder.mTvTitle.setText(imModel.getContent());
        }
        if (!TextUtils.isEmpty(imModel.getDetail())){
            viewholder.mTvDetail.setVisibility(View.VISIBLE);
            viewholder.mTvDetail.setText(imModel.getDetail());
        }
        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/9/14 跳转H5
            }
        });
    }

    private void handleTime(ViewHolder holder,ImModel imModel){
        ViewHolderTime viewholder = (ViewHolderTime) holder;
        Date date = new Date(imModel.getTime());
        SimpleDateFormat myFmt2=new SimpleDateFormat(mContext.getString(R.string.default_time_format), Locale.CHINA);
        String str = myFmt2.format(date);
        viewholder.mTvTime.setText(str);
    }

    private void handleHeader(ViewHolder holder,ImModel imModel){
        ViewHolderHeader viewholder = (ViewHolderHeader) holder;
        viewholder.mTvContent.setText(imModel.getContent());
    }

    public class ViewHolderLeftOne extends ViewHolder{

        ImageView mIvLogo;
        TextView mTvLeftOne;

        public ViewHolderLeftOne(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mTvLeftOne = (TextView) itemView.findViewById(R.id.tv_im_1_content);
        }


    }

    public class ViewHolderLeftSecond extends ViewHolder{

        ImageView mIvLogo;
        TextView mTvLeftContent;
        LinearLayout mLlLeftList;

        public ViewHolderLeftSecond(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mTvLeftContent = (TextView) itemView.findViewById(R.id.tv_im_2_content);
            mLlLeftList = (LinearLayout) itemView.findViewById(R.id.ll_im_2_list);
        }


    }

    public class ViewHolderLeftThird extends ViewHolder{

        ImageView mIvLogo;
        LinearLayout mLlLeftList;

        public ViewHolderLeftThird(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mLlLeftList = (LinearLayout) itemView.findViewById(R.id.ll_im_3_list);
        }


    }

    public class ViewHolderLeftFourth extends ViewHolder{

        ImageView mIvLogo;
        TextView mTvLeftContent;
        LinearLayout mLlLeftList;
        LinearLayout mLlYes;
        LinearLayout mLlNo;
        ImageView mIvYes;
        ImageView mIvNo;
        TextView mTvYes;
        TextView mTvNo;

        public ViewHolderLeftFourth(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mTvLeftContent = (TextView) itemView.findViewById(R.id.tv_im_4_content);
            mLlLeftList = (LinearLayout) itemView.findViewById(R.id.ll_im_4_list);

            mLlYes = (LinearLayout) itemView.findViewById(R.id.ll_im_4_yes);
            mLlNo = (LinearLayout) itemView.findViewById(R.id.ll_im_4_no);
            mIvYes = (ImageView) itemView.findViewById(R.id.iv_im_4_yes);
            mIvNo = (ImageView) itemView.findViewById(R.id.iv_im_4_no);
            mTvYes = (TextView) itemView.findViewById(R.id.tv_im_4_yes);
            mTvNo = (TextView) itemView.findViewById(R.id.tv_im_4_no);
        }
    }

    public class ViewHolderLeftFifth extends ViewHolder{

        ImageView mIvLogo;
        TextView mTvLeftQuestion;
        TextView mTvLeftAnswer;
        LinearLayout mLlLeftList;
        LinearLayout mLlYes;
        LinearLayout mLlNo;
        ImageView mIvYes;
        ImageView mIvNo;
        TextView mTvYes;
        TextView mTvNo;

        public ViewHolderLeftFifth(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mTvLeftQuestion = (TextView) itemView.findViewById(R.id.tv_im_5_question);
            mTvLeftAnswer = (TextView) itemView.findViewById(R.id.tv_im_5_answer);
            mLlLeftList = (LinearLayout) itemView.findViewById(R.id.ll_im_5_list);
            mLlYes = (LinearLayout) itemView.findViewById(R.id.ll_im_5_yes);
            mLlNo = (LinearLayout) itemView.findViewById(R.id.ll_im_5_no);
            mIvYes = (ImageView) itemView.findViewById(R.id.iv_im_5_yes);
            mIvNo = (ImageView) itemView.findViewById(R.id.iv_im_5_no);
            mTvYes = (TextView) itemView.findViewById(R.id.tv_im_5_yes);
            mTvNo = (TextView) itemView.findViewById(R.id.tv_im_5_no);
        }
    }

    public class ViewHolderLeftSixth extends ViewHolder{

        ImageView mIvLogo;
        TextView mTvLeftGoSubmit;

        public ViewHolderLeftSixth(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mTvLeftGoSubmit = (TextView) itemView.findViewById(R.id.tv_im_6_submit_question);
        }
    }

    public class ViewHolderLeftSeventh extends ViewHolder{

        ImageView mIvLogo;
        GridView mGvProv;

        public ViewHolderLeftSeventh(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mGvProv = (GridView) itemView.findViewById(R.id.gv_im_7_province);
        }
    }

    public class ViewHolderLeftEighth extends ViewHolder{

        ImageView mIvLogo;
        GridView mGvWl;
        TextView mTvTip;

        public ViewHolderLeftEighth(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mGvWl = (GridView) itemView.findViewById(R.id.gv_im_8_choose);
            mTvTip = (TextView) itemView.findViewById(R.id.tv_im_left_8_tip);
        }
    }

    public class ViewHolderLeftNinth extends ViewHolder{

        ImageView mIvLogo;
        GridView mGvZj;
        TextView mTvTip;

        public ViewHolderLeftNinth(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mGvZj = (GridView) itemView.findViewById(R.id.gv_im_8_choose);
            mTvTip = (TextView) itemView.findViewById(R.id.tv_im_left_8_tip);

        }
    }

    public class ViewHolderLeftTenth extends ViewHolder{

        ImageView mIvLogo;
        AutoWrapLayout mAwlMajor;

        public ViewHolderLeftTenth(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mAwlMajor = (AutoWrapLayout) itemView.findViewById(R.id.awl_im_major);
        }
    }

    public class ViewHolderLeftEleventh extends ViewHolder{

        ImageView mIvLogo;
        GridView mGvBatch;
        TextView mTvTip;

        public ViewHolderLeftEleventh(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mGvBatch = (GridView) itemView.findViewById(R.id.gv_im_8_choose);
            mTvTip = (TextView) itemView.findViewById(R.id.tv_im_left_8_tip);
        }
    }

    public class ViewHolderRightOne extends ViewHolder{

        ImageView mIvLogo;
        TextView mTvRight;

        public ViewHolderRightOne(View itemView) {
            super(itemView);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_im_logo);
            mTvRight = (TextView) itemView.findViewById(R.id.tv_im_content);
        }


    }

    public class ViewHolderCenterOne extends ViewHolder{

        LinearLayout mLlUser;
        ImageView mIvLogo;
        TextView mTvTeamName;
        View mVAds;
        SimpleDraweeView mSdvPic;
        TextView mTvTitle;
        TextView mTvDetail;

        public ViewHolderCenterOne(View itemView) {
            super(itemView);
            mLlUser = (LinearLayout) itemView.findViewById(R.id.ll_message_block_user);
            mIvLogo = (ImageView) itemView.findViewById(R.id.iv_message_block_avatar);
            mTvTeamName = (TextView) itemView.findViewById(R.id.iv_message_block_name);
            mVAds = itemView.findViewById(R.id.tv_message_block_tag_ads);
            mSdvPic = (SimpleDraweeView)itemView.findViewById(R.id.sdv_message_block_pic);
            mTvTitle = (TextView)itemView.findViewById(R.id.tv_message_block_title);
            mTvDetail = (TextView)itemView.findViewById(R.id.tv_message_block_detail);
        }


    }

    public class ViewHolderHeader extends ViewHolder{

        TextView mTvContent;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            mTvContent = (TextView) itemView.findViewById(R.id.tv_im_header_content);
        }
    }

    public class ViewHolderTime extends ViewHolder{

        TextView mTvTime;

        public ViewHolderTime(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_im_time);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private void sendOwnMessage(String content){
        MyClient.getMyClient().getImManager().handleBroadcastMsg(UrlConstantV2.VALUE.IM_RIGHT_1,content);
    }

    private void adjustGridView(GridView gv,int lineHeight,int columnCount,int horizontalSpacing,boolean isHadLine){
        int line = gv.getAdapter().getCount()/columnCount + (gv.getAdapter().getCount()%columnCount>0?1:0);
        int height = line * lineHeight;
        height += (line - 1) * horizontalSpacing;
        if (isHadLine){
            height += line * mContext.getResources().getDimensionPixelOffset(R.dimen.margin_1);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) gv.getLayoutParams();
        params.height = height;
        gv.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getType();
    }

}
