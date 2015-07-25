package slj.ojisan;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
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

import android.os.Handler;
import java.util.Timer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

// 広告関連
import jp.co.imobile.sdkads.android.FailNotificationReason;
import jp.co.imobile.sdkads.android.ImobileSdkAd;
import jp.co.imobile.sdkads.android.ImobileSdkAdListener;
import android.view.ViewGroup;

public class MainActivity extends ActionBarActivity  {
    static final String PUBLISHER_ID="21189";
    static final String MEDIA_ID="184633";
    static final String SPOT_ID="508532";

    /** アプリケーションのContext */
    private static MainActivity instance;     /* Contextの宣言 */
    private static int ImageList[];           /* 画像IDリスト */
    private static int imgage_switch_counter; /* 画像切替数値の取得モデル */
    private static int user_click_counter;    /* ユーザーがクリックした回数を保持 */
    private TextView  txtTimeInstance;

    // 時間で利用
    private Handler mHandler;
    private Timer mTimer;
    // 時刻表示のフォーマット
    private long startTime = -1;
    private long diffTime;

    // MainActivityのインスタンスを設定
    public MainActivity() {

        // 全て初期化処理を実施
        this.initialize();

        // MainActivityのインスタンスを保存
        instance = this;
    }

    // 画像切替モデル初期化+画像切替値を設定
    // 補足: onCreate()で実行しないとまだactivityが初期化されていない為null poiner exceptionがでる。
    private void setImageSwitchValue()
    {
        ModelMax max = new ModelMax(MainActivity.getInstance());            // DBからMAXを値を取得するクラスを初期化
        MainActivity.imgage_switch_counter = Integer.valueOf(max.getMax());   // 画像切替数値を設定
    }

    // 切替画像リストの作成
    // 補足: onCreate()で実行しないとまだactivityが初期化されていない為null poiner exceptionがでる。
    private void setImageList()
    {
        MainActivity.ImageList = new int[3];
        // 画像のIDを全て配列に保存
        MainActivity.ImageList[0] = R.drawable.ojisan_1;
        MainActivity.ImageList[1] = R.drawable.ojisan_2;
        MainActivity.ImageList[2] = R.drawable.ojisan_3;
    }

    // 初期化メソッド
    public void initialize()
    {
        MainActivity.instance = null;          // MainActivityのインスタンスを保存する変数の初期化
        MainActivity.ImageList = null;         // 表示対象の画像のIDを保存する変数の初期化
        MainActivity.imgage_switch_counter = 0; // 画像切替変数の初期化
        MainActivity.user_click_counter = 0;    // ユーザーがクリックした回数を保持する変数の初期化
    }

    // MainActivityのインスタンスの取得
    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.広告スポットの登録
        ImobileSdkAd.registerSpotFullScreen(MainActivity.this, PUBLISHER_ID, MEDIA_ID, SPOT_ID);
        //2.広告取得開始
        ImobileSdkAd.start(SPOT_ID);
        //3. 広告表示
        ImobileSdkAd.showAd(this, SPOT_ID);
        ImobileSdkAd.showAd(this, SPOT_ID, 1749, 1749, true);

        this.setImageSwitchValue();  // イメージ切替値の設定
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
        final TextView  txtTimeMsg = (TextView)  this.findViewById(R.id.textView3);

