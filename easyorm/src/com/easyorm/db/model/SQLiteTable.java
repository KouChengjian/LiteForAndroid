package com.easyorm.db.model;

import java.io.Serializable;
import java.util.HashMap;

import com.easyorm.db.annotation.Column;


/**
 * 表结构，SQLite中的每一张表都有这样的属性。
 * CREATE TABLE sqlite_master (
 *　type TEXT,
 *　name TEXT,
 *　tbl_name TEXT,
 *　rootpage INTEGER,
 *　sql TEXT
 * );
 */
public class SQLiteTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5599023598069684010L;

	@Column("type")
    public String type;

    @Column("name")
    public String name;

    @Column("tbl_name")
    public String tbl_name;

    @Column("rootpage")
    public long rootpage;

    @Column("sql")
    public String sql;

    public boolean isTableChecked;

    public HashMap<String, Integer> columns;

	@Override
	public String toString() {
		return "SQLiteTable [type=" + type + ", name=" + name + ", tbl_name="
				+ tbl_name + ", rootpage=" + rootpage + ", sql=" + sql
				+ ", isTableChecked=" + isTableChecked + ", columns=" + columns
				+ "]";
	}
    
}
