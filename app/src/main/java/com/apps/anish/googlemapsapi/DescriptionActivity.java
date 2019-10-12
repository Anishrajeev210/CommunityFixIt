package com.apps.anish.googlemapsapi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DescriptionActivity extends AppCompatActivity {

    Button okButton;
    EditText descriptionText;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        okButton = (Button)findViewById(R.id.okbutton_id);
        descriptionText = (EditText)findViewById(R.id.editddescription_id);


    }
    public void submitDescription(View v){
        description = descriptionText.getText().toString();
        Intent intent1 = new Intent(this, ReportActivity.class);
        intent1.putExtra("description", description);
        startActivity(intent1);
    }


}
