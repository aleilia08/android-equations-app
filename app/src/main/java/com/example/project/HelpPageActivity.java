package com.example.project;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpPageActivity extends AppCompatActivity {

    private VideoView helpVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        helpVideoView = findViewById(R.id.help_video);

        // Set the media controller buttons
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(helpVideoView);

        // Set the video URI
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.demo_video);
        helpVideoView.setMediaController(mediaController);
        helpVideoView.setVideoURI(videoUri);
        helpVideoView.requestFocus();

        // Start the video
        helpVideoView.start();
    }
}
