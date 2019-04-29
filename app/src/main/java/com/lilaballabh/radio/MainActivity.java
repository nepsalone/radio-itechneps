package com.lilaballabh.radio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.lilaballabh.radio.player.PlaybackStatus;
import com.lilaballabh.radio.player.RadioManager;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    public static ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.PlayPause)
    ImageButton trigger;

    @BindView(R.id.name)
    TextView textView;

    @BindView(R.id.Player)
    View subPlayer;

   private RadioManager radioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        radioManager = RadioManager.with(this);

        progressBar = (ProgressBar) findViewById(R.id.ProgressBar_myfm);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        radioManager.unbind();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        radioManager.bind();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Subscribe
   // @Override
    public void onEvent(String status){

        switch (status){

            case PlaybackStatus.LOADING:

                // loading

                break;

            case PlaybackStatus.ERROR:
                Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show();
                break;

        }

        trigger.setImageResource(status.equals(PlaybackStatus.PLAYING)
                ? R.drawable.ic_pause
                : R.drawable.ic_play);

    }

    @OnClick(R.id.PlayPause)
    public void onClicked(){
        if (TextUtils.isEmpty(getString(R.string.radio_url)))  return;
            this.radioManager.playOrPause(getString(R.string.radio_url));


        //123
       // if (!TextUtils.isEmpty(getString(R.string.radio_url))) {
       //     this.radioManager.playOrPause(getString(R.string.radio_url));
       // }
        //123
       // if(TextUtils.isEmpty(streamURL)) return;
      //  radioManager.playOrPause(streamURL);
    }

}