        // 経過時間を表示
        mHandler = new Handler(getMainLooper());
        mTimer = new Timer();
        // 一秒ごとに定期的に実行します。
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        if (startTime != -1) {

                            // 経過時間を表示
                            diffTime = (System.currentTimeMillis() - startTime) / 1000;
                            if( diffTime < 10 ) {
                                // 時刻表示をするTextView
                                txtTimeMsg.setText(String.valueOf(diffTime+1) + " 秒");
                            }
                            else
                            {
                                startTime = -1;
                                txtTimeMsg.setVisibility(View.INVISIBLE);
                                imgInstance.setVisibility(View.INVISIBLE);
                                MainActivity.user_click_counter = 0;
                                iRevengeBtn.setVisibility(View.VISIBLE);       // リベンジボタンの表示
                            }
                        }
                    }
                });}
        },0,1000);

        // スタートボタン及びおじさんのタップ処理を実行
        iBtn.setOnClickListener(new OnClickListener() {// ボタンをクリックしたときに働いてくれるオブジェクト
            public int counter = 0;                                          /* ユーザーがクリックした回数を保持する変数 */

            @Override
            public void onClick(View v) {               // ボタンをクリックした時に実行されるコード
                if (startTime == -1) {
                    startTime = System.currentTimeMillis();// スタートボタンが押された時間を代入
                    this.counter = 0;
                }
                // ゲームスタート時の初期処理を実行
                if (this.counter == 0) {
                    txtMsgInstance.setVisibility(View.INVISIBLE);                                           // 「スタート!!」テキストを非表示にする。
                    txtTitleInstance.setText("いまだ！！おじさんをタップ♪タップ♪");                     // タイトルのメッセージを設定
                    imgInstance.setImageResource(MainActivity.ImageList[MainActivity.user_click_counter]);   // 最初の画像へ切替
                    MainActivity.user_click_counter++;    // 次の画像へスイッチ
                    this.counter++;                     // ユーザーがクリックした回数をカウントアップ
                    return;
                }
                // 画像の切替処理
                this.counter++;// ユーザーがクリックした回数をカウントアップ
                if (MainActivity.user_click_counter == MainActivity.ImageList.length) {
                    // ゲームが終わった時の処理
                    imgInstance.setVisibility(View.INVISIBLE);  // 画像の非表示
                    txtMsgInstance.setText("YOU WIN!!");          // テキストメッセージの表示
                    txtMsgInstance.setTextSize(40);                 // テキストサイズの設定
                    txtTitleInstance.setText("♪おじさん♪");      // テキスト内容を設定
                    txtMsgInstance.setVisibility(View.VISIBLE);    // スタート!!テキストを非表示にする。
                    iRevengeBtn.setVisibility(View.VISIBLE);       // リベンジボタンの表示
                    this.counter = 0;                             // カウンターを0に

                } else if (MainActivity.imgage_switch_counter == this.counter) {
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
            public void onClick(View v) {                      // ボタンをクリックした時に実行されるコード
                imgInstance.setVisibility(View.VISIBLE);        // 画像の表示
                MainActivity.user_click_counter = 0;            // ユーザーがクリックした回数を初期化

                txtMsgInstance.setText("スタート!!");          // テキストメッセージの表示
                txtMsgInstance.setVisibility(View.VISIBLE);    // スタート!!テキストを非表示にする。

                imgInstance.setImageResource(R.drawable.start); // スタートボタンの表示
                iRevengeBtn.setVisibility(View.INVISIBLE);     //  リベンジボタンを非表示
            }
        });

        //スポットへのリスナーの設定
        ImobileSdkAd.setImobileSdkAdListener(SPOT_ID, new ImobileSdkAdListener() {
            @Override
            public void onAdReadyCompleted() {
                //広告の表示準備が完了した際に呼び出されます
            }

            @Override
            public void onAdShowCompleted() {
                //広告が表示された際に呼び出されます
            }

            @Override
            public void onAdCliclkCompleted() {
                //広告がクリックされた際に呼び出されます
            }

            @Override
            public void onAdCloseCompleted() {
                //広告が閉じられた際に呼び出されます
            }

            @Override
            public void onFailed(FailNotificationReason reason) {
                //広告の取得に失敗した際に呼び出されます。（reasonは、失敗理由が設定されます）
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

    @Override
    protected void onDestroy() {
        //Activity廃棄時の後処理
        ImobileSdkAd.activityDestory();
        super.onDestroy();
    }
    /*
    @Override
    protected void onPasue () {
        //広告の取得を停止
        ImobileSdkAd.stopAll();
        super.onPause();
    }*/
    @Override
    protected void onResume () {
        //広告の取得を再開
        ImobileSdkAd.startAll();
        super.onResume();
    }
}
