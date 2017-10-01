package com.secondbike.fogetpwd;

import com.secondbike.SecondBike.DengluActivity;
import com.secondbike.SecondBike.R;
import com.secondbike.SecondBike.SysApplication;
import com.secondbike.SecondBike.ZhuceActivity;
import com.secondbike.SecondBike.R.id;
import com.secondbike.SecondBike.R.layout;
import com.secondbike.SecondBike.R.menu;
import com.secondbike.user.MySQLiteOpenHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChPWDActivity extends Activity {

	private Button cancel, certain;
	private ButtonListener buttonListner;
	private EditText pwdEdittext1, pwdEdittext2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ch_pwd);
		SysApplication.getInstance().addActivity(this);
		init();
	}

	public void init() {
		pwdEdittext1 = (EditText) findViewById(R.id.pwdEdittext1);
		pwdEdittext2 = (EditText) findViewById(R.id.pwdEdittext2);
		cancel = (Button) findViewById(R.id.cancel);
		certain = (Button) findViewById(R.id.login_in);
		buttonListner = new ButtonListener();
		cancel.setOnClickListener(buttonListner);
		certain.setOnClickListener(buttonListner);
	}

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.cancel) {
				ChPWDActivity.this.finish();
			} else if (v.getId() == R.id.login_in) {
				// �ж��Ƿ�����
				String oldpwd = pwdEdittext1.getText().toString();
				String newpwd = pwdEdittext2.getText().toString();
				if (oldpwd.length() == 0 || newpwd.length() == 0) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ChPWDActivity.this, AlertDialog.THEME_HOLO_LIGHT);
					builder.setTitle("�޸�����");
					builder.setMessage("���벻��Ϊ�գ�");
					builder.setPositiveButton("ȷ��", new DialogListener());
					AlertDialog dialog = builder.create();
					dialog.show();
					return;
				}
				// �ж����볤�ȺϷ�
				if (oldpwd.length() < 6 || oldpwd.length() > 14) {
					DialogListener diagListener = new DialogListener();
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ChPWDActivity.this, AlertDialog.THEME_HOLO_LIGHT);
					builder.setTitle("�޸�����");
					builder.setMessage("����λ��Ϊ6~14λ��");
					builder.setPositiveButton("ȷ��", diagListener);
					AlertDialog dialog = builder.create();
					dialog.show();
					return;
				}
				// ����һ��
				if (!oldpwd.equals(newpwd)) {
					DialogListener diagListener = new DialogListener();
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ChPWDActivity.this, AlertDialog.THEME_HOLO_LIGHT);
					builder.setTitle("�޸�����");
					builder.setMessage("�������벻һ�£�");
					builder.setPositiveButton("ȷ��", diagListener);
					AlertDialog dialog = builder.create();
					dialog.show();
					return;
				}
				// �޸����룬�޸����ݿ�
				Intent intent = getIntent();
				String uname = intent.getStringExtra(DengluActivity.UNAME);
				MySQLiteOpenHelper helper = new MySQLiteOpenHelper(
						ChPWDActivity.this, "db");
				SQLiteDatabase database = helper.getWritableDatabase();
				ContentValues values = new ContentValues();
				values.put("passwd", newpwd);
				database.update("user", values, "name= ?",
						new String[] { uname });
				// �����ǰshared�����˻�һ�����޸�shared������
				SharedPreferences sharedPreferences = getSharedPreferences(
						DengluActivity.PREFERENCE_NAME, DengluActivity.MODE);
				String curname = sharedPreferences.getString(
						DengluActivity.UNAME, "defValue");
				if (curname.equals(uname)) {
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString(DengluActivity.PWD, newpwd);
					editor.commit();
				}
				//
				DialogListener diagListener = new DialogListener();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ChPWDActivity.this, AlertDialog.THEME_HOLO_LIGHT);
				builder.setTitle("�ύ���");
				builder.setMessage("�ύ�ɹ������ص�¼����");
				builder.setNegativeButton("ȷ��", new DialogListener());
				AlertDialog dialog = builder.create();
				dialog.show();
			}

		}

	}

	class DialogListener implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_NEGATIVE:
				// ���ص�¼
				Intent intent = new Intent(ChPWDActivity.this,
						DengluActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
			}

		}

	}

}
