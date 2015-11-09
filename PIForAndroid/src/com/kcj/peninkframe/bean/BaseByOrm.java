package com.kcj.peninkframe.bean;

import java.io.Serializable;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

public class BaseByOrm implements Serializable{

	@PrimaryKey(AssignType.BY_MYSELF)
	@Column("_id")
	protected long id;
	
}
