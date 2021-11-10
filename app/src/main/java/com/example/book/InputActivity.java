package com.example.book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.main.R;

public class InputActivity extends AppCompatActivity {

    public static final int RESULT_CODE_ADD_ITEM = 996;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Intent intent=getIntent();
        int position=intent.getIntExtra("position",0);

        EditText editTextName=findViewById(R.id.edit_text_name);
        // Get default name from extra field in intent
        String name=intent.getStringExtra("name");
        if(null!=name){
            editTextName.setText(name);
        }

        Button buttonOk=this.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the data to be sent back to the previous activity.
                intent.putExtra("position",position);
                intent.putExtra("name",editTextName.getText().toString());
                setResult(RESULT_CODE_ADD_ITEM,intent);     // result code, data
                InputActivity.this.finish();
            }
        });
    }
}