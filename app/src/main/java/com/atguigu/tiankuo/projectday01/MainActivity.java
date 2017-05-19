package com.atguigu.tiankuo.projectday01;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.atguigu.tiankuo.projectday01.Fragment.BaseFragmentActivity;
import com.atguigu.tiankuo.projectday01.pager.LocalAudioPager;
import com.atguigu.tiankuo.projectday01.pager.LocalVideoPager;
import com.atguigu.tiankuo.projectday01.pager.NetAudioPager;
import com.atguigu.tiankuo.projectday01.pager.NetVideoPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_main;
    private ArrayList<BaseFragmentActivity> fragments;
    private int position;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isGrantExternalRW(this);
        rg_main = (RadioGroup) findViewById(R.id.rg_main);


        initFragmetn();
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rg_main.check(R.id.rb_local_audio);
    }

    private boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return false;
        }

        return true;

    }

    private void initFragmetn() {
        fragments = new ArrayList<>();
        fragments.add(new LocalVideoPager());
        fragments.add(new LocalAudioPager());
        fragments.add(new NetVideoPager());
        fragments.add(new NetAudioPager());
    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_local_video:
                    position = 0;
                    break;
                case R.id.rb_local_audio:
                    position = 1;
                    break;
                case R.id.rb_net_video:
                    position = 2;
                    break;
                case R.id.rb_net_audio:
                    position = 3;
                    break;
            }
            BaseFragmentActivity bfa = fragments.get(position);
            addFragment(bfa);
        }

        private void addFragment(BaseFragmentActivity bfa) {
            if (tempFragment != bfa) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (!bfa.isAdded()) {
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }
                    ft.add(R.id.fl_content, bfa);
                } else {
                    if (tempFragment != null) {
                        ft.hide(tempFragment);
                    }
                    ft.show(bfa);
                }
                ft.commit();
                tempFragment = bfa;
            }
        }

        public boolean isGrantExternalRW(Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                activity.requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
                return false;
            }
            return true;
        }
    }
}
