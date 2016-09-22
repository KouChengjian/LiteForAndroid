package com.easy.db.impl;

import android.database.sqlite.SQLiteDatabase;

import com.easy.db.DataBaseConfig;
import com.easy.db.EasyOrm;
import com.easy.db.assit.SQLBuilder;
import com.easy.db.assit.SQLStatement;

/**
 * 数据SQLite操作实现
 */
public final class SingleSQLiteImpl extends EasyOrm {

	public static final String TAG = SingleSQLiteImpl.class.getSimpleName();
	
	protected SingleSQLiteImpl(EasyOrm dataBase) {
        super(dataBase);
    }

    private SingleSQLiteImpl(DataBaseConfig config) {
        super(config);
    }
    
    public synchronized static EasyOrm newInstance(DataBaseConfig config) {
        return new SingleSQLiteImpl(config);
    }
	
    @Override
    public EasyOrm single() {
        return this;
    }

    @Override
    public EasyOrm cascade() {
//        if (otherDatabase == null) {
//            otherDatabase = new CascadeSQLiteImpl(this);
//        }
        return otherDatabase;
    }
    
	@Override
	public long save(Object entity) {
		acquireReference();
        try {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            mTableManager.checkOrCreateTable(db, entity);
            return SQLBuilder.buildReplaceSql(entity).execInsert(db, entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseReference();
        }
        return SQLStatement.NONE;
	}

	

}
