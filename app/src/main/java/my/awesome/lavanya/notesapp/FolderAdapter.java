package my.awesome.lavanya.notesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import static my.awesome.lavanya.notesapp.Foldercreate.folderAdapter;
import static my.awesome.lavanya.notesapp.Foldercreate.recyclerView;

/**
 * Created by lavanya on 3/14/17.
 */

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
    static ArrayList<String> foldersl;
    static ArrayList<Integer> numoffiles;
     Context mcontext;
    public FolderAdapter(ArrayList<String> foldersn, Context context,ArrayList<Integer> numoffiles){
        foldersl=foldersn;
        mcontext=context;
        this.numoffiles=numoffiles;
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_folder,parent,false);
        FolderViewHolder folderViewHolder=new FolderViewHolder(view,mcontext);
        return folderViewHolder;

    }

    @Override
    public void onBindViewHolder(FolderViewHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.foldericon);
        holder.textViewf.setText(foldersl.get(position));
        holder.textViewn.setText(numoffiles.get(position)+ " Notes");
    }



    @Override
    public int getItemCount() {
        return foldersl.size();
    }

    public static class FolderViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewf;
        TextView textViewn;
        public FolderViewHolder(View itemView, final Context mcontext) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.imgview);
            textViewf= (TextView) itemView.findViewById(R.id.txtviewf);
            textViewn= (TextView) itemView.findViewById(R.id.txtviewn);
            itemView.setLongClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mcontext,FolderOpen.class);
                    String name=foldersl.get(getAdapterPosition());
                    intent.putExtra("foldernames",name);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(mcontext, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    alertDialogBuilder.setMessage("Are you sure, you want to delete");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String dirname=foldersl.get(getAdapterPosition());
                            int x=getAdapterPosition();
                            File dir = new File(Environment.getExternalStorageDirectory().toString()+"/lavanya/"+ dirname);
                            if (dir.isDirectory()) {
                                String[] children = dir.list();
                                for (int j = 0; j < children.length; j++) {
                                    new File(dir, children[j]).delete();
                                }
                                dir.delete();
                            }

                            File f = new File(Environment.getExternalStorageDirectory().toString()+"/lavanya/");
                            File[] files = f.listFiles();
                            foldersl.clear();
                            numoffiles.clear();
                            for (File inFile : files) {
                                if (inFile.isDirectory()) {
                                    // is directory

                                    String[] fileslist=inFile.toString().split("/");
                                    foldersl.add(fileslist[fileslist.length-1]);
                                    File[] childfiles=inFile.listFiles();
                                    numoffiles.add(childfiles.length);
                                }
                            }

                            folderAdapter=new FolderAdapter(foldersl,mcontext,numoffiles);
                            recyclerView.setAdapter(folderAdapter);
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    alertDialog.show();

                    return true;
                }
            });
        }
    }
}
