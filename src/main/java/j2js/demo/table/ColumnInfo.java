/*
 * Copyright (c) 2005 j2js.com,
 *
 * All Rights Reserved. This work is distributed under the j2js Software License [1]
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.j2js.com/license.txt
 */

package j2js.demo.table;

/**
 * This class holds meta information about a column of a table.
 * 
 * @author j2js.com
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
