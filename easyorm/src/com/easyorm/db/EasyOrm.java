package com.easyorm.db;

import android.content.Context;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;

import com.easyorm.EasyConfig;
import com.easyorm.db.assit.SQLiteHelper;
import com.easyorm.db.impl.CascadeSQLiteImpl;
import com.easyorm.db.impl.SingleSQLiteImpl;

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
//        EasyLog.e(TAG, "create  database path: " + path);
//        path = mConfig.context.getDatabasePath(mConfig.dbName).getPath();
//        EasyLog.e(TAG, "context database path: " + path);
//        File dbp = new File(path).getParentFile();
//        if (dbp != null && !dbp.exists()) {
//            boolean mks = dbp.mkdirs();
//            EasyLog.e(TAG, "create database, parent file mkdirs: " + mks + "  path:" + dbp.getAbsolutePath());
//        }
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
    public static EasyOrm newCascadeInstance(Context context, String dbName) {
        return newCascadeInstance(new DataBaseConfig(context, dbName));
    }

    /**
     * get and new a cascade model operator based on SQLite
     *
     * @param config lite-orm config
     * @return {@link CascadeSQLiteImpl}
     */
    public synchronized static EasyOrm newCascadeInstance(DataBaseConfig config) {
        return CascadeSQLiteImpl.newInstance(config);
    }

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
    
//    @Override
//    public ArrayList<RelationKey> queryRelation(final Class class1, final Class class2, final List<String> key1List) {
//        acquireReference();
//        final ArrayList<RelationKey> rList = new ArrayList<RelationKey>();
//        try {
//            final EntityTable table1 = TableManager.getTable(class1);
//            final EntityTable table2 = TableManager.getTable(class2);
//            if (mTableManager.isSQLMapTableCreated(table1.name, table2.name)) {
//                CollSpliter.split(key1List, SQLStatement.IN_TOP_LIMIT, new CollSpliter.Spliter<String>() {
//
//                    @Override
//                    public int oneSplit(ArrayList<String> list) throws Exception {
//                        SQLStatement stmt = SQLBuilder.buildQueryRelationSql(class1, class2, key1List);
//                        Querier.doQuery(mHelper.getReadableDatabase(), stmt, new Querier.CursorParser() {
//
//                            @Override
//                            public void parseEachCursor(SQLiteDatabase db, Cursor c) throws Exception {
//                                RelationKey relation = new RelationKey();
//                                relation.key1 = c.getString(c.getColumnIndex(table1.name));
//                                relation.key2 = c.getString(c.getColumnIndex(table2.name));
//                                rList.add(relation);
//                            }
//
//                        });
//                        return 0;
//
//                    }
//                });
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            releaseReference();
//        }
//        return rList;
//    }
//
//    @Override
//    public <E, T> boolean mapping(Collection<E> col1, Collection<T> col2) {
//        if (Checker.isEmpty(col1) || Checker.isEmpty(col2)) {
//            return false;
//        }
//        acquireReference();
//        try {
//            return keepMapping(col1, col2) | keepMapping(col2, col1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            releaseReference();
//        }
//        return false;
//    }
//
//
//    @Override
//    public SQLStatement createSQLStatement(String sql, Object[] bindArgs) {
//        return new SQLStatement(sql, bindArgs);
//    }
//
//    @Override
//    public boolean execute(SQLiteDatabase db, SQLStatement statement) {
//        acquireReference();
//        try {
//            if (statement != null) {
//                return statement.execute(db);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            releaseReference();
//        }
//        return false;
//
//    }
//
//    @Override
//    @Deprecated
//    public boolean dropTable(Object entity) {
//        return dropTable(entity.getClass());
//    }
//
//    @Override
//    public boolean dropTable(Class<?> claxx) {
//        return dropTable(TableManager.getTable(claxx, false).name);
//    }
//
//    @Override
//    public boolean dropTable(String tableName) {
//        acquireReference();
//        try {
//            return SQLBuilder.buildDropTable(tableName).execute(mHelper.getWritableDatabase());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            releaseReference();
//        }
//        return false;
//    }
//
//
//    @Override
//    public <T> long queryCount(Class<T> claxx) {
//        return queryCount(new QueryBuilder<T>(claxx));
//    }
//
//    @Override
//    public long queryCount(QueryBuilder qb) {
//        acquireReference();
//        try {
//            if (mTableManager.isSQLTableCreated(qb.getTableName())) {
//                SQLiteDatabase db = mHelper.getReadableDatabase();
//                SQLStatement stmt = qb.createStatementForCount();
//                return stmt.queryForLong(db);
//            } else {
//                return 0;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            releaseReference();
//        }
//        return SQLStatement.NONE;
//    }
//
//    @Override
//    public int update(WhereBuilder where, ColumnsValue cvs, ConflictAlgorithm conflictAlgorithm) {
//        acquireReference();
//        try {
//            SQLiteDatabase db = mHelper.getWritableDatabase();
//            SQLStatement stmt = SQLBuilder.buildUpdateSql(where, cvs, conflictAlgorithm);
//            return stmt.execUpdate(db);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            releaseReference();
//        }
//        return SQLStatement.NONE;
//    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        return mHelper.getReadableDatabase();
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    @Override
    public TableManager getTableManager() {
        return mTableManager;
    }

    @Override
    public SQLiteHelper getSQLiteHelper() {
        return mHelper;
    }

    @Override
    public DataBaseConfig getDataBaseConfig() {
        return mConfig;
    }

