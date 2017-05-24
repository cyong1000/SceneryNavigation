package com.jryx.cy.scenerynavigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class SceneryShowActivity extends AppCompatActivity {

    private EditText editText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenery_show);
        editText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        editText.setText(bundle.getString("url"));
    }
}
