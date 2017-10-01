package com.secondbike.SecondBike;

import java.util.ArrayList;

import com.seconbike.carlibary.MyData;
import com.seconbike.carlibary.Myadapter;
import com.secondbike.SecondBike.ChangePWDActivity.DiagListener;
import com.secondbike.SecondBike.MyFaovrActivity.ButtonListener;
import com.secondbike.SecondBike.MyFaovrActivity.ListViewListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class BroHistoryActivity extends Activity {

	private ListView listView;
	private Myadapter myadapter;
	private MyData[] datas;
	private ButtonListener btnListener;
	private ListViewListener lvListener;

	private Button cancel, clear;
	private ButtonListener buttonListner;
	private ArrayList<MyData> dataslist;
	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brohistory);
		SysApplication.getInstance().addActivity(this);
		Intent intent = getIntent();
		init();
	}

	public void init() {
		cancel = (Button) findViewById(R.id.cancel);
		buttonListner = new ButtonListener();
		cancel.setOnClickListener(buttonListner);
		clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(buttonListner);
		text = (TextView) findViewById(R.id.text);
		//
		datas = new MyData[5];
		datas[0] = new MyData("�ݰ���", R.drawable.a, 100, true, 10,"��ɫ","express");
		datas[1] = new MyData("������", R.drawable.b, 100, false, 0,"��ɫ","express");
		datas[2] = new MyData("���", R.drawable.c, 100, true, 12,"��ɫ","express");
		datas[3] = new MyData("����", R.drawable.d, 100, true, 12,"��ɫ","express");
		datas[4] = new MyData("�ɸ�", R.drawable.e, 100, true, 12,"��ɫ","express");
		//
		dataslist = new ArrayList<MyData>();
		for (int i = 0; i < datas.length; i++) {
			dataslist.add(datas[i]);
		}
		listView = (ListView) findViewById(R.id.listview);
		myadapter = new Myadapter(this, dataslist);
		listView.setAdapter(myadapter);
		btnListener = new ButtonListener();
		cancel.setOnClickListener(btnListener);
		//
		lvListener = new ListViewListener();
		listView.setOnItemClickListener(lvListener);
		//
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				menu.add(0, 1, 1, "�����¼");
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo acmenuinfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int id = (int) myadapter.getItemId(acmenuinfo.position);
		// ȡ��id��(���idΪ���ݿ��еĶ�Ӧid)
		// ��������adapter
		dataslist.remove(id);
		if (dataslist.size() > 0) {
			myadapter = new Myadapter(this, dataslist);
			listView.setAdapter(myadapter);
		} else {
			listView.setAdapter(null);
			listView.setEmptyView(text);
		}
		return true;
	}

	// ��ť������
	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.cancel) {
				BroHistoryActivity.this.finish();
			} else if (id == R.id.clear) {
				// ����ǰҳ�沼���������
				// �ж��Ƿ��Ѿ�����
				if (listView.getItemAtPosition(0) == null) {
					DiagListener diagListener1 = new DiagListener();
					AlertDialog.Builder builder = new AlertDialog.Builder(
							BroHistoryActivity.this,
							AlertDialog.THEME_HOLO_LIGHT);
					builder.setTitle("�����ʷ");
					builder.setMessage("������ʷ��¼��");
					builder.setNegativeButton("ȷ��", diagListener1);
					AlertDialog dialog = builder.create();
					dialog.show();
					return;
				}
				DiagListener diagListener = new DiagListener();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						BroHistoryActivity.this, AlertDialog.THEME_HOLO_LIGHT);
				builder.setTitle("�����ʷ");
				builder.setMessage("ȷ�������ʷ��¼��");
				builder.setPositiveButton("ȷ��", diagListener);
				builder.setNegativeButton("ȡ��", diagListener);
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		}
	}

	class DiagListener implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == AlertDialog.BUTTON_POSITIVE) {
				listView.setAdapter(null);
				listView.setEmptyView(text);
			} else if (which == AlertDialog.BUTTON_NEGATIVE) {

			}
		}
	}

	class ListViewListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// ���֮����ת�����ݶ�̬��
			Intent intent = new Intent(BroHistoryActivity.this,
					CarDetailActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
		}

	}
}
