package my.awesome.lavanya.notesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lavanya on 3/6/17.
 */

public class notesobject implements Parcelable{
    String title;
    String body;
    String datemodify;

    public notesobject(String title,String body,String datemodify){
        this.title=title;
        this.body=body;
        this.datemodify=datemodify;
    }

    protected notesobject(Parcel in) {
        title = in.readString();
        body = in.readString();
        datemodify=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(datemodify);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<notesobject> CREATOR = new Creator<notesobject>() {
        @Override
        public notesobject createFromParcel(Parcel in) {
            return new notesobject(in);
        }

        @Override
        public notesobject[] newArray(int size) {
            return new notesobject[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDatemodify(){ return  datemodify;}
}
