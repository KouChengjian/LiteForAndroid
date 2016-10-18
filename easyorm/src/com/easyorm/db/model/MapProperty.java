package com.easyorm.db.model;



import java.lang.reflect.Field;

import com.easyorm.db.enums.Relation;

/**
 * 映射关系
 */
public class MapProperty extends Property {
	private static final long serialVersionUID = 1641409866866426637L;
	public static final String PRIMARYKEY = " PRIMARY KEY ";
	public Relation relation;

	public MapProperty(Property p, Relation relation) {
		this(p.column, p.field, relation);
	}

	private MapProperty(String column, Field field, Relation relation) {
		super(column, field);
		this.relation = relation;
	}

    public boolean isToMany(){
        return relation == Relation.ManyToMany || relation == Relation.OneToMany;
    }

    public boolean isToOne(){
        return relation == Relation.ManyToOne || relation == Relation.OneToOne;
    }

}
