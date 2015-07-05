package slj.ojisan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by slj on 2015/06/14.
 */
public class ModelMax {

    public Context MainActivityContext;

    // コンストラクター
    // CLASSが生成される時に呼び出し、main activityの保存と、データを1件登録する
    public ModelMax(Context context)
    {
        this.MainActivityContext = context; // Main activityのContextを保存

        // DBにデータが無ければ1件登録
        SqlLight helper = new SqlLight(this.MainActivityContext);               /* DBのインスタンス生成 */
        SQLiteDatabase db = helper.getReadableDatabase();             /* 読み込み可能なDBをインスタンス生成 */
        try{
            Cursor cursor = db.rawQuery(this.getSelectString(), null);
            // DBにデータが無ければ1件登録
            if( cursor.getCount() == 0 )
            {
                db.execSQL(this.getInsertString());         // INSERT文を取得しデータを登録
            }
        }
        finally {
            // DBへの接続を切断
            db.close();
        }
    }

    // Max値を取得
    public String getMax(){
        // DB接続インスタンス生成
        SqlLight helper = new SqlLight(this.MainActivityContext);     // DBのインスタンス生成
        SQLiteDatabase db = helper.getReadableDatabase();        // 読み込み可能なDBをインスタンス生成
        String ret = new String("0");                           // SQLの結果を0に初期化

        try{
            // DBへQUERYを投げる
            Cursor cursor = db.rawQuery(this.getSelectString(), null);

            // SELECTしたデータを取得
            while (cursor.moveToNext()) {
                ret = String.valueOf(cursor.getInt(1));
                break;
            }
        }
        finally {
            // DBへの接続を切断
            db.close();
        }
        return ret; // 実行結果を返却
    }

    // データを1件取得するSQL文を生成
    private String getSelectString()
    {
        //SQL作成
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" id");
        sql.append(" ,max");
        sql.append(" FROM MAX");
        sql.append(" LIMIT 1;");
        return sql.toString();
    }

    // データを1件登録するSQL文生成
    private String getInsertString() {
        StringBuilder sqlInsert = new StringBuilder();
        sqlInsert.append(" insert into MAX");
        sqlInsert.append(" VALUES(");
        sqlInsert.append(" 1,");
        sqlInsert.append(" 3");
        sqlInsert.append(" );");
        return sqlInsert.toString();
    }
}
