package com.kcj.peninkframe.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Conflict;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.PrimaryKey.AssignType;
import com.litesuits.orm.db.annotation.Table;

/**
 * Wifi 和Man 是一对一关系
 * 
 * @author MaTianyu
 * 2014-3-7上午10:39:45
 */
@Table("wife")
public class Person extends  BaseModel{
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    protected long id;

    @NotNull
    @Conflict(Conflict.Strategy.FAIL)
    public String name;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }
}
