package com.easy.db.model;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.easy.db.utils.DataUtil;

/**
 * 属性
 */
public class Property implements Serializable{
	
	private static final long serialVersionUID = 1542861322620643038L;
    public String column;
    public Field field;
    public int classType = DataUtil.CLASS_TYPE_NONE;

    //public Property() {
    //}

    public Property(String column, Field field) {
        this.column = column;
        this.field = field;
        if (classType <= 0) {
            this.classType = DataUtil.getFieldClassType(field);
        }
    }

    public Property(String column, Field field, int classType) {
        this.column = column;
        this.field = field;
        if (classType <= 0) {
            this.classType = DataUtil.getFieldClassType(field);
        }
        this.classType = classType;
    }

    @Override
    public String toString() {
        return "Property{" +
               "column='" + column + '\'' +
               ", field=" + field +
               ", classType=" + classType +
               '}';
    }
	
}
