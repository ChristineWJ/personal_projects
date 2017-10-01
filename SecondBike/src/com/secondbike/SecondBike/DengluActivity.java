package com.secondbike.SecondBike;

import com.secondbike.SecondBike.ZhuceActivity.DiagListener3;
import com.secondbike.fogetpwd.FogetPWDActivity;
import com.secondbike.user.MySQLiteOpenHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DengluActivity extends Activity {

	private EditText accountEditText, pwdEditText;
	private TextView fogetpwd, zhuce;
	private CheckBox savepwd, autologin_in;
	private Button login_in, cancel;
	private ButtonListener buttonListener;
	private TextViewListener fogetpwdListener;
	public static int MODE = Context.MODE_WORLD_READABLE
			+ Context.MODE_WORLD_WRITEABLE;
	public static final String PREFERENCE_NAME = "setting";
	public final static String UNAME = "uname";
	public final static String PWD = "pwd";
	public final static String SAVE = "save";
	public final static String AUTO_LOGIN = "auto_login";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.denglu);
		SysApplication.getInstance().addActivity(this);
		Intent intent = getIntent();// ����
		init();// ��ʼ�����¼�����
		initinfo();

	}

	public void init() {
		buttonListener = new ButtonListener();
		login_in = (Button) findViewById(R.id.login_in);
		login_in.setOnClickListener(buttonListener);
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(buttonListener);
		zhuce = (TextView) findViewById(R.id.zhuce);
		accountEditText = (EditText) findViewById(R.id.accountEdittext);
		pwdEditText = (EditText) findViewById(R.id.pwdEdittext);
		fogetpwd = (TextView) findViewById(R.id.fogetpwd);
		fogetpwdListener = new TextViewListener();
		fogetpwd.setOnClickListener(fogetpwdListener);
		zhuce.setOnClickListener(fogetpwdListener);
		savepwd = (CheckBox) findViewById(R.id.savepwd);
		autologin_in = (CheckBox) findViewById(R.id.autologin_in);
	}

	public void initinfo() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				PREFERENCE_NAME, MODE);
		accountEditText.setText(sharedPreferences.getString(UNAME, ""));
		if (sharedPreferences.getBoolean(SAVE, false)) {
			savepwd.setChecked((true));
			pwdEditText.setText(sharedPreferences.getString(PWD, "defValue"));
		}
		if (sharedPreferences.getBoolean(AUTO_LOGIN, false)) {
			autologin_in.setChecked(true);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			SysApplication.getInstance().exit();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.denglu, menu);
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

	public class TextViewListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.fogetpwd) {
				Intent intent = new Intent(DengluActivity.this,
						FogetPWDActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			} else if (id == R.id.zhuce) {
				Intent intent = new Intent(DengluActivity.this,
						ZhuceActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
		}

	}

	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.login_in) {
				String accout = accountEditText.getText().toString();
				String pwd = pwdEditText.getText().toString();
				if (accout.length() == 0 || pwd.length() == 0) {
					Toast toast = Toast.makeText(DengluActivity.this, "",
							Toast.LENGTH_SHORT);
					toast.setText("�˺Ż����벻��Ϊ�գ�");
					toast.show();
					return;
				}
				SharedPreferences sharedPreferences = getSharedPreferences(
						PREFERENCE_NAME, MODE);
				SharedPreferences.Editor editor = sharedPreferences.edit();
				String u1 = sharedPreferences.getString(UNAME, "defaultuname");
				String p1 = sharedPreferences.getString(PWD, "defaultpwd");
				Log.e("��һ��name=" + u1, "pwd=" + p1);
				Log.e("��ǰname=" + accout, "pwd=" + pwd);
				if (!accout.equals(u1)) {
					// �ж��˺������Ƿ�Ϸ�
					boolean findable = false;
					MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(
							DengluActivity.this, "db");
					SQLiteDatabase database = mySQLiteOpenHelper
							.getReadableDatabase();
					Cursor cursor = database.rawQuery("select name from user",
							null);
					while (cursor.moveToNext()) {
						String name = cursor.getString(cursor
								.getColumnIndex("name"));
						Log.e("name", name);
						if (accout.equals(name)) {
							findable = true;
							break;
						}
					}
					if (!findable) {
						Toast.makeText(DengluActivity.this, "�˺Ų����ڣ�",
								Toast.LENGTH_SHORT).show();
						return;
					}
					// �Ϸ��˺�,��ȡ����
					cursor = database.rawQuery(
							"select passwd from user where name = ? ",
							new String[] { accout });
					cursor.moveToNext();
					String passwd = cursor.getString(cursor
							.getColumnIndex("passwd"));

					// �ж�����
					if (!pwd.equals(passwd)) {
						Toast.makeText(DengluActivity.this, "���벻��ȷ��",
								Toast.LENGTH_SHORT).show();
						return;
					}
					// ����ϴ�
					sharedPreferences.edit().clear().commit();
					Log.e("�����û���һ�������shared", "");
					// shared���浱ǰ��¼�û�
					editor.putString(DengluActivity.UNAME, accout);
					editor.putString(DengluActivity.PWD, pwd);
					Log.e("�����µ�shared", "");
				} else if (!(p1.equals(pwd))) {
					Toast.makeText(DengluActivity.this, "���벻��ȷ��",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// ������Ϸ��ж��Ƿ��ס������Զ���¼
				if (autologin_in.isChecked() && !savepwd.isChecked()) {
					Toast.makeText(DengluActivity.this, "�빴ѡ��ס���룡",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (savepwd.isChecked()) {
					// ����״̬
					editor.putBoolean(SAVE, true);
				} else {
					editor.putBoolean(SAVE, false);
				}
				if (autologin_in.isChecked()) {
					editor.putBoolean(AUTO_LOGIN, true);
				} else {
					editor.putBoolean(AUTO_LOGIN, false);
				}
				Log.e("save=" + savepwd.isChecked(),
						"auto=" + autologin_in.isChecked());
				editor.commit();
				Intent intent = new Intent(DengluActivity.this,
						ZhuyeActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				Bundle bundle = new Bundle();
				bundle.putString("uname", accountEditText.getText().toString());
				bundle.putString("pwd", pwdEditText.getText().toString());
				intent.putExtras(bundle);
				startActivity(intent);
			} else if (id == R.id.cancel) {
				// �˳�����
				SysApplication.getInstance().exit();
			}
		}
	}

}
