package slj.ojisan;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

// イメージボタンで利用
import android.widget.ImageButton;

// iBtn.setOnClickListenerで利用
import android.view.View;
import android.view.View.OnClickListener;

// 画像とテキストの切替に利用
import android.widget.ImageView;
import android.widget.TextView;

// 戻るボタンでアプリを終了するのに利用
import android.view.KeyEvent;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends ActionBarActivity {

    /** アプリケーションのContext */
    private static MainActivity instance;     /* Contextの宣言 */
    private static int ImageList[];           /* 画像IDリスト */
    private static int imgage_switch_counter; /* 画像切替数値の取得モデル */
    private static int user_click_counter;    /* ユーザーがクリックした回数を保持 */

    // MainActivityのインスタンスを設定
    public MainActivity() {

        // 全て初期化処理を実施
        this.initialize();

        // MainActivityのインスタンスを保存
        instance = this;
    }

    // 画像切替モデル初期化+画像切替値を設定
    // 補足: onCreate()で実行しないとまだactivityが初期化されていない為null poiner exceptionがでる。
    private void setImageSwichValue()
    {
        ModelMax max = new ModelMax(MainActivity.getInstance());            // DBからMAXを値を取得するクラスを初期化
        MainActivity.imgage_switch_counter = Integer.valueOf(max.getMax());   // 画像切替数値を設定
    }

    // 切替画像リストの作成
    // 補足: onCreate()で実行しないとまだactivityが初期化されていない為null poiner exceptionがでる。
    private void setImageList()
    {
        MainActivity.ImageList = new int[31];
        // 画像のIDを全て配列に保存
        MainActivity.ImageList[0] = R.drawable.main_logo;
        MainActivity.ImageList[1] = R.drawable.main_logo1;
        MainActivity.ImageList[2] = R.drawable.main_logo2;
        MainActivity.ImageList[3] = R.drawable.main_logo3;
        MainActivity.ImageList[4] = R.drawable.main_logo4;
        MainActivity.ImageList[5] = R.drawable.main_logo5;
        MainActivity.ImageList[6] = R.drawable.main_logo6;
        MainActivity.ImageList[7] = R.drawable.main_logo7;
        MainActivity.ImageList[8] = R.drawable.main_logo8;
        MainActivity.ImageList[9] = R.drawable.main_logo9;
        MainActivity.ImageList[10] = R.drawable.main_logo10;
        MainActivity.ImageList[11] = R.drawable.main_logo11;
        MainActivity.ImageList[12] = R.drawable.main_logo12;
        MainActivity.ImageList[13] = R.drawable.main_logo13;
        MainActivity.ImageList[14] = R.drawable.main_logo14;
        MainActivity.ImageList[15] = R.drawable.main_logo15;
        MainActivity.ImageList[16] = R.drawable.main_logo16;
        MainActivity.ImageList[17] = R.drawable.main_logo17;
        MainActivity.ImageList[18] = R.drawable.main_logo18;
        MainActivity.ImageList[19] = R.drawable.main_logo19;
        MainActivity.ImageList[20] = R.drawable.main_logo20;
        MainActivity.ImageList[21] = R.drawable.main_logo21;
        MainActivity.ImageList[22] = R.drawable.main_logo22;
        MainActivity.ImageList[23] = R.drawable.main_logo23;
        MainActivity.ImageList[24] = R.drawable.main_logo24;
        MainActivity.ImageList[25] = R.drawable.main_logo25;
        MainActivity.ImageList[26] = R.drawable.main_logo26;
        MainActivity.ImageList[27] = R.drawable.main_logo27;
        MainActivity.ImageList[28] = R.drawable.main_logo28;
        MainActivity.ImageList[29] = R.drawable.main_logo29;
        MainActivity. ImageList[30] = R.drawable.main_logo30;
    }

    // 初期化メソッド
    public void initialize()
    {
        MainActivity.instance = null;          // MainActivityのインスタンスを保存する変数の初期化
        MainActivity.ImageList = null;         // 表示対象の画像のIDを保存する変数の初期化
        MainActivity.imgage_switch_counter = 0;  // 画像切替変数の初期化
        MainActivity.user_click_counter = 0;         // ユーザーがクリックした回数を保持する変数の初期化
    }

    // MainActivityのインスタンスの取得
    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setImageSwichValue();  // イメージ切替値の設定
        this.setImageList();        // イメージリストの初期化

        // イメージボタンのインスタンス生成
        ImageButton iBtn = (ImageButton) this.findViewById(R.id.startButton);

        // 画像のウィジェットの初期化
        final ImageView imgInstance = (ImageView) this.findViewById(R.id.startButton);

        // リベンジボタンを初期化し初期画面では非表示
        final ImageButton iRevengeBtn = (ImageButton) this.findViewById(R.id.imageRevengeButton);
        iRevengeBtn.setVisibility(View.INVISIBLE);

        // テキストウィジェットの初期化
        final TextView  txtTitleInstance = (TextView)  this.findViewById(R.id.textView);
        final TextView  txtMsgInstance = (TextView)  this.findViewById(R.id.textView2);

        // スタートボタン及びおじさんのタップ処理を実行
        iBtn.setOnClickListener(new OnClickListener() {// ボタンをクリックしたときに働いてくれるオブジェクト
            public int counter = 0;                                          /* ユーザーがクリックした回数を保持する変数 */
            @Override
            public void onClick(View v) {               // ボタンをクリックした時に実行されるコード
                // ゲームスタート時の初期処理を実行
                if( this.counter == 0 )
                {
                    txtMsgInstance.setVisibility(View.INVISIBLE);                                           // 「スタート!!」テキストを非表示にする。
                    txtTitleInstance.setText("いまだ！！おじさんをタップ♪タップ♪");                     // タイトルのメッセージを設定
                    imgInstance.setImageResource(MainActivity.ImageList[MainActivity.user_click_counter]);   // 最初の画像へ切替
                    MainActivity.user_click_counter++;    // 次の画像へスイッチ
                    this.counter++;                     // ユーザーがクリックした回数をカウントアップ
                    return;
                }
                // 画像の切替処理
                this.counter++;// ユーザーがクリックした回数をカウントアップ
                if( MainActivity.user_click_counter == MainActivity.ImageList.length )
                {
                    // ゲームが終わった時の処理
                    imgInstance.setVisibility(View.INVISIBLE);  // 画像の非表示
                    txtMsgInstance.setText("YOU WIN!!");          // テキストメッセージの表示
                    txtMsgInstance.setTextSize(40);                 // テキストサイズの設定
                    txtTitleInstance.setText("♪おじさん♪");      // テキスト内容を設定
                    txtMsgInstance.setVisibility(View.VISIBLE);    // スタート!!テキストを非表示にする。
                    iRevengeBtn.setVisibility(View.VISIBLE);       // リベンジボタンの表示
                    this.counter = 0;                             // カウンターを0に

                }
                else if( MainActivity.imgage_switch_counter == this.counter )
                {
                    // 次の画像に切替
                    imgInstance.setImageResource(MainActivity.ImageList[MainActivity.user_click_counter]);
                    this.counter = 0;                 // カウンターを初期値に設定
                    MainActivity.user_click_counter++;  // 次の画像へスイッチする
                }
            }
        });

        // リベンジボタンの処理
        iRevengeBtn.setOnClickListener(new OnClickListener() {// ボタンをクリックしたときに働いてくれるオブジェクト
            @Override
            public void onClick(View v) {               // ボタンをクリックした時に実行されるコード
                imgInstance.setVisibility(View.VISIBLE);        // 画像の表示
                MainActivity.user_click_counter  = 0;            // ユーザーがクリックした回数を初期化

                txtMsgInstance.setText("スタート!!");          // テキストメッセージの表示
                txtMsgInstance.setVisibility(View.VISIBLE);   // スタート!!テキストを非表示にする。

                imgInstance.setImageResource(R.drawable.start); // スタートボタンの表示
                iRevengeBtn.setVisibility(View.INVISIBLE);     //  リベンジボタンを非表示
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 戻るボタンでアプリを終了
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setTitle("おじさんの終了")
                    .setMessage("おじさんを終了してよろしいですか？")
                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO 自動生成されたメソッド・スタブ
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("いいえ", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO 自動生成されたメソッド・スタブ

                        }
                    })
                    .show();

            return true;
        }
        return false;
    }

}
