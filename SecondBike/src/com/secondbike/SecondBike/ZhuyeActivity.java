package com.secondbike.SecondBike;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ZhuyeActivity extends Activity {

	private Button btn1, btn2, btn3, btn4;
	private ButtonListener btnListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhuye);
		SysApplication.getInstance().addActivity(this);
		init();// ��ʼ��
	}

	public void init() {
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn4 = (Button) findViewById(R.id.button4);
		btnListener = new ButtonListener();
		btn1.setOnClickListener(btnListener);
		btn2.setOnClickListener(btnListener);
		btn3.setOnClickListener(btnListener);
		btn4.setOnClickListener(btnListener);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// �˳�
			DiagListener1 diagListener = new DiagListener1();
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ZhuyeActivity.this, AlertDialog.THEME_HOLO_LIGHT);
			builder.setTitle("�˳�����");
			builder.setMessage("�Ƿ��˳���");
			builder.setPositiveButton("ȷ��", diagListener);
			builder.setNeutralButton("ȡ��", diagListener);
			AlertDialog dialog = builder.create();
			dialog.show();

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zhuye, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class DiagListener1 implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:
				SysApplication.getInstance().exit();
				break;
			case AlertDialog.BUTTON_NEGATIVE:

				break;
			}
		}

	}

	// ��ť������
	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.button1) {
				// ��ǰҳ
			} else if (id == R.id.button2) {
				// ����
				Intent intent = new Intent(ZhuyeActivity.this,
						CarLibaryActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			} else if (id == R.id.button3) {
				// ����
				Intent intent = new Intent(ZhuyeActivity.this,
						FenleiActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);

			} else if (id == R.id.button4) {
				// �ҵ�
				Intent intent = new Intent(ZhuyeActivity.this,
						MineActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
		}
	}
}
