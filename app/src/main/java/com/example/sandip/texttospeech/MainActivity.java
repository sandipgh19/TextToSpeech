package com.example.sandip.texttospeech;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

        private TextToSpeech textToSpeech;
        private Button button;
        private EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button1);
        inputText = (EditText) findViewById(R.id.inputText);
        textToSpeech = new TextToSpeech(this, this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                convertTextToSpeech();
            }

        });
        convertTextToSpeech();
    }


    /**
     * a callback to be invoked indicating the completion of the TextToSpeech
     * engine initialization.
     *
     * @see android.speech.tts.TextToSpeech.OnInitListener#onInit(int)
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("error", "This Language is not supported");
            } else {
                convertTextToSpeech();
            }
        } else {
            Log.e("error", "Initilization Failed!");
        }
    }

    /**
     * Releases the resources used by the TextToSpeech engine. It is good
     * practice for instance to call this method in the onDestroy() method of an
     * Activity so the TextToSpeech engine can be cleanly stopped.
     *
     * @see android.app.Activity#onDestroy()
     */
    @Override
    public void onDestroy() {
        textToSpeech.shutdown();
        super.onDestroy();
    }

    /**
     * Speaks the string using the specified queuing strategy and speech
     * parameters.
     */
    private void convertTextToSpeech() {
        String text = inputText.getText().toString();
        if (null == text || "".equals(text)) {
            text = "Please give some input.";
        }
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
