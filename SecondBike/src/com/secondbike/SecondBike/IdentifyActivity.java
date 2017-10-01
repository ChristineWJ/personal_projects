package com.secondbike.SecondBike;

import java.util.Random;

import com.secondbike.SecondBike.AboutActivity.DiagListener;
import com.secondbike.blinktophone.BlinkActivity;
import com.secondbike.fogetpwd.FogetPWDActivity;
import com.secondbike.fogetpwd.NextActivity;
import com.secondbike.user.MySQLiteOpenHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IdentifyActivity extends Activity {

	private Button next, cancel;
	private EditText phone, code;
	private ButtonListemer buttonListener;
	private TextView getcode;
	private int randCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.identify);
		SysApplication.getInstance().addActivity(this);
		init();// ��ʼ�����¼�����
		randCode = new Random().nextInt(99999);// ��ʼ��ʱ���Ѿ�����һ����֤�룬��ֹ�û�ֱ������
	}

	public void init() {
		cancel = (Button) findViewById(R.id.cancel);
		next = (Button) findViewById(R.id.next);
		buttonListener = new ButtonListemer();
		cancel.setOnClickListener(buttonListener);
		next.setOnClickListener(buttonListener);
		phone = (EditText) findViewById(R.id.phoneEdittext);
		code = (EditText) findViewById(R.id.code);
		getcode = (TextView) findViewById(R.id.getcode);
		getcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �õ���֤��
				randCode = new Random().nextInt(99999);
				DiagListener diagListener = new DiagListener();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						IdentifyActivity.this, AlertDialog.THEME_HOLO_LIGHT);
				builder.setTitle("��ȡ��֤��");
				builder.setMessage("" + randCode);
				builder.setPositiveButton("����", diagListener);
				AlertDialog dialog = builder.create();
				dialog.show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.identify, menu);
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

	class ButtonListemer implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.cancel) {
				// ���ص�¼
				IdentifyActivity.this.finish();
			} else if (id == R.id.next) {
				// �ж��Ƿ���д
				String pnum = phone.getText().toString();
				String codenum = code.getText().toString();
				if (pnum.length() == 0 || codenum.length() == 0) {
					Toast.makeText(IdentifyActivity.this, "���벻��Ϊ�գ�",
							Toast.LENGTH_SHORT).show();
					return;
				}
				String rs = phone.getText().toString();
				// �ж��ֻ����Ƿ��ǰ��ֻ���
				Intent intent = getIntent();// ����
				String name = intent.getStringExtra(DengluActivity.UNAME);
				MySQLiteOpenHelper helper = new MySQLiteOpenHelper(
						IdentifyActivity.this, "db");
				SQLiteDatabase database = helper.getReadableDatabase();
				Cursor cursor = database.rawQuery(
						"select phone from user where name= ?",
						new String[] { name });
				cursor.moveToNext();
				String tele = cursor.getString(cursor.getColumnIndex("phone"));
				if(tele==null){
					DiagListener2 diagListener = new DiagListener2();
					AlertDialog.Builder builder = new AlertDialog.Builder(
							IdentifyActivity.this, AlertDialog.THEME_HOLO_LIGHT);
					builder.setTitle("��֤���");
					builder.setMessage("���û�δ���ֻ��ţ�");
					builder.setPositiveButton("ȷ��", diagListener);
					AlertDialog dialog = builder.create();
					dialog.show();
					return;
				}
				if (!rs.equals(tele)) {
					DiagListener2 diagListener = new DiagListener2();
					AlertDialog.Builder builder = new AlertDialog.Builder(
							IdentifyActivity.this, AlertDialog.THEME_HOLO_LIGHT);
					builder.setTitle("��֤���");
					builder.setMessage("�ֻ��Ų���ȷ��");
					builder.setPositiveButton("ȷ��", diagListener);
					AlertDialog dialog = builder.create();
					dialog.show();
					return;
				}

				if (!codenum.equals(String.valueOf(randCode))) {
					DiagListener2 diagListener = new DiagListener2();
					AlertDialog.Builder builder = new AlertDialog.Builder(
							IdentifyActivity.this, AlertDialog.THEME_HOLO_LIGHT);
					builder.setTitle("��֤���");
					builder.setMessage("��֤�벻��ȷ��");
					builder.setPositiveButton("ȷ��", diagListener);
					AlertDialog dialog = builder.create();
					dialog.show();
					return;
				}
				Intent intent2 = new Intent(IdentifyActivity.this,
						NextActivity.class);
				intent2.putExtra(DengluActivity.UNAME, name);
				intent2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent2);
			}
		}

	}

	class DiagListener implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:
				// ������ƣ��Զ�ճ��
				code.setText("" + randCode);
				break;
			default:
				break;
			}
		}

	}

	class DiagListener2 implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:
				break;
			default:
				break;
			}
		}

	}

}
