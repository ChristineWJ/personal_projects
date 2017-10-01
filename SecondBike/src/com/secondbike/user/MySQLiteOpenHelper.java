package com.secondbike.user;

import android.R.integer;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Mysqlitehelper��Ϊ�������ݿ�������࣬�ṩ��������
//1.�õ�sqlitehelper����
//2.�ṩ����.

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	private static final int VERSION = 1;// Ĭ�ϰ汾,�Զ���

	// ���캯�������
	public MySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// (�����ģ�actiity���󣩣������֣�null����ǰ���ݿ�汾>0)
	}

	// �Զ���
	public MySQLiteOpenHelper(Context context, String name) {
		this(context, name, null, VERSION);
	}

	// �Զ���
	public MySQLiteOpenHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	// ��һ�δ������ݿ�ʱ����,��һ�εõ�MySQLiteOpenHelper����ʱ����
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e("helper", "create()");
		db.execSQL("create table user(id integer primary key autoincrement ,nicheng varchar(30),name varchar(30),passwd varchar(30),QQ varchar(20),phone varchar(30),address varchar(30),moto varchar(30))");
		Log.e("helper", "����user ok");
	}

	// �����ݿ�
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);

	}

	// �������ݿ�
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("helper", "update()");
		// update table_name set xxx=xxx where xxx
	}

}
