package my.awesome.lavanya.notesapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class Foldercreate extends AppCompatActivity {
    static ArrayList<String> folderslist=new ArrayList<>();
    static ArrayList<Integer> numoffiles=new ArrayList<>();
  //  ListView listView;
    static RecyclerView recyclerView;
    static FolderAdapter folderAdapter;
    //ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foldercreate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  listView= (ListView) findViewById(R.id.folderlistview);
        recyclerView= (RecyclerView) findViewById(R.id.folderlistview);
            folderslist.clear();
        numoffiles.clear();
        File f = new File(Environment.getExternalStorageDirectory().toString()+"/lavanya/");
        File[] files = f.listFiles();
        for (File inFile : files) {
            if (inFile.isDirectory()) {
                // is directory

                String[] fileslist=inFile.toString().split("/");
                folderslist.add(fileslist[fileslist.length-1]);
                File[] childfiles=inFile.listFiles();
                numoffiles.add(childfiles.length);

            }
        }

         folderAdapter=new FolderAdapter(folderslist,this,numoffiles);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Foldercreate.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(folderAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_folder,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.action_add){
            createnewfolderfunc();
        }

        return super.onOptionsItemSelected(item);
    }

    private  void createnewfolderfunc(){
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        alertDialog.setTitle("Create Folder");
        alertDialog.setMessage("Enter Name");
        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String foldername=input.getText().toString();
                File file = new File(Environment.getExternalStorageDirectory().toString()+"/lavanya/"+foldername);
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        Log.e("TravellerLog :: ", "Problem creating Note folder");

                    }
                }
                File f = new File(Environment.getExternalStorageDirectory().toString()+"/lavanya/");
                File[] files = f.listFiles();
                folderslist.clear();
                for (File inFile : files) {
                    if (inFile.isDirectory()) {
                        // is directory

                        String[] fileslist=inFile.toString().split("/");
                        folderslist.add(fileslist[fileslist.length-1]);
                        File[] childfiles=inFile.listFiles();
                        numoffiles.add(childfiles.length);
                    }
                }

                 folderAdapter=new FolderAdapter(folderslist,getApplicationContext(),numoffiles);
                recyclerView.setAdapter(folderAdapter);

            }
        });
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        android.app.AlertDialog alertDialog1=alertDialog.create();
        alertDialog1.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog1.show();
    }

}
