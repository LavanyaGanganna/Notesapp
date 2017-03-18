package my.awesome.lavanya.notesapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import static my.awesome.lavanya.notesapp.MainActivity.dateslist;
import static my.awesome.lavanya.notesapp.MainActivity.recyclerView;
import static my.awesome.lavanya.notesapp.MainActivity.rvAdapter;
import static my.awesome.lavanya.notesapp.MainActivity.titleslist;

public class Notesreopen extends AppCompatActivity {
    private static final String TAG = Notesreopen.class.getSimpleName();
    int retnumber;
    EditText editext;
    Lineedittext editext1;
    boolean written=false;
    String newtitle=null;
    String newbody=null;
    notesobject noted;
    String rcvd;
    int todel;
    DBHandler db = new DBHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notesreopen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editext= (EditText) findViewById(R.id.titletext);
        editext1= (Lineedittext) findViewById(R.id.bodytext);

        Intent i=getIntent();
        rcvd=i.getStringExtra("sendingnote");
         //noted=i.getParcelableExtra("sendingnote");
        noted=db.getnoteonly(rcvd);
        retnumber=i.getIntExtra("recordnum",-1);
        Log.d(TAG,"the retnum" + retnumber);
        editext.setText(noted.getTitle());
        editext1.setText(noted.getBody());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                editext.setEnabled(true);
                newtitle=editext.getText().toString();
                editext1.setEnabled(true);
                newbody=editext1.getText().toString();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if( !(charSequence.toString().equals(""))) {
                    newtitle = String.valueOf(charSequence);
                  /*  String lines[] = newtitle.split("\\r?\\n");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM/dd/yyyy HH:mm:ss", Locale.getDefault());
                    Date date = new Date();
                    if (lines.length > 0) {
                        lines[0] = lines[0] + "\n" + simpleDateFormat.format(date);
                        newtitle = lines[0];
                    }*/
                }
                else{
                    newtitle="";
                }
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
                if(!(charSequence.toString().equals(""))) {
                    newbody = String.valueOf(charSequence);
                }
                else{
                    newbody="";
                }
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
               // arrayAdapter.remove(arrayAdapter.getItem(retnumber));
                if(!(newtitle.equals("")) && !(newbody.equals(""))) {
                    Log.d(TAG,"inside if");
                    db.delete(noted.getTitle());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM/dd/yyyy HH:mm:ss", Locale.getDefault());
                    Date date = new Date();
                    rvAdapter.removetextitem(rcvd);
                    titleslist.add(0, newtitle);
                    dateslist.add(0,simpleDateFormat.format(date));
                    rvAdapter.notifyItemInserted(0);
                    db.addNotes(newtitle, newbody,simpleDateFormat.format(date));
                    rvAdapter.notifyDataSetChanged();
                }
                else if(newtitle.equals("")&& newbody.equals("")){
                    Log.d(TAG,"inside else");
                    db.delete(noted.getTitle());
                    rvAdapter.removetextitem(rcvd);
                    rvAdapter.notifyDataSetChanged();
                }
            }

            written=false;
            finish();
            return  true;

        }
        else if(item.getItemId()==R.id.action_delete){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure, you want to delete");
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  //  int count=db.getProfilesCount();
                    //todel=(count-(retnumber+1))+1;
                    db.delete(noted.getTitle());
                    rvAdapter.removeitem(retnumber);
                  //  arrayAdapter.remove(arrayAdapter.getItem(retnumber));
                    //arrayAdapter.notifyDataSetChanged();
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
            // arrayAdapter.remove(arrayAdapter.getItem(retnumber));
            if(!(newtitle.equals("")) && !(newbody.equals(""))) {
                Log.d(TAG,"inside if");
                db.delete(noted.getTitle());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM/dd/yyyy HH:mm:ss", Locale.getDefault());
                Date date = new Date();
                rvAdapter.removetextitem(rcvd);
                titleslist.add(0, newtitle);
                dateslist.add(0,simpleDateFormat.format(date));
                rvAdapter.notifyItemInserted(0);
                db.addNotes(newtitle, newbody,simpleDateFormat.format(date));
                rvAdapter.notifyDataSetChanged();
            }
            else if(newtitle.equals("")&& newbody.equals("")){
                Log.d(TAG,"inside else");
                db.delete(noted.getTitle());
                rvAdapter.removetextitem(rcvd);
                rvAdapter.notifyDataSetChanged();
            }
        }
        written=false;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note,menu);

        return super.onCreateOptionsMenu(menu);
    }
}
