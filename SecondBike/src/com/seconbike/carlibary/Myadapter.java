package com.seconbike.carlibary;

import java.util.ArrayList;

import com.secondbike.SecondBike.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Myadapter extends BaseAdapter {

	private Context context;// �н�������
	private ArrayList<MyData> datas;

	public Myadapter(Context context, ArrayList<MyData> datas) {
		super();
		this.context = context;
		this.datas=datas;
	}
	
	/**
	 * ��ǰ���ݳ���
	 */
	@Override
	public int getCount() {
		if(datas==null){
			return 0;
		}else{
			return datas.size();
		}
	}

	/**
	 * ÿһ�����ݵ�item
	 */
	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	/**
	 * ÿһ�����ݵ�id
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	public boolean remove(int position){
		if(datas.isEmpty()){
			return false;
		}
		if(datas.size()==1){
			datas.remove(position);
			return false;
		}
		if(position>=0&&position<datas.size()){
			datas.remove(position);
			return true;
		}else {
			return false;
		}
	}
	/**
	 * ��Ҫ���ص���ͼ
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.car,
					null);// ����һ����ͼ�õ�һ��ʵ��convertView
		}
		TextView tv = (TextView) convertView.findViewById(R.id.textview1);// ������ͼ����Ŀؼ�-����
		tv.setText(datas.get(position).getName());

		TextView tv1 = (TextView) convertView.findViewById(R.id.price);// ������ͼ����Ŀؼ�-�۸�
		tv1.setText(String.valueOf(datas.get(position).getPrice()));

		TextView tv2 = (TextView) convertView.findViewById(R.id.borrow_label);// ������ͼ����Ŀؼ�-����
		if (datas.get(position).isRentable()) {
			tv2.setText("����");
		} else {
			tv2.setText("������");
		}

		TextView tv3 = (TextView) convertView.findViewById(R.id.borrow_price);// ������ͼ����Ŀؼ�-���
		tv3.setText(String.valueOf(String.valueOf(datas.get(position).getRentPrice())));

		ImageView imageView = (ImageView) convertView.findViewById(R.id.image);// ����ͼƬ
		imageView.setImageResource(datas.get(position).getImageid());

		return convertView;
	}
	
	
}
