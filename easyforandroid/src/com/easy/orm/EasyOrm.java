package com.easy.orm;

import android.database.sqlite.SQLiteClosable;


/**
 * 数据SQLite操作实现
 * 可查阅 <a href="http://www.sqlite.org/lang.html">SQLite操作指南</a>
 */
public class EasyOrm extends SQLiteClosable  { //implements DataBase

	@Override
	protected void onAllReferencesReleased() {
		
	}

}
