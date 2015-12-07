package com.liteutil.example.orm;

import com.liteutil.annotation.Column;
import com.liteutil.annotation.Conflict;
import com.liteutil.annotation.NotNull;
import com.liteutil.annotation.PrimaryKey;
import com.liteutil.annotation.Table;
import com.liteutil.orm.db.enums.AssignType;
import com.liteutil.orm.db.enums.Strategy;

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
    @Conflict(Strategy.FAIL)
    public String name;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "} " + super.toString();
    }
}