//    @Override
//    public SQLiteDatabase openOrCreateDatabase(String path, SQLiteDatabase.CursorFactory factory) {
//        path = mConfig.context.getDatabasePath(mConfig.dbName).getPath();
//        return SQLiteDatabase.openOrCreateDatabase(path, factory);
//    }
//
//    @Override
//    public boolean deleteDatabase() {
//        String path = mHelper.getWritableDatabase().getPath();
//        justRelease();
//        OrmLog.i(TAG, "data has cleared. delete Database path: " + path);
//        return deleteDatabase(new File(path));
//    }
//
//    @Override
//    public boolean deleteDatabase(File file) {
//        acquireReference();
//        try {
//            if (file == null) {
//                throw new IllegalArgumentException("file must not be null");
//            }
//            boolean deleted = file.delete();
//            deleted |= new File(file.getPath() + "-journal").delete();
//            deleted |= new File(file.getPath() + "-shm").delete();
//            deleted |= new File(file.getPath() + "-wal").delete();
//
//            File dir = file.getParentFile();
//            if (dir != null) {
//                final String prefix = file.getName() + "-mj";
//                final FileFilter filter = new FileFilter() {
//                    @Override
//                    public boolean accept(File candidate) {
//                        return candidate.getName().startsWith(prefix);
//                    }
//                };
//                for (File masterJournal : dir.listFiles(filter)) {
//                    deleted |= masterJournal.delete();
//                }
//            }
//            return deleted;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            releaseReference();
//        }
//        return false;
//    }

    @Override
    public synchronized void close() {
        releaseReference();
    }
    
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
	
	/**
     * Attempts to release memory that SQLite holds but does not require to
     * operate properly. Typically this memory will come from the page cache.
     *
     * @return the number of bytes actually released
     */
//    public static int releaseMemory() {
//        return SQLiteDatabase.releaseMemory();
//    }
	
	/* --------------------------------  私有方法 -------------------------------- */
//    private <E, T> boolean keepMapping(Collection<E> col1,
//                                       Collection<T> col2) throws IllegalAccessException, InstantiationException {
//        Class claxx1 = col1.iterator().next().getClass();
//        Class claxx2 = col2.iterator().next().getClass();
//        EntityTable table1 = TableManager.getTable(claxx1);
//        EntityTable table2 = TableManager.getTable(claxx2);
//        if (table1.mappingList != null) {
//            for (MapProperty mp : table1.mappingList) {
//                Class itemClass;
//                Class fieldClass = mp.field.getType();
//                if (mp.isToMany()) {
//                    // N对多关系
//                    if (ClassUtil.isCollection(fieldClass)) {
//                        itemClass = FieldUtil.getGenericType(mp.field);
//                    } else if (fieldClass.isArray()) {
//                        itemClass = FieldUtil.getComponentType(mp.field);
//                    } else {
//                        throw new RuntimeException(
//                                "OneToMany and ManyToMany Relation, Must use collection or array object");
//                    }
//                } else {
//                    itemClass = fieldClass;
//                }
//                if (itemClass == claxx2) {
//                    ArrayList<String> key1List = new ArrayList<String>();
//                    HashMap<String, Object> map1 = new HashMap<String, Object>();
//                    // 构建第1个集合对象的key集合以及value映射
//                    for (Object o1 : col1) {
//                        if (o1 != null) {
//                            Object key1 = FieldUtil.get(table1.key.field, o1);
//                            if (key1 != null) {
//                                key1List.add(key1.toString());
//                                map1.put(key1.toString(), o1);
//                            }
//                        }
//                    }
//                    ArrayList<RelationKey> relationKeys = queryRelation(claxx1, claxx2, key1List);
//                    if (!Checker.isEmpty(relationKeys)) {
//                        HashMap<String, Object> map2 = new HashMap<String, Object>();
//                        // 构建第2个对象的value映射
//                        for (Object o2 : col2) {
//                            if (o2 != null) {
//                                Object key2 = FieldUtil.get(table2.key.field, o2);
//                                if (key2 != null) {
//                                    map2.put(key2.toString(), o2);
//                                }
//                            }
//                        }
//                        HashMap<Object, ArrayList> collMap = new HashMap<Object, ArrayList>();
//                        for (RelationKey m : relationKeys) {
//                            Object obj1 = map1.get(m.key1);
//                            Object obj2 = map2.get(m.key2);
//                            if (obj1 != null && obj2 != null) {
//                                if (mp.isToMany()) {
//                                    // N对多关系
//                                    ArrayList col = collMap.get(obj1);
//                                    if (col == null) {
//                                        col = new ArrayList();
//                                        collMap.put(obj1, col);
//                                    }
//                                    col.add(obj2);
//                                } else {
//                                    FieldUtil.set(mp.field, obj1, obj2);
//                                }
//                            }
//                        }
//                        // N对多关系,查出来的数组
//                        if (!Checker.isEmpty(collMap)) {
//                            for (Map.Entry<Object, ArrayList> entry : collMap.entrySet()) {
//                                Object obj1 = entry.getKey();
//                                Collection tempColl = entry.getValue();
//                                if (ClassUtil.isCollection(itemClass)) {
//                                    Collection col = (Collection) FieldUtil.get(mp.field, obj1);
//                                    if (col == null) {
//                                        FieldUtil.set(mp.field, obj1, tempColl);
//                                    } else {
//                                        col.addAll(tempColl);
//                                    }
//                                } else if (ClassUtil.isArray(itemClass)) {
//                                    Object[] tempArray = (Object[]) ClassUtil.newArray(itemClass, tempColl.size());
//                                    tempColl.toArray(tempArray);
//                                    Object[] array = (Object[]) FieldUtil.get(mp.field, obj1);
//                                    if (array == null) {
//                                        FieldUtil.set(mp.field, obj1, tempArray);
//                                    } else {
//                                        Object[] newArray = DataUtil.concat(array, tempArray);
//                                        FieldUtil.set(mp.field, obj1, newArray);
//                                    }
//                                }
//                            }
//
//                        }
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
}
