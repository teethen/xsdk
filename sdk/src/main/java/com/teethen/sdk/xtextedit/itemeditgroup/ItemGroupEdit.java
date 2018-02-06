package com.teethen.sdk.xtextedit.itemeditgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teethen.sdk.R;

/**
 * Created by xingq on 2017/12/03.
 * 组合控件封装
 */
public class ItemGroupEdit extends FrameLayout implements View.OnClickListener {

    private LinearLayout itemGroupLayout; //组合控件的布局
    private TextView leftLabelTv; //标题
    private EditText centerEdit; //输入框
    private ImageView rightClearIv; //清楚输入内容
    private ImageView rightJumpIv; //向右的箭头
    private ItemOnClickListener itemOnClickListener; //Item的点击事件

    public ItemGroupEdit(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public ItemGroupEdit(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    public ItemGroupEdit(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }

    //初始化View
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_edit_layout, null);
        itemGroupLayout = (LinearLayout) view.findViewById(R.id.item_group_edit_layout);
        leftLabelTv = (TextView) view.findViewById(R.id.left_label_tv);
        centerEdit = (EditText) view.findViewById(R.id.center_edit);
        rightClearIv = (ImageView) view.findViewById(R.id.right_clear_iv);
        rightJumpIv = (ImageView) view.findViewById(R.id.right_jump_iv);
        addView(view); //把自定义的这个组合控件的布局加入到当前FrameLayout
        itemGroupLayout.setBackgroundResource(R.drawable.selector_white);
        itemGroupLayout.setOnClickListener(this);
        rightClearIv.setOnClickListener(this);
        centerEdit.addTextChangedListener(new TextChangeWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                //输入框输入内容改变
                String content = centerEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    //输入内容不为空的时候，清除输入的Icon可见
                    rightClearIv.setVisibility(VISIBLE);
                } else {
                    rightClearIv.setVisibility(GONE);
                }
            }
        });
    }

    /**
     * 初始化相关属性，引入相关属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        //标题默认的字体大小，15sp
        float defaultTitleSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, context.getResources().getDisplayMetrics());
        //标题的默认字体颜色
        int defaultTitleColor = context.getResources().getColor(R.color.item_group_title);
        //输入框默认的字体大小，13sp
        float defaultEdtSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, context.getResources().getDisplayMetrics());
        //输入框的默认字体颜色
        int defaultEdtColor = context.getResources().getColor(R.color.item_group_edt);
        //输入框的默认的提示内容的字体颜色
        int defaultHintColor = context.getResources().getColor(R.color.item_group_edt);

        //通过obtainStyledAttributes方法获取到一个TypedArray对象，然后通过TypedArray对象就可以获取到相对应定义的属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemGroupEdit);
        String title = typedArray.getString(R.styleable.ItemGroupEdit_title);
        float paddingLeft = typedArray.getDimension(R.styleable.ItemGroupEdit_paddingLeft, 15);
        float paddingRight = typedArray.getDimension(R.styleable.ItemGroupEdit_paddingRight, 15);
        float paddingTop = typedArray.getDimension(R.styleable.ItemGroupEdit_paddingTop, 5);
        float paddingBottom = typedArray.getDimension(R.styleable.ItemGroupEdit_paddingTop, 5);
        float titleSize = typedArray.getDimension(R.styleable.ItemGroupEdit_title_size, 15);
        int titleColor = typedArray.getColor(R.styleable.ItemGroupEdit_title_color, defaultTitleColor);
        String content = typedArray.getString(R.styleable.ItemGroupEdit_edit_content);
        float contentSize = typedArray.getDimension(R.styleable.ItemGroupEdit_edit_text_size, 14);
        int contentColor = typedArray.getColor(R.styleable.ItemGroupEdit_edit_text_color, defaultEdtColor);
        String hintContent = typedArray.getString(R.styleable.ItemGroupEdit_edit_hint_content);
        int hintColor = typedArray.getColor(R.styleable.ItemGroupEdit_edit_hint_text_color, defaultHintColor);
        //默认输入框可以编辑
        boolean isEditable = typedArray.getBoolean(R.styleable.ItemGroupEdit_isEditable, true);
        //向右的箭头图标是否可见，默认可见
        boolean showJtIcon = typedArray.getBoolean(R.styleable.ItemGroupEdit_right_jump_visible, true);
        typedArray.recycle();

        //设置数据
        //设置item的内边距
        itemGroupLayout.setPadding((int) paddingLeft, (int) paddingTop, (int) paddingRight, (int) paddingBottom);
        leftLabelTv.setText(title);
        leftLabelTv.setTextSize(titleSize);
        leftLabelTv.setTextColor(titleColor);

        centerEdit.setText(content);
        centerEdit.setTextSize(contentSize);
        centerEdit.setTextColor(contentColor);
        centerEdit.setHint(hintContent);
        centerEdit.setHintTextColor(hintColor);
        centerEdit.setFocusableInTouchMode(isEditable); //设置输入框是否可以编辑
        centerEdit.setLongClickable(false); //输入框不允许长按

        rightJumpIv.setVisibility(showJtIcon ? View.VISIBLE : View.GONE);  //设置向右的箭头图标是否可见
    }

    //Item点击事件监听
    public interface ItemOnClickListener {
        void onItemClick(View v);
    }

    /**
     * 供外部调用的方法，设置Item的点击事件
     *
     * @param itemOnClickListener
     */
    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    //获取输入的内容
    public String getText() {
        return centerEdit.getText().toString().trim();
    }

    //设置输入框的内容
    public void setText(String text) {
        centerEdit.setText(text);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.item_group_edit_layout){
            //点击了Item的布局
            if (itemOnClickListener != null) {
                itemOnClickListener.onItemClick(this);
            }
        } else if (id == R.id.right_clear_iv) {
            //清除输入内容
            centerEdit.setText("");
            rightClearIv.setVisibility(GONE);
        }
    }
}
