package my.awesome.lavanya.notesapp;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static my.awesome.lavanya.notesapp.FolderOpen.arrayAdapter;
import static my.awesome.lavanya.notesapp.FolderOpen.filenames;
import static my.awesome.lavanya.notesapp.MainActivity.rvAdapter;
import static my.awesome.lavanya.notesapp.MainActivity.titleslist;

public class Foldernotereopen extends AppCompatActivity {
    private static final String TAG = Foldernotereopen.class.getSimpleName() ;
    EditText editext;
    Lineedittext editext1;
    String rcvd;
    String dirname;
    String newtitle=null;
    boolean written=false;
    String newbody=null;
    BufferedReader br = null;
    StringBuilder textBuilder;
    File dirnames;
    File file;
    int muns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foldernotereopen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editext= (EditText) findViewById(R.id.titletext);
        editext1= (Lineedittext) findViewById(R.id.bodytext);
        Intent i=getIntent();
        rcvd=i.getStringExtra("notestitle");
        Log.d(TAG,"the received string"+ rcvd);
        dirname=i.getStringExtra("notedir");
        muns=i.getIntExtra("listnum",-1);
         dirnames = new File(Environment.getExternalStorageDirectory().toString() + "/lavanya/" + dirname);
        String[] splitedvals=rcvd.split("\\r?\\n");
         file = new File(dirnames,splitedvals[0]);
        try {
            br = new BufferedReader(new FileReader(file));
            textBuilder = new StringBuilder();
            String line;
            while((line = br.readLine()) != null) {
                textBuilder.append(line);
                textBuilder.append("\n");

            }
            br.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        editext.setText(rcvd);
        editext1.setText(textBuilder.toString());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
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
                 /*   String lines[] = newtitle.split("\\r?\\n");
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

                    filenames.remove(muns);
                    arrayAdapter.remove(rcvd);
                    // String filename = editext.getText().toString();
                    try {
                        FileOutputStream outputStream=new FileOutputStream(file);
                        //outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(newbody.getBytes());
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Date lastModDate = new Date(file.lastModified());
                    filenames.add(0,file.getName()+"\n"+lastModDate.toString());
                    arrayAdapter.notifyDataSetChanged();

                }
                else if(newtitle.equals("")&& newbody.equals("")){
                    file.delete();
                    arrayAdapter.remove(rcvd);
                    arrayAdapter.notifyDataSetChanged();

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
                    file.delete();
                    arrayAdapter.remove(rcvd);
                  arrayAdapter.notifyDataSetChanged();
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
            if(!(newtitle.equals("")) && !(newbody.equals(""))) {

                filenames.remove(muns);
                arrayAdapter.remove(rcvd);
                // String filename = editext.getText().toString();
                try {
                    FileOutputStream outputStream=new FileOutputStream(file);
                    //outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(newbody.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Date lastModDate = new Date(file.lastModified());
                filenames.add(0,file.getName()+"\n"+lastModDate.toString());
                arrayAdapter.notifyDataSetChanged();

            }
            else if(newtitle.equals("")&& newbody.equals("")){
                file.delete();
                arrayAdapter.remove(rcvd);
                arrayAdapter.notifyDataSetChanged();

            }

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note,menu);

        return super.onCreateOptionsMenu(menu);
    }

}
