package com.example.lenovo.savingdataa3;


import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Notes extends Activity {
    final String str="Notes";
    final String fileName="myNotesFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteslayout);

        Button readFileButton=(Button)findViewById(R.id.ReadFromFileButton);
        Button writeFileButton=(Button)findViewById(R.id.WriteToFileButton);

        final EditText fileText=(EditText)findViewById(R.id.FileInputText);

        //this event handler writes content of edit Text entered by user to "myNotesFile"
        writeFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=fileText.getText().toString();
                Log.d(str, "After write file button click in Notes");
                try {
                    FileOutputStream fo=openFileOutput(fileName,MODE_PRIVATE);
                    fo.write(text.getBytes());
                    Toast.makeText(getApplication().getBaseContext(), "Successfully written to file", Toast.LENGTH_SHORT).show();
                    fo.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });

        //This event handler reads content of edit Text entered by user to "myNotesFile"
        readFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text="";
                Log.d(str, "After read file button click in Notes");
                try {
                    FileInputStream fi=openFileInput(fileName);
                    int c;
                    while((c=fi.read())!=-1)
                    {
                        text=text+Character.toString((char)c);
                    }
                    fileText.setText(text);

                    fi.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override       //It saves the content of all the EditText fields during device rotation
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(str,"In onSaveInstance");
        TextView tv1=(TextView)findViewById(R.id.FileInputText);
        outState.putString("NOTES",tv1.getText().toString());
    }


    @Override       //It restores the content of all the EditText fields during device rotation
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        TextView tv1=(TextView)findViewById(R.id.FileInputText);
        tv1.setText(savedInstanceState.getString("NOTES"));
    }
}
