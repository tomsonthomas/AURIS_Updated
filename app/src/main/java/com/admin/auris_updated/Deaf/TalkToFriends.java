package com.admin.auris_updated.Deaf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.admin.auris_updated.R;

public class TalkToFriends extends AppCompatActivity {
    LinearLayout speak,text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_talk_tofriends);
        speak =findViewById(R.id.speech);
        text =findViewById(R.id.text);

/********************* speech to text ****************************************/
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SpeechToText.class);
                startActivity(intent);
            }
        });

/*************** text to speech ********************************************/
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TexttoSpeech.class);
                startActivity(intent);
            }
        });
    }
}
