package com.atguigu.tiankuo.projectday01.pager;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.tiankuo.projectday01.Fragment.BaseFragmentActivity;

/**
 * Created by Administrator on 2017/5/19 0019.
 */
public class LocalAudioPager extends BaseFragmentActivity {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLUE);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("本地音乐");
    }
}
