package com.secondbike.fenlei;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.secondbike.SecondBike.R;

public class Myadapter extends BaseAdapter {

	private Context context;// �н�������
	private MyData data[];

	public Myadapter(Context context, MyData[] data) {
		super();
		this.context = context;
		this.data = data;
	}

	/**
	 * ��ǰ���ݳ���
	 */
	@Override
	public int getCount() {

		return data.length;
	}

	/**
	 * ÿһ�����ݵ�item
	 */
	@Override
	public Object getItem(int position) {
		return data[position];
	}

	/**
	 * ÿһ�����ݵ�id
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * ��Ҫ���ص���ͼ
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.fenlei_item, null);// ����һ����ͼ
		}
		// TextView tv = (TextView) convertView.findViewById(R.id.mine_tv);//
		// ������ͼ����Ŀؼ�-����
		// tv.setText(data[position].getName());

		ImageView image = (ImageView) convertView.findViewById(R.id.mine_tv);

		Drawable drawable = parent.getResources().getDrawable(
				data[position].getImageId());
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		image.setImageDrawable(drawable);
		// tv.setCompoundDrawables(drawable, null, null, null); // ����ͼ��

		return convertView;
	}
}
