package my.awesome.lavanya.notesapp;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import static my.awesome.lavanya.notesapp.MainActivity.dateslist;
import static my.awesome.lavanya.notesapp.MainActivity.rvAdapter;
import static my.awesome.lavanya.notesapp.MainActivity.titleslist;

public class Voicenotescreen extends AppCompatActivity {
    private static final String TAG =Voicenotescreen.class.getSimpleName() ;
    ImageButton imageButton;
    EditText editText;
    String tosave="";
    boolean written=false;

    String newnote;
    DBHandler db = new DBHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voicenotescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageButton=(ImageButton)findViewById(R.id.imageButton);
        editText= (EditText) findViewById(R.id.editText);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptforspeech();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // tosave.append(charSequence);
                tosave.concat(charSequence.toString());
                written=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void promptforspeech(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say something");

        try {

            startActivityForResult(intent,1);
        }catch (ActivityNotFoundException e){
            Toast.makeText(Voicenotescreen.this,"Sorry, your device doesnt support speech language",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK && data !=null){
                    ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            tosave= tosave+results.get(0);
                       // editText.setText(results.get(0), TextView.BufferType.EDITABLE);
                    editText.append(" "+results.get(0));
                    results.clear();
                    written=true;

                }
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            if(written && !(editText.getText().toString().isEmpty()) && !(tosave.toString().isEmpty()) ) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM/dd/yyyy HH:mm:ss", Locale.getDefault());
                Date date = new Date();
              //  String saved=String.valueOf(tosave);
                String[] splitedstr=tosave.split(" ");
                newnote=splitedstr[0]+" "+ splitedstr[1];
                titleslist.add(0, newnote);
                dateslist.add(0,simpleDateFormat.format(date));
                Log.d(TAG,"The final text"+tosave);
                db.addNotes(newnote, tosave,simpleDateFormat.format(date));
                //arrayAdapter.notifyDataSetChanged();
                rvAdapter.notifyDataSetChanged();
            }
            Log.d(TAG,"inside home");
            finish();
            return  true;

        }
        else if(item.getItemId()==R.id.action_delete){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure, you want to delete");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(written) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM/dd/yyyy HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            String saved=String.valueOf(tosave);
            String[] splitedstr=saved.split(" ");
            newnote=splitedstr[0]+" "+splitedstr[1];
            titleslist.add(0, newnote);
            dateslist.add(0,simpleDateFormat.format(date));
            db.addNotes(newnote,tosave,simpleDateFormat.format(date));
            //arrayAdapter.notifyDataSetChanged();
            rvAdapter.notifyDataSetChanged();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note,menu);

        return super.onCreateOptionsMenu(menu);
    }
}
