package com.secondbike.fogetpwd;

import com.secondbike.SecondBike.DengluActivity;
import com.secondbike.SecondBike.IdentifyActivity;
import com.secondbike.SecondBike.R;
import com.secondbike.SecondBike.SysApplication;
import com.secondbike.SecondBike.ZhuceActivity;
import com.secondbike.user.MySQLiteOpenHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FogetPWDActivity extends Activity {

	private EditText accountEditText;
	private Button next, cancel;
	private ButtonListener buttonListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foget_pwd);
		SysApplication.getInstance().addActivity(this);
		Intent intent = getIntent();// ����
		init();// ��ʼ��
	}

	public void init() {
		accountEditText = (EditText) findViewById(R.id.accountEdittext);
		next = (Button) findViewById(R.id.next);
		cancel = (Button) findViewById(R.id.cancel);
		buttonListener = new ButtonListener();
		next.setOnClickListener(buttonListener);
		cancel.setOnClickListener(buttonListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.foget_pwd, menu);
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

	// ��ť������
	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.next) {
				// �ж��Ƿ���д
				String uname = accountEditText.getText().toString();
				if (uname.length() == 0) {
					Toast.makeText(FogetPWDActivity.this, "���벻��Ϊ�գ�",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// �û��Ƿ����
				boolean exisitable = false;
				MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(
						FogetPWDActivity.this, "db");
				SQLiteDatabase database = mySQLiteOpenHelper
						.getReadableDatabase();
				Cursor cursor = database
						.rawQuery("select name from user", null);
				while (cursor.moveToNext()) {
					Log.e("�����ж�", "");
					String name = cursor.getString(cursor
							.getColumnIndex("name"));
					if (uname.equals(name)) {
						exisitable = true;
					}
				}
				if (!exisitable) {
					DiagListener diagListener = new DiagListener();
					AlertDialog.Builder builder = new AlertDialog.Builder(
							FogetPWDActivity.this, AlertDialog.THEME_HOLO_LIGHT);
					builder.setTitle("��֤");
					builder.setMessage("�û��������ڣ�");
					builder.setPositiveButton("ȷ��", diagListener);
					AlertDialog dialog = builder.create();
					dialog.show();
					return;
				}
				// ����
				Intent intent = new Intent(FogetPWDActivity.this,
						IdentifyActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				intent.putExtra(DengluActivity.UNAME, uname);
				startActivity(intent);
			} else if (id == R.id.cancel) {
				FogetPWDActivity.this.finish();
			}

		}
	}

	class DiagListener implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
		}
	}
}
