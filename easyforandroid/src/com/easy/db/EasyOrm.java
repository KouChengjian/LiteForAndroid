package com.easy.db;


import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;

import com.easy.EasyConfig;
import com.easy.common.util.EasyLog;
import com.easy.db.assit.SQLiteHelper;
import com.easy.db.impl.SingleSQLiteImpl;

/**
 * 数据SQLite操作实现
 * 可查阅 <a href="http://www.sqlite.org/lang.html">SQLite操作指南</a>
 */
public abstract class EasyOrm extends SQLiteClosable implements DataBase {

	public static final String TAG = EasyOrm.class.getSimpleName();
	protected SQLiteHelper mHelper;
    protected DataBaseConfig mConfig;
    protected TableManager mTableManager;
    protected EasyOrm otherDatabase;
    
    protected EasyOrm(EasyOrm dataBase) {
        this.mHelper = dataBase.mHelper;
        this.mConfig = dataBase.mConfig;
        this.mTableManager = dataBase.mTableManager;
        this.otherDatabase = dataBase;
    }

    protected EasyOrm(DataBaseConfig config) {
        config.context = config.context.getApplicationContext();
        if (config.dbName == null) {
            config.dbName = EasyConfig.DEFAULT_DB_NAME;
        }
        if (config.dbVersion <= 0) {
            config.dbVersion = EasyConfig.DEFAULT_DB_VERSION;
        }
        mConfig = config;
        openOrCreateDatabase();
    }
    
    @Override
    public SQLiteDatabase openOrCreateDatabase() {
        initDatabasePath(mConfig.dbName);
        if (mHelper != null) {
            justRelease();
        }
        mHelper = new SQLiteHelper(mConfig.context.getApplicationContext(),
                mConfig.dbName, null, mConfig.dbVersion, mConfig.onUpdateListener);
        mTableManager = new TableManager(mConfig.dbName, mHelper.getReadableDatabase());
        return mHelper.getWritableDatabase();
    }

    private void initDatabasePath(String path) {
        EasyLog.i(TAG, "create  database path: " + path);
        path = mConfig.context.getDatabasePath(mConfig.dbName).getPath();
        EasyLog.i(TAG, "context database path: " + path);
        File dbp = new File(path).getParentFile();
        if (dbp != null && !dbp.exists()) {
            boolean mks = dbp.mkdirs();
            EasyLog.i(TAG, "create database, parent file mkdirs: " + mks + "  path:" + dbp.getAbsolutePath());
        }
    }
    
    
    /**
     * get and new a single model operator based on SQLite
     *
     * @param context app context
     * @param dbName  database name
     * @return {@link SingleSQLiteImpl}
     */
    public static EasyOrm newSingleInstance(Context context, String dbName) {
        return newSingleInstance(new DataBaseConfig(context, dbName));
    }
    
    /**
     * get and new a single model operator based on SQLite
     *
     * @param config lite-orm config
     * @return {@link CascadeSQLiteImpl}
     */
    public synchronized static EasyOrm newSingleInstance(DataBaseConfig config) {
        return SingleSQLiteImpl.newInstance(config);
    }
    
    /**
     * get and new a cascade model operator based on SQLite
     *
     * @param context app context
     * @param dbName  database name
     * @return {@link SingleSQLiteImpl}
     */
//    public static LiteOrm newCascadeInstance(Context context, String dbName) {
//        return newCascadeInstance(new DataBaseConfig(context, dbName));
//    }

    /**
     * get and new a cascade model operator based on SQLite
     *
     * @param config lite-orm config
     * @return {@link CascadeSQLiteImpl}
     */
//    public synchronized static LiteOrm newCascadeInstance(DataBaseConfig config) {
//        return CascadeSQLiteImpl.newInstance(config);
//    }

    /**
     * get a single data operator based on SQLite
     *
     * @return {@link com.litesuits.orm.db.impl.CascadeSQLiteImpl}
     */
    public abstract EasyOrm single();

    /**
     * get a cascade data operator based on SQLite
     *
     * @return {@link com.litesuits.orm.db.impl.CascadeSQLiteImpl}
     */
    public abstract EasyOrm cascade();
    
	@Override
	protected void onAllReferencesReleased() {
		justRelease();
	} 

	protected void justRelease() {
        if (mHelper != null) {
            mHelper.getWritableDatabase().close();
            mHelper.close();
            mHelper = null;
        }
        if (mTableManager != null) {
            mTableManager.release();
            mTableManager = null;
        }
    }
}
