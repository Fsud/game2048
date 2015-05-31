package com.example.game2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public MainActivity(){
		mainActivity = this;//һ����Mainactivity�͸��Լ���˽�б�����ֵ������
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	//	Intent intent = getIntent();
	//	String game = intent.getStringExtra("game");
	//	String[] gamestr = intent.getStringExtra("game").split(",");
	//	int[] gameInt = new int[16];
	//	for(int i =0;i<gamestr.length;i++){
	//		gameInt[i]=Integer.parseInt(gamestr[i]);
	//	}
	//	GameView.setCardsMapNum(gameInt);
		tvScore=(TextView)findViewById(R.id.tvScore);
		saveGame=(Button)findViewById(R.id.savegame);
		saveGame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int[] savef = new int[16];
				savef = GameView.getCardsMapNum();
				System.out.println(savef[0]);
				Bundle b = new Bundle();
				b.putIntArray("savefe", savef);
			
				Intent intent = new Intent(MainActivity.this,ChooseGameActivity.class);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
	}
	
	public void clearScore(){
		score = 0;
	}
	public void showScore(){
		tvScore.setText(score+"");
	}
	public void addScore(int s){
		score+= s;
		showScore();
	}
	private int score = 0;
	
	private TextView tvScore;
	private Button saveGame;
	private static MainActivity mainActivity= null;
	
	public static MainActivity getMainActivity(){
		return mainActivity;
	}
}
