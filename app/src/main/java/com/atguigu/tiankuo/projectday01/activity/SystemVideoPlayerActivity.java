package com.atguigu.tiankuo.projectday01.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.atguigu.tiankuo.projectday01.R;

public class SystemVideoPlayerActivity extends AppCompatActivity {

    private VideoView vv_video;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_video_player);
        vv_video = (VideoView)findViewById(R.id.vv_video);

        uri = getIntent().getData();

        vv_video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                vv_video.start();
            }
        });

        vv_video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(SystemVideoPlayerActivity.this, "播放错误", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        vv_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(SystemVideoPlayerActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        vv_video.setVideoURI(uri);
        vv_video.setMediaController(new MediaController(this));

    }
}
