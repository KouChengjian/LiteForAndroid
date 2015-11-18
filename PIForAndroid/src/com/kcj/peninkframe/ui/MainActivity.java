package com.kcj.peninkframe.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @ClassName: MainActivity
 * @Description:
 * @author: KCJ
 * @date:
 */
public class MainActivity extends Activity implements OnItemClickListener{

	private ListView listView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		listView = new ListView(this);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		setContentView(listView);
		listView.setOnItemClickListener(this);
	}

	private List<String> getData(){
        List<String> data = new ArrayList<String>();
        data.add("orm");
        data.add("http");
        data.add("async");
        return data;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0:
			startAnimActivity(SampleOrmActivity.class);
			break;
		case 1:
			startAnimActivity(SampleHttpActivity.class);
			break;
		case 2:
			startAnimActivity(SampleAsyncActivity.class);
			break;
		case 3:
			break;
		default:
			break;
		}
	}
	
	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(this, cla));
	}
}
