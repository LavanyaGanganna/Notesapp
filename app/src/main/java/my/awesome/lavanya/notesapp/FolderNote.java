package my.awesome.lavanya.notesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static my.awesome.lavanya.notesapp.FolderOpen.arrayAdapter;
import static my.awesome.lavanya.notesapp.FolderOpen.filenames;
import static my.awesome.lavanya.notesapp.MainActivity.rvAdapter;
import static my.awesome.lavanya.notesapp.MainActivity.titleslist;

public class FolderNote extends AppCompatActivity {
    private static final String TAG = FolderNote.class.getSimpleName() ;
    EditText editext;
    Lineedittext editext1;
    String newnote=null;
    String newbody=null;
    boolean written=false;
    File dir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editext= (EditText) findViewById(R.id.titletext);
        editext1= (Lineedittext) findViewById(R.id.bodytext);
        Intent intent=getIntent();
        String dirname=intent.getStringExtra("directorynam");
          dir = new File(Environment.getExternalStorageDirectory().toString() + "/lavanya/" + dirname);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                File file = new File(dir, editext.getText().toString());
               // String filename = editext.getText().toString();
                try {
                    FileOutputStream outputStream=new FileOutputStream(file);
                    //outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(newbody.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG,"inside home");
                Date lastModDate = new Date(file.lastModified());
                filenames.add(0,file.getName()+"\n"+lastModDate.toString());
                // rvAdapter=new RvAdapter(titleslist,getApplicationContext());
                arrayAdapter.notifyDataSetChanged();
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
            File file = new File(dir, editext.getText().toString());
            // String filename = editext.getText().toString();
            try {
                FileOutputStream outputStream=new FileOutputStream(file);
                //outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(newbody.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG,"inside home");
            Date lastModDate = new Date(file.lastModified());
            filenames.add(0,file.getName()+"\n"+lastModDate.toString());
            arrayAdapter.notifyDataSetChanged();

        }

    }
}
