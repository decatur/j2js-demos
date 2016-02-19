/*
 * Copyright (c) 2005 Wolfgang Kuehn
 */

package j2js.demo.table;

/**
 * This class holds meta information about a column of a table.
 */
public class ColumnInfo {
    
    private String dataType;
    private String header;
    
    public ColumnInfo() {
        setDataType("String");
    }

    /**
     * @see #setDataType(String)
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets the data type of the column.
     */
    public void setDataType(String theDataType) {
        dataType = theDataType;
    }

    /**
     * @see #setHeader(String)
     */
    public String getHeader() {
        return header;
    }

    /**
     * Sets the displayed header for this column.
     */
    public void setHeader(String theHeader) {
        this.header = theHeader;
    }
}
