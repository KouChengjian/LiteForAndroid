package com.kcj.peninkframe.bean;

import java.io.Serializable;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.PrimaryKey.AssignType;

public class BaseAutoOrm implements Serializable{

	@PrimaryKey(AssignType.AUTO_INCREMENT)
	@Column("_id")
    protected long id;
	
	@Override
    public String toString() {
        return "BaseAutoOrm{" +"id=" + id + super.toString();
    }
}
