package com.jasrsir.videoview;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class PrincipalMain_Activity extends AppCompatActivity {

    //VideoView Component
    private VideoView mvideoV;
    private MediaController mMedContr;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_main);
        //Inicialized Variables
        mvideoV = (VideoView) findViewById(R.id.videoView);
        mMedContr = new MediaController(PrincipalMain_Activity.this);
        mPlayer = new MediaPlayer();

        //mvideoV.setVideoPath("android.resource://" + getPackageName() + "/"+ R.raw.video);
        mvideoV.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.video_dos);
        //Select Media controller
        mvideoV.setMediaController(mMedContr);
    }

    //region Par onResume(), onPause()......onStart(), onStop()
    @Override
    protected void onResume() {
        super.onResume();
        try {
            mvideoV.resume();

        } catch (Exception e) {
            mvideoV.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        getIntent().putExtra("playing", mvideoV.isPlaying());
        if (!mvideoV.isPlaying()) {
            mvideoV.pause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //only run 1 time
        mvideoV.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mvideoV.suspend();
    }
    //endregion


    //SAVE ACTIVITY STATE
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Creamos una variable para guardar el estado de la activity
        int posicion = mvideoV.getCurrentPosition();
        outState.putInt("videoPos",posicion);
        //Comprobamos esto en la segunda llamada a onSaveInstanceState para no modificar el valor. y lo pausamos si es necesario
        if (!outState.getBoolean("playing"))
            mvideoV.pause();
    }

    //LOAD ACTIVITY STATE
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Restore Activity State
        if (!savedInstanceState.getBoolean("playing"))
            mvideoV.pause();
        mvideoV.seekTo(savedInstanceState.getInt("videoPos"));
    }
}
