package com.example.game2048;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ChooseGameActivity extends Activity {
	private int[] savetosql; // 接收方数组
	private MyDatabaseHelper dbHelper;
	private List<String> gameV = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_game);
		dbHelper = new MyDatabaseHelper(this, "GameStore.db", null, 1);
		dbHelper.getWritableDatabase();
		Button addtosql = (Button) findViewById(R.id.savethisgame);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Vector<String> tempV = new Vector<String>();
		
		

		Cursor cursor = db.query("Game", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				// 遍历Cursor对象，取出数据并打印
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String time = cursor.getString(cursor
						.getColumnIndex("time"));
				String gameGet = cursor.getString(cursor.getColumnIndex("string"));
				gameV.add(gameGet);
				tempV.addElement(id+"时间:"+time);
		
			} while (cursor.moveToNext());
			
		}

	//	String xx = tempV.toString();
	//	String[] yy = new String[tempV.size()/2];
	//	yy = xx.split(",");
		String[] zz =new String[tempV.size()];
		tempV.toArray(zz);
		ListView listView = (ListView) findViewById(R.id.list_view);
		listView.setAdapter(new ArrayAdapter<String>(ChooseGameActivity.this,android.R.layout.simple_list_item_1,zz));

		Bundle b = getIntent().getExtras();
		if (b == null) {
			Toast.makeText(this, "gg", Toast.LENGTH_SHORT).show();
		}
		savetosql = b.getIntArray("savefe");

		addtosql.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String strsavetosql = join(",", savetosql);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("time", getTime());
				values.put("string", strsavetosql);
				db.insert("Game", null, values); // 插入第一条数据

				// Toast.makeText(ChooseGameActivity.this, strsavetosql,
				// Toast.LENGTH_LONG).show();
				// Log.d("test", strsavetosql);
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id){
				String tempg=gameV.get(position);
				String[] gameStr = tempg.split(",");
				int[] gameInt = new int[16];
				for(int i =0;i<gameStr.length;i++){
					gameInt[i]=Integer.parseInt(gameStr[i]);
					
				}
				GameView.setCardsMapNum(gameInt);
				ChooseGameActivity.this.finish();
			}
		
		});
	}
	

	public String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	public String join(String join, int[] intAry) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < intAry.length; i++) {
			if (i == (intAry.length - 1)) {
				sb.append(intAry[i]);
			} else {
				sb.append(intAry[i]).append(join);
			}
		}
		return new String(sb);
	}

}
