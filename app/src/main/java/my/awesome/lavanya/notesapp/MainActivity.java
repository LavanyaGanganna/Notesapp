package my.awesome.lavanya.notesapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    private static final String TAG =MainActivity.class.getSimpleName() ;

    static RecyclerView recyclerView;
    static RvAdapter rvAdapter;
    static ArrayList<notesobject> notes=new ArrayList<notesobject>();
    static ArrayList<String> titleslist=new ArrayList<>();
    static ArrayList<String> dateslist=new ArrayList<>();

  DBHandler db = new DBHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notes.clear();
        titleslist.clear();
        notes=db.getAllnotes();

        for(notesobject noteobjs: notes){
            titleslist.add(noteobjs.getTitle());
            dateslist.add(noteobjs.getDatemodify());
        }
        Collections.reverse(titleslist);

        recyclerView= (RecyclerView) findViewById(R.id.recyclviewlist);
        rvAdapter=new RvAdapter(titleslist,dateslist,getApplicationContext());
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rvAdapter);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
             (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.bytext){
            addfunction();
        }
        else if(item.getItemId()== R.id.byvoice){
            addvoicefunction();
        }
        else if(item.getItemId()==R.id.action_search){
            onSearchRequested();
            return true;
        }

        else if(item.getItemId()==R.id.action_sort){
            callsortfunction();
        }
        else if(item.getItemId()==R.id.action_create){
            createnewfolder();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addfunction(){
        Intent intent=new Intent(MainActivity.this,Notescreen.class);
        startActivity(intent);
    }

    private void addvoicefunction(){
        Intent intent=new Intent(MainActivity.this,Voicenotescreen.class);
        startActivity(intent);
    }
    private void callsortfunction(){

        Collections.sort(titleslist);
        rvAdapter.notifyDataSetChanged();
    }

    private void createnewfolder(){
        Intent intent=new Intent(getApplicationContext(),Foldercreate.class);
        startActivity(intent);
    }

}
