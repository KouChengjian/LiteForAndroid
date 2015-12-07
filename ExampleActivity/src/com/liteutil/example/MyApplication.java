package com.liteutil.example;



import com.liteutil.http.*;
import com.liteutil.orm.LiteOrm;
import com.liteutil.orm.db.DataBase;

import android.app.Application;




/**
 * Created by wyouflf on 15/10/28.
 */
public class MyApplication extends Application {

	private DataBase db;
	private static MyApplication myApplication = null;
	
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        new LiteHttp(this);
        db = LiteOrm.newSingleInstance(this, "liteorm.db");
    }
    
    public static MyApplication getInstance() {
		return myApplication;
	}
    
    public DataBase getDBInstance(){
    	return db;
    }
}
