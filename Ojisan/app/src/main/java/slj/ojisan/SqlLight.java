package slj.ojisan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by slj on 2015/05/30.
 * データベースの作成クラス
 */

public class SqlLight extends SQLiteOpenHelper {
    public SqlLight(Context context){
        super(context,"MyDB", null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table MAX(" + " id integer primary key," + "max integer not null" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
