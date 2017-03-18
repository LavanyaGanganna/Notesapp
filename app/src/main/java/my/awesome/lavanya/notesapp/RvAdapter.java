package my.awesome.lavanya.notesapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import static my.awesome.lavanya.notesapp.MainActivity.titleslist;

/**
 * Created by lavanya on 3/14/17.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.OnnoteViewHolder> {
    private static final String TAG = RvAdapter.class.getSimpleName();
    ArrayList<String> titleslists;
    ArrayList<String> dateslists;
     Context mcontext;

    public RvAdapter(ArrayList<String> titless,ArrayList<String> dateslist,Context context){
        titleslists=titless;
        dateslists=dateslist;
       mcontext=context;

    }

    @Override
    public OnnoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_element,parent,false);
        OnnoteViewHolder onnoteViewHolder=new OnnoteViewHolder(view,mcontext);
        return onnoteViewHolder;
    }

    @Override
    public void onBindViewHolder(OnnoteViewHolder holder, int position) {

        holder.notetitle.setText(titleslists.get(position));
        holder.notedate.setText(dateslists.get(position));
    }

    @Override
    public int getItemCount() {
        return titleslists.size();
        //return  titleslist.size();
    }

    public String getItem(int position){
       return titleslists.get(position);
       // return titleslist.get(position);
    }

    public void removeitem(int position){
        int j=0;
        for(String s:titleslists){
            Log.d(TAG,"position" + j + s);
            j++;
        }
        titleslists.remove(position);
         j=0;
        for(String s:titleslists){
            Log.d(TAG,"position" + j + s);
            j++;
        }
       // titleslist.remove(position);
        notifyItemRemoved(position);
    }

    public void removetextitem(String texts){
        int x=0;
        for(String s: titleslists) {
            if (s.equals(texts)) {
                titleslists.remove(x);
                notifyItemRemoved(x);
                break;
            }
            x++;
        }
    }
    public static  class  OnnoteViewHolder extends RecyclerView.ViewHolder {
        TextView notetitle;
        TextView notedate;
        public OnnoteViewHolder(final View itemView, final Context mcontext) {
            super(itemView);
            notetitle= (TextView) itemView.findViewById(R.id.notetitle);
            notedate= (TextView) itemView.findViewById(R.id.notedate);
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String listtext=notetitle.getText().toString();
                   Intent intent=new Intent(mcontext,Notesreopen.class);
                   intent.putExtra("sendingnote",listtext);
                   intent.putExtra("recordnum",getAdapterPosition());
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   mcontext.startActivity(intent);
               }
           });

        }

    }
}
