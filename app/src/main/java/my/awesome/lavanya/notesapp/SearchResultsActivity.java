package my.awesome.lavanya.notesapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = SearchResultsActivity.class.getSimpleName();
    ArrayList<String> retresults;
    ListView slistView;
    ArrayAdapter sarrayAdapter;
    DBHandler db = new DBHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        retresults=new ArrayList<>();
        slistView= (ListView) findViewById(R.id.searchlistview);
        slistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String listtext=adapterView.getItemAtPosition(i).toString();
                Intent intent=new Intent(getApplicationContext(),Notesreopen.class);
                intent.putExtra("sendingnote",listtext);
                intent.putExtra("recordnum",i);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            showResults(query);
        }
    }

    public void showResults(String keystring){
        //Log.d(TAG,"the string" + keystring);
        retresults=db.searchdata(keystring);
       // Log.d(TAG,"the result" + retresults.toString());
        sarrayAdapter = new ArrayAdapter(SearchResultsActivity.this, android.R.layout.simple_list_item_1,retresults);
        slistView.setAdapter(sarrayAdapter);

    }

}
