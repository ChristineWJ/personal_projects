package com.secondbike.SecondBike;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GuacheActivity extends Activity {

	private Button cancel, submit;
	private ButtonListener buttonListner;
	private EditText name, price, rent, color, QQ, contact, address, moto;
	private ImageView loadimage;
	private ImageListener imageListener;
	private final int REQUSET = 0;
	public final static  String EXPRESSION="expression";
	private static final int PHOTO_REQUEST_CAREMA = 1;// ����
	private static final int PHOTO_REQUEST_GALLERY = 2;// �������ѡ��
	private static final int PHOTO_REQUEST_CUT = 3;// ���
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
	// private PopupMenu popupMenu;
	// private PopupMenuListener popupmenuListener;
	// private Button popbtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guache);
		SysApplication.getInstance().addActivity(this);
		Intent i = getIntent();
		init();// ��ʼ��
	}

	public void init() {
		cancel = (Button) findViewById(R.id.cancel);
		buttonListner = new ButtonListener();
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(buttonListner);
		cancel.setOnClickListener(buttonListner);
		//
		name = (EditText) findViewById(R.id.name);
		price = (EditText) findViewById(R.id.price);
		rent = (EditText) findViewById(R.id.rent);
		color = (EditText) findViewById(R.id.color);
		QQ = (EditText) findViewById(R.id.QQ);
		contact = (EditText) findViewById(R.id.contact);
		address = (EditText) findViewById(R.id.address);
		moto = (EditText) findViewById(R.id.moto);
		loadimage = (ImageView) findViewById(R.id.loadimage);
		imageListener = new ImageListener();
		loadimage.setOnClickListener(imageListener);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_MENU){
			return true;
		}else if(keyCode==KeyEvent.KEYCODE_BACK){
			return true;
		}
		return false;
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.guache, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.photo) {
			// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
			// �������
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			// �жϴ洢���Ƿ�����ã����ý��д洢
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(),
						PHOTO_FILE_NAME);
				// ���ļ��д���uri
				Uri uri = Uri.fromFile(tempFile);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			}
			// ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_CAREMA
			startActivityForResult(intent, PHOTO_REQUEST_CAREMA);

			return true;
		} else if (id == R.id.local) {
			// ����ϵͳͼ�⣬ѡ��һ��ͼƬ
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			// ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_GALLERY
			startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

			return true;
		} else if (id == R.id.quxiao) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
     * ����ͼƬ
     */
    private void crop(Uri uri) {
        // �ü�ͼƬ��ͼ
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // �ü���ı�����1��1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // �ü������ͼƬ�ĳߴ��С
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// ͼƬ��ʽ
        intent.putExtra("noFaceDetection", true);// ȡ������ʶ��
        intent.putExtra("return-data", true);
        // ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /*
     * �ж�sdcard�Ƿ񱻹���
     */
    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // ����᷵�ص�����
            if (data != null) {
                // �õ�ͼƬ��ȫ·��
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
        	if(resultCode==RESULT_OK)
            // ��������ص�����
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(GuacheActivity.this, "δ�ҵ��洢�����޷��洢��Ƭ��", 0).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // �Ӽ���ͼƬ���ص�����
            if (data != null) {
//                Bitmap bitmap = data.getParcelableExtra("data");
//                this.iv_image.setImageBitmap(bitmap);
            	Bitmap bmPhoto = (Bitmap) data.getExtras().get("data");
				ImageView imageView = new ImageView(this);
				imageView.setImageBitmap(bmPhoto);
				Display display = getWindowManager().getDefaultDisplay();
				int width = display.getWidth();
				LinearLayout.LayoutParams PARA = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				imageView.setLayoutParams(PARA);
				imageView.setAdjustViewBounds(true);
				imageView.setMaxWidth(width * 3);
				imageView.setMaxHeight(width * 2);
				GridLayout layout = (GridLayout) findViewById(R.id.gridlayout);
				layout.addView(imageView);
            }
            try {
                // ����ʱ�ļ�ɾ��
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

	// ��ť������
	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.cancel) {
				GuacheActivity.this.finish();
			} else if (id == R.id.submit) {
				String nameString = name.getText().toString();
				String priceString = price.getText().toString();
				String contactString = contact.getText().toString();
				if (nameString.length() == 0 || priceString.length() == 0
						|| contactString.length() == 0) {
					DiagListener diagListener = new DiagListener();
					AlertDialog.Builder builder = new AlertDialog.Builder(
							GuacheActivity.this, AlertDialog.THEME_HOLO_LIGHT);
					builder.setTitle("��Ҫ�ҳ�");
					builder.setMessage("�������ȱʡ��");
					builder.setNegativeButton("ȷ��", diagListener);
					AlertDialog dialog = builder.create();
					dialog.show();
					return;
				}

				// �ύ�ɹ�
				DiagListener diagListener = new DiagListener();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						GuacheActivity.this, AlertDialog.THEME_HOLO_LIGHT);
				builder.setTitle("�ύ���");
				builder.setMessage("�ύ�ɹ���");
				builder.setPositiveButton("ȷ��", diagListener);
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		}
	}
	
	class ImageListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// �����˵�
			GuacheActivity.this.openOptionsMenu();
		}

	}

	class DiagListener implements
			android.content.DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == AlertDialog.BUTTON_POSITIVE) {
				GuacheActivity.this.finish();
			}
		}
	}
}
