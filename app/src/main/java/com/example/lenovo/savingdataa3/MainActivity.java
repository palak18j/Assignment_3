package com.example.lenovo.savingdataa3;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;


public class MainActivity extends Activity {

    final private String str="main";
    final String DATE_PREF="mydatePref";
    final String filename="InfoExtFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(str,"inside on create");

        //use of shared preference to display current time and date and last opened time and date
        displayCurrentDate();       //Displays current date in the top text view
        displayLateDate();          //Displays last opened date and time in the bottom text View
        saveCurrDate();             //Saves the last opened date in sharedPreferences

        Button studentDetails=(Button)findViewById(R.id.StudentDetailsbutton);
        Button notesButton=(Button)findViewById(R.id.MainNotesButton);
        Button extReadButton=(Button)findViewById(R.id.ExtReadFromFileButton);
        Button extWriteButton=(Button)findViewById(R.id.ExtWriteToFileButton);

        /*if(isExternalStorageReadOnly()==true||isExternalStorageWritable()==false)
        {
            Log.d(str,"Inside true CONDITION"); //Alternate to disable  the buttons if sd card is not available with device instead of showing toast
            //extReadButton.setEnabled(false);
            //extWriteButton.setEnabled(false);
            extReadButton.setClickable(false);
            extWriteButton.setClickable(false);
        }

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)==false) {
            Log.d(str,"Inside my true CONDITION write");
            extWriteButton.setClickable(false);
            extWriteButton.setEnabled(false);
        }

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)==false&&
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)==false) {
            Log.d(str,"Inside my true CONDITION read");
            extReadButton.setClickable(false);

        }*/
            final EditText extfileText=(EditText)findViewById(R.id.ExtFileInputText);

        //This event handler opens another activity that deals with students data stored in sqlite db
        studentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),StudentsDetails.class);
                startActivity(i);
                Log.d(str,"After student details button click in main");

            }
        });

        //This event handler opens another activity that deals with notes (another data)  stored in internal file
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Notes.class);
                startActivity(i);
                Log.d(str,"After notes button click in main");
            }
        });

        //This event handler writes content to edit Text stored in external storage file "MYFile.txt"
        extWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String text = extfileText.getText().toString();
                    Log.d(str, "After ext write file button click in main");
                    try {

                        File file = new File((getApplicationContext()).getExternalFilesDir(null), "MYFile.txt");
                        if (!file.exists())
                            file.createNewFile();
                        if(file==null)
                        Toast.makeText(getApplication().getBaseContext(), "Sorry file not created", Toast.LENGTH_SHORT).show();

                        else {
                           // Toast.makeText(getApplication().getBaseContext(), "file  created", Toast.LENGTH_SHORT).show();

                            FileOutputStream fo = new FileOutputStream(file);
                            OutputStreamWriter ou=new OutputStreamWriter(fo);
                            ou.append(text);
                            ou.close();
                            //fo.write(text.getBytes());
                            Toast.makeText(getApplication().getBaseContext(), "Successfully written to external file", Toast.LENGTH_SHORT).show();
                            fo.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                    Toast.makeText(getApplication().getBaseContext(), "OOPs!! external storage not available currently", Toast.LENGTH_SHORT).show();



            }
        });

        //This event handler reads content from edit Text stored in external storage file "MYFile.txt"
        extReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text="";
                Log.d(str, "After read file button click in main");

                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)==false&&
                        Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)==false)
                {
                    Toast.makeText(getApplication().getBaseContext(), "OOPs no attached external storage", Toast.LENGTH_SHORT).show();

                }

                else {
                    Log.d(str, "After read file button storage available");

                    try {

                        File file = new File((getApplicationContext()).getExternalFilesDir(null), "MYFile.txt");
                        if (!file.exists())
                            Toast.makeText(getApplication().getBaseContext(), "Sorry no file exists", Toast.LENGTH_SHORT).show();
                        else {
                            FileInputStream fi = new FileInputStream(file);

                            int c;
                            while ((c = fi.read()) != -1) {
                                text = text + Character.toString((char) c);
                            }
                            extfileText.setText(text);

                            fi.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(str,"inside on Resume");

    }

    public boolean isExternalStorageWritable()
    {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            return true;
        else
            return false;
    }

    public boolean isExternalStorageReadOnly()
    {
        String state=Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
            return true;
        else
            return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(str,"inside on Start");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(str,"inside on Stop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(str,"inside on Destroy");

    }

    private void displayCurrentDate() {
        Date d=new Date();
        String text="Current time : "+d.toString();
        TextView tv=(TextView)findViewById(R.id.currDate);
        tv.setText(text);
    }
    private void displayLateDate() {
        SharedPreferences prfs=getSharedPreferences(DATE_PREF,MODE_PRIVATE);
        String text=prfs.getString("Time","yet to record");
        String last_time="Last used time : "+text;
        TextView tv=(TextView)findViewById(R.id.LastDate);
        tv.setText(last_time);
    }

    private void saveCurrDate() {
        SharedPreferences prfs=getSharedPreferences(DATE_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor=prfs.edit();
        editor.putString("Time",new Date().toString());
        editor.commit();
    }

    @Override    //It saves the content of all the EditText fields during device rotation
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(str,"In onSaveInstance");
        TextView tv1=(TextView)findViewById(R.id.ExtFileInputText);
        outState.putString("INFO",tv1.getText().toString());
    }


    @Override    //It restores the content of all the EditText fields during device rotation
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        TextView tv1=(TextView)findViewById(R.id.ExtFileInputText);
        tv1.setText(savedInstanceState.getString("INFO"));
    }
}
