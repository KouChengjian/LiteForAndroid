package com.easyorm.db;

import android.content.Context;

import com.easyorm.EasyConfig;
import com.easyorm.db.assit.Checker;
import com.easyorm.db.assit.SQLiteHelper.OnUpdateListener;

/**
 * 数据操作配置
 */
public class DataBaseConfig {

    public Context context;
    public String dbName = EasyConfig.DEFAULT_DB_NAME;
    public int dbVersion = EasyConfig.DEFAULT_DB_VERSION;
    public OnUpdateListener onUpdateListener;
    
    
    public DataBaseConfig(Context context) {
        this(context , EasyConfig.DEFAULT_DB_NAME);
    }

    public DataBaseConfig(Context context, String dbName) {
        this(context , dbName, EasyConfig.DEFAULT_DB_VERSION, null);
    }
    
    public DataBaseConfig(Context context, String dbName, int dbVersion, OnUpdateListener onUpdateListener) {
        this.context = context.getApplicationContext();
        if (!Checker.isEmpty(dbName))
            this.dbName = dbName;
        if (dbVersion > 1)
            this.dbVersion = dbVersion;
        this.onUpdateListener = onUpdateListener;
    }
    
    @Override
    public String toString() {
        return "DataBaseConfig [mContext=" + context + ", mDbName=" + dbName + ", mDbVersion="
               + dbVersion + ", mOnUpdateListener=" + onUpdateListener + "]";
    }
}
