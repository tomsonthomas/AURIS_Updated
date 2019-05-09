package com.admin.auris_updated.Deaf;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.auris_updated.R;

import java.util.ArrayList;
import java.util.Locale;

public  class SpeechToText  extends AppCompatActivity {
    private final int SPEECH_RECOGNITION_CODE = 1;
    private TextView txtOutput;
    private ImageButton btnMicrophone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutspeechtotext);
        txtOutput = (TextView) findViewById(R.id.textView4);
        btnMicrophone = (ImageButton) findViewById(R.id.imageButton2);
    }
    /*********************************************** method used to convert Sppech to text **************************/
    public void speakclick(View view) {

        startSpeechToText();
    }


    private void startSpeechToText() {
/*********************************************** Intent  used to record speech **************************/
        Intent in = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        in.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        in.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now");
        try {
 /***********************************************method   used to get recorded speech **************************/
            startActivityForResult(in, SPEECH_RECOGNITION_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Speech not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }
/***********************************************method   used to get convert  recorded speech to text **************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_RECOGNITION_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    /***********************************************method   used to get converted  recorded speech as text **************************/
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text = result.get(0);
                    txtOutput.setText(text);
                }
                break;
            }
        }
    }
}
