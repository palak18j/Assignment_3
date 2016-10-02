package com.example.lenovo.savingdataa3;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import android.widget.*;



public class StudentsDetails extends Activity {

    final String str="Student";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);
        Log.d(str, "inside on create");

        final DatabaseHelper db=new DatabaseHelper(getApplicationContext());
        //Log.d(str, "inside on create after layout putting");
        Button b_add=(Button)findViewById(R.id.Add);
        Button b_del=(Button)findViewById(R.id.delete);
        Button b_update=(Button)findViewById(R.id.update);
        Button b_load=(Button)findViewById(R.id.Load);

        final EditText roll_text=(EditText)findViewById(R.id.s_roll_no);
        final EditText name_text=(EditText)findViewById(R.id.s_name);
        final EditText marks_text=(EditText)findViewById(R.id.s_marks);
        final EditText sem_text=(EditText)findViewById(R.id.s_sem);

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int roll=Integer.parseInt(roll_text.getText().toString());
                String name=name_text.getText().toString();
                int marks=Integer.parseInt(marks_text.getText().toString());
                String sem=sem_text.getText().toString();
                boolean res=db.insert(roll,name,marks,sem);
                if(res)
                    Toast.makeText(getApplication().getBaseContext(),"Successfully added",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplication().getBaseContext(),"Not added!Try with correct values",Toast.LENGTH_SHORT).show();
            }
        });

        b_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int roll=Integer.parseInt(roll_text.getText().toString());
                boolean res=db.delete(roll);
                if(res)
                    Toast.makeText(getApplication().getBaseContext(),"Successfully Removed Record",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplication().getBaseContext(),"Oops! No such record exist",Toast.LENGTH_SHORT).show();
            }
        });

        b_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int roll=Integer.parseInt(roll_text.getText().toString());
                String name=name_text.getText().toString();
                int marks=Integer.parseInt(marks_text.getText().toString());
                String sem=sem_text.getText().toString();
                boolean res=db.update(roll, name, marks, sem);
                if(res)
                    Toast.makeText(getApplication().getBaseContext(),"Successfully Updated Record",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplication().getBaseContext(),"Oops! No such record exist",Toast.LENGTH_SHORT).show();
            }
        });

        b_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //It considers the entered roll number by user as input whose corresponding data from the database
                // is retrieved and loaded in respective fields
                int roll=Integer.parseInt(roll_text.getText().toString());
                //String name=name_text.getText().toString();
                Log.d(str, "Before cursor call");
                Cursor cursor=db.retrieveRec(roll);
                Log.d(str, "After cursor call before checking if received null");
                if(cursor!=null&& cursor.getCount()>0)
                {
                    Log.d(str, "Inside true of cursor if");
                    cursor.moveToFirst();
                    String name,sem;int marks;
                    name=cursor.getString(cursor.getColumnIndex(DatabaseHelper.TableDef.NAME));
                    Log.d(str, "cursor gives this as name"+name);
                    sem=cursor.getString(cursor.getColumnIndex(DatabaseHelper.TableDef.CURR_SEM));
                    marks=cursor.getInt(cursor.getColumnIndex(DatabaseHelper.TableDef.MARKS));
                    //populating data to the respective fields
                    name_text.setText(name);
                    sem_text.setText(sem);
                    marks_text.setText(""+marks);
                }
                else
                    Toast.makeText(getApplication().getBaseContext(),"Oops! No such record exist with this ROLL NO.",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(str,"In onSaveInstance");          //It saves the content of all the EditText fields during device rotation

        EditText tv1=(EditText)findViewById(R.id.s_roll_no);
        EditText tv2=(EditText)findViewById(R.id.s_name);
        EditText tv3=(EditText)findViewById(R.id.s_marks);
        EditText tv4=(EditText)findViewById(R.id.s_sem);

        outState.putString("ROLL_NO",tv1.getText().toString());
        outState.putString("NAME",tv2.getText().toString());
        outState.putString("MARKS",tv3.getText().toString());
        outState.putString("SEMESTER",tv4.getText().toString());

    }


    @Override    //It restores the content of all the EditText fields during device rotation
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        EditText tv1=(EditText)findViewById(R.id.s_roll_no);
        EditText tv2=(EditText)findViewById(R.id.s_name);
        EditText tv3=(EditText)findViewById(R.id.s_marks);
        EditText tv4=(EditText)findViewById(R.id.s_sem);
        tv1.setText(savedInstanceState.getString("ROLL_NO"));
        tv2.setText(savedInstanceState.getString("NAME"));
        tv3.setText(savedInstanceState.getString("MARKS"));
        tv4.setText(savedInstanceState.getString("SEMESTER"));

    }
}
