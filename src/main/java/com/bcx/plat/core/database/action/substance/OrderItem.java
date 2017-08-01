package com.bcx.plat.core.database.action.substance;

import com.bcx.plat.core.database.action.phantom.Column;

public class OrderItem {
    public static final int ASC=0,DESC=1;
    Column column;
    int type;

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public OrderItem(Column column, int type) {
        this.column = column;
        this.type = type;
    }
}
