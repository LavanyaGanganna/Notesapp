package my.awesome.lavanya.notesapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import static my.awesome.lavanya.notesapp.FolderAdapter.numoffiles;
import static my.awesome.lavanya.notesapp.Foldercreate.folderAdapter;
import static my.awesome.lavanya.notesapp.Foldercreate.folderslist;
import static my.awesome.lavanya.notesapp.Foldercreate.recyclerView;
import static my.awesome.lavanya.notesapp.MainActivity.rvAdapter;
import static my.awesome.lavanya.notesapp.MainActivity.titleslist;

public class FolderOpen extends AppCompatActivity {
    static ArrayList<String> filenames = new ArrayList<>();
    ListView listView;
    static ArrayAdapter arrayAdapter;
    String dirname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_open);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        dirname = intent.getStringExtra("foldernames");
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.recyclefolders);
        filenames.clear();
        File dir = new File(Environment.getExternalStorageDirectory().toString() + "/lavanya/" + dirname);
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (File f : children) {
                String name = f.getName();
                Date lastModDate = new Date(f.lastModified());
                filenames.add(name + "\n" + lastModDate.toString());
            }

        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, filenames);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String textnote = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(FolderOpen.this, Foldernotereopen.class);
                intent.putExtra("notestitle", textnote);
                intent.putExtra("notedir", dirname);
                intent.putExtra("listnum", i);
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_openfolder, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_addn) {
            addfunctionnote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addfunctionnote() {
        Intent intent = new Intent(this, FolderNote.class);
        intent.putExtra("directorynam", dirname);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        folderslist.clear();
        numoffiles.clear();
        File f = new File(Environment.getExternalStorageDirectory().toString() + "/lavanya/");
        File[] files = f.listFiles();
        for (File inFile : files) {
            if (inFile.isDirectory()) {
                // is directory
                String[] fileslist = inFile.toString().split("/");
                folderslist.add(fileslist[fileslist.length - 1]);
                // is directory
                File[] childfiles = inFile.listFiles();
                numoffiles.add(childfiles.length);
                folderAdapter = new FolderAdapter(folderslist, getApplicationContext(), numoffiles);
                recyclerView.setAdapter(folderAdapter);
                folderAdapter.notifyDataSetChanged();


            }
        }
    }
}
