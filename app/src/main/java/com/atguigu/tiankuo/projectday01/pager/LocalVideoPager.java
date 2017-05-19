package com.atguigu.tiankuo.projectday01.pager;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.tiankuo.projectday01.Fragment.BaseFragmentActivity;
import com.atguigu.tiankuo.projectday01.R;
import com.atguigu.tiankuo.projectday01.activity.SystemVideoPlayerActivity;
import com.atguigu.tiankuo.projectday01.adapter.LocalVideoAdapter;
import com.atguigu.tiankuo.projectday01.domain.MediaItem;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class LocalVideoPager extends BaseFragmentActivity {
    private ListView lv;
    private TextView tv_local_video;
    private ArrayList<MediaItem> mediaItems;
    private Object data;
    private LocalVideoAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_local_vidio, null);
        lv = (ListView) view.findViewById(R.id.lv_vidio);
        tv_local_video = (TextView) view.findViewById(R.id.tv_content);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaItem item = (MediaItem) adapter.getItem(position);
                Intent intent = new Intent(context, SystemVideoPlayerActivity.class);
                intent.setDataAndType(Uri.parse(item.getData()),"video/*");
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void initData() {
        super.initData();
        getData();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mediaItems != null && mediaItems.size() > 0) {
                tv_local_video.setVisibility(View.GONE);
                adapter = new LocalVideoAdapter(context, mediaItems);
                lv.setAdapter(adapter);
            } else {
                tv_local_video.setVisibility(View.VISIBLE);
            }
        }
    };
    public void getData() {
        new Thread(){
            @Override
            public void run() {

                mediaItems = new ArrayList<MediaItem>();
                ContentResolver resolver = context.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String obj[] = {
                        MediaStore.Video.Media.DISPLAY_NAME,
                        MediaStore.Video.Media.DURATION,
                        MediaStore.Video.Media.SIZE,
                        MediaStore.Video.Media.DATA,
                };

                Cursor cursor = resolver.query(uri, obj, null, null, null);
                if(cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                        long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                        long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));

                        mediaItems.add(new MediaItem(name, duration, size, data));
                        handler.sendEmptyMessage(0);
                    }
                    cursor.close();
                }
            }
        }.start();
    }
}
