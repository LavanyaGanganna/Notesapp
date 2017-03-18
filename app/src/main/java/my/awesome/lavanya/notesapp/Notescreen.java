package my.awesome.lavanya.notesapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static my.awesome.lavanya.notesapp.MainActivity.dateslist;
import static my.awesome.lavanya.notesapp.MainActivity.notes;
import static my.awesome.lavanya.notesapp.MainActivity.recyclerView;
import static my.awesome.lavanya.notesapp.MainActivity.rvAdapter;
import static my.awesome.lavanya.notesapp.MainActivity.titleslist;


public class Notescreen extends AppCompatActivity {
    private static final String TAG = Notescreen.class.getSimpleName();
    EditText editext;
    Lineedittext editext1;
    DBHandler db = new DBHandler(this);
    String newnote=null;
    String newbody=null;
    boolean written=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notescreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         editext= (EditText) findViewById(R.id.titletext);
         editext1= (Lineedittext) findViewById(R.id.bodytext);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        editext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newnote=String.valueOf(charSequence);
                written=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editext1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              newbody=String.valueOf(charSequence);
                written=true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            if(written) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM/dd/yyyy HH:mm:ss", Locale.getDefault());
                Date date = new Date();
                titleslist.add(0, newnote);
                dateslist.add(0,simpleDateFormat.format(date));
                db.addNotes(newnote, newbody, simpleDateFormat.format(date));
                Log.d(TAG,"inside home");
               // rvAdapter=new RvAdapter(titleslist,getApplicationContext());
                rvAdapter.notifyDataSetChanged();
                //recyclerView.setAdapter(rvAdapter);
            }

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
            titleslist.add(0, newnote);
            dateslist.add(0,simpleDateFormat.format(date));
            db.addNotes(newnote, newbody,simpleDateFormat.format(date));
            rvAdapter.notifyDataSetChanged();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note,menu);

        return super.onCreateOptionsMenu(menu);
    }
}
