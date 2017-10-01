package com.secondbike.SecondBike;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePWDActivity extends Activity {

	private Button cancel, certain;
	private ButtonListener buttonListner;
	private EditText accountEdittxt, pwdEdittext1, pwdEdittext2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_pwd);
		SysApplication.getInstance().addActivity(this);
		Intent intent = getIntent();
		init();
	}

	public void init() {
		cancel = (Button) findViewById(R.id.cancel);
		certain = (Button) findViewById(R.id.login_in);
		buttonListner = new ButtonListener();
		cancel.setOnClickListener(buttonListner);
		certain.setOnClickListener(buttonListner);
		accountEdittxt = (EditText) findViewById(R.id.accountEdittext);
		pwdEdittext1 = (EditText) findViewById(R.id.pwdEdittext1);
		pwdEdittext2 = (EditText) findViewById(R.id.pwdEdittext2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_pwd, menu);
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

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.cancel) {
				ChangePWDActivity.this.finish();
			} else if (id == R.id.login_in) {
				// �ж��Ƿ���д
				String account = accountEdittxt.getText().toString();
				String newpwd = pwdEdittext1.getText().toString();
				String againpwd = pwdEdittext2.getText().toString();
				if (account.length() == 0 || newpwd.length() == 0
						|| againpwd.length() == 0) {
					Toast.makeText(ChangePWDActivity.this, "���벻��Ϊ�գ�",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// �ж���������һ����
				if (!newpwd.equals(againpwd)) {
					DiagListener diagListener = new DiagListener();
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ChangePWDActivity.this,
							AlertDialog.THEME_HOLO_LIGHT);
					builder.setTitle("�޸�����");
					builder.setMessage("�����������벻һ�£�");
					builder.setPositiveButton("ȷ��", diagListener);
					AlertDialog dialog = builder.create();
					dialog.show();

					return;
				}
				// ����һ���ж����ݿ�����û������û�

				// �޸�����ɹ�
				DiagListener2 diagListener = new DiagListener2();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ChangePWDActivity.this, AlertDialog.THEME_HOLO_LIGHT);
				builder.setTitle("�޸�����");
				builder.setMessage("�޸�����ɹ����뷵�����µ�¼!");
				builder.setPositiveButton("ȷ��", diagListener);
				AlertDialog dialog = builder.create();
				dialog.show();
			}

		}

	}

	class DiagListener implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
		}
	}

	class DiagListener2 implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:
				Intent intent = null;
				intent = new Intent(ChangePWDActivity.this,
						DengluActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;

			default:
				break;
			}
		}
	}

}
