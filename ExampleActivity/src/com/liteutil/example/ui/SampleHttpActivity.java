package com.liteutil.example.ui;

import java.util.ArrayList;
import java.util.List;

import com.liteutil.android.http.LiteHttp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @ClassName: SampleHttpActivity
 * @Description: http����
 * @author: KCJ
 * @date: 2015-11-28
 */
public class SampleHttpActivity extends Activity implements OnItemClickListener{

	private ListView listView;
	private LiteHttp liteHttp;
	private Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		listView = new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		setContentView(listView);
		listView.setOnItemClickListener(this);
		
//		liteHttp = LiteHttp.newApacheHttpClient(null);
	}
	
	private List<String> getData() {
		List<String> data = new ArrayList<String>();
		data.add("0. ��������");
		data.add("1. �첽����");
		data.add("2. ͬ������");
		data.add("3. ��ͬ������");
		data.add("4. �׳��쳣����");
		data.add("9. �ļ��ϴ�");
		data.add("10. �ļ�/ͼƬ����");
		data.add("11. ��ֹһЩ�������");
		data.add("20. �໺�����");
		data.add("21. �ص�����");
		return data;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}

}
