package com.admin.auris_updated.Deaf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.admin.auris_updated.R;

import java.util.ArrayList;

public class VideoList extends AppCompatActivity {
    VideoView videoView;
    private TextView mText,subtitle;
    private SpeechRecognizer sr;
    private static final String TAG = "MyActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_videoview);
        videoView = findViewById(R.id.video);
        subtitle = findViewById(R.id.subtitle);
        mText= findViewById(R.id.start);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());
        Intent intent = getIntent();
        try {
            String path = intent.getStringExtra("path");
            videoView.setMediaController(new MediaController(this));
            videoView.setVideoURI(Uri.parse(path));
            videoView.requestFocus();
            videoView.start();
            videoView.pause();
            mText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
                                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"com.admin.auris_updated");
                                    intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true);
                                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                                    sr.startListening(intent);
                                    videoView.start();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
        class listener implements RecognitionListener {
            public void onReadyForSpeech(Bundle params) {
                Log.d(TAG, "onReadyForSpeech");
            }

            public void onBeginningOfSpeech() {
                Log.d(TAG, "onBeginningOfSpeech");
            }

            public void onRmsChanged(float rmsdB) {
                Log.d(TAG, "onRmsChanged");
            }

            public void onBufferReceived(byte[] buffer) {
                Log.d(TAG, "onBufferReceived");
            }

            public void onEndOfSpeech() {
                Log.d(TAG, "onEndofSpeech");
            }

            @SuppressLint("SetTextI18n")
            public void onError(int error) {
                Log.d(TAG, "error " + error);
                mText.setText("error " + error);
            }

            public void onResults(Bundle results) {
//                String str = new String();
//                Log.d(TAG, "onResults " + results);
//                ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//                for (int i = 0; i < data.size(); i++) {
//                    Log.d(TAG, "result " + data.get(i));
//                    str += data.get(i);
//                }
//                subtitle.setText("results: " + String.valueOf(data.size()));
            }

            public void onPartialResults(Bundle partialResults) {

                ArrayList data = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String word = (String) data.get(data.size() - 1);
                subtitle.setText(word);
                Log.i("TEST", "partial_results: " + word);
            }

            public void onEvent(int eventType, Bundle params) {
                Log.d(TAG, "onEvent " + eventType);
            }
        }
    }
