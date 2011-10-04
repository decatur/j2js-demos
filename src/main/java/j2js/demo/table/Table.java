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

import org.w3c.dom5.Document;
import org.w3c.dom5.Element;
import org.w3c.dom5.Node;
import org.w3c.dom5.NodeList;
import org.w3c.dom5.Text;
import org.w3c.dom5.html.*;

import j2js.Global;
import j2js.net.HttpRequest;
import j2js.net.ReadyStateChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.lang.RuntimeException;

/**
 * A smart HTML table which supports client side editing and server fetches.
 * This implementation serves instructional purposes only.
 * 
 * @author j2js.com
 */
public class Table implements ReadyStateChangeListener {

    // The owner document of this table.
    private Document document;
    
    // The HTML table element of this table.
    private HTMLTableElement tableElement;
    
    // Each column is described by a ColumnInfo object.
    private ArrayList<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>();
    
    // The body of the table;
    private HTMLTableSectionElement tBody;
    
    // True if the table has a header row.
    private boolean hasHeaders = false;
    
    private int sequence = 1;
    
    /**
     * Appends a smart table to the specified element. 
     */
    public Table(Element container) {
        this.document = container.getOwnerDocument();
        if (container == null) {
            throw new NullPointerException("Container element must not be null");
        }
       
        init(container);
    }
    
    /**
     * Appends a smart table to the element specified by it's id. 
     */
    public Table(Document theDocument, String containerId) {
        this.document = theDocument;
        Element container = theDocument.getElementById(containerId);
        if (container == null) {
            throw new NullPointerException("No HTML element with id: " + containerId);
        }
       
        init(container);
    }
    
    private void init(Element container) {
        tableElement = (HTMLTableElement) document.createElement("TABLE");
        tableElement.setBorder("1");
        container.appendChild(tableElement);
        tBody = (HTMLTableSectionElement) document.createElement("TBODY");
        tableElement.appendChild(tBody);
    }
    
    /**
     * Sets the column headers.
     */
    public void setHeaders(List<String> headers) {
        hasHeaders = true;
        for (String header : headers) {
            ColumnInfo info = new ColumnInfo();
            info.setHeader(header);
            columnInfos.add(info);
        }
        addRow(headers, "TH");
    }

    private Element getFirstChildElement(Element node) {
        NodeList children = node.getChildNodes();
        for (int i=0; i<children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                return (Element) children.item(i);
            }
        }
        return null;
    }
   
    /**
     * Returns the info for the specified column.
     */
    public ColumnInfo getColumnInfo(int colIndex) {
        return columnInfos.get(colIndex);
    }
    
    /**
     * Returns the number of data rows in the table.
     */
    public int rowCount() {
        int count = tBody.getChildNodes().getLength();
        if (hasHeaders) count--;
        return count;
    }
    
    /**
     * Clears all data rows of the table.
     */
    public void clear() {
        do {
            Element element = (Element) getFirstChildElement(tBody).getNextSibling();
            if (element == null) {
                break;
            }
            tBody.removeChild(element);
        } while (true);
        sequence = 1;
    }

    /**
     * Returns the zero-based index of the specified table row. This is the
     * inverse operation of {@link #getRow(int)}.
     */
    public int getIndex(HTMLTableRowElement row) {
        return row.getSectionRowIndex();
    }
    
    /**
     * Returns the table row for a given zero-based index. This is the
     * inverse operation of {@link #getIndex(HTMLTableRowElement)}.
     */
    public HTMLTableRowElement getRow(int rowIndex) {
        NodeList rows = tBody.getChildNodes();
        if (rowIndex<0 || rowIndex>=rows.getLength()) {
            throw new RuntimeException("Row index out of bound: " + rowIndex);
        }
        return (HTMLTableRowElement) rows.item(rowIndex);
    }
    
    /**
     * Removes a table row.
     * 
     * @param rowIndex the zero-based index of the row
     * @return the removed row
     */
    public HTMLTableRowElement removeRowByIndex(int rowIndex) {
        return removeRow(getRow(rowIndex));
    }
    
    /**
     * Removes the specified table row.
     * 
     * @return the removed row
     */
    public HTMLTableRowElement removeRow(HTMLTableRowElement row) {
        int count = getIndex(row);
        HTMLTableRowElement r = (HTMLTableRowElement) row.getNextSibling();
        while (r != null) {
            ((Text) r.getFirstChild().getFirstChild()).setNodeValue("" + count);
            r = (HTMLTableRowElement) r.getNextSibling();
            count++;
        }
        tBody.removeChild(row);
        return row;
    }

    /**
     * Adds a new data row to the table.
     * 
     * @param data the cell data for the row
     */
    public void addRow(List<String> data) {
        addRow(data, "TD");
    }
    
    /**
     * Adds a single data or header row to this table.
     *
     * @param data the data array
     * @param cellName either "TD" or "TH"
     */
    private void addRow(List<String> dataList, String cellName) {
        HTMLTableRowElement tr = (HTMLTableRowElement) document.createElement("TR");
        HTMLTableCellElement td;
        
        td = (HTMLTableCellElement) document.createElement(cellName);
        
        if (cellName.equals("TD")) {
            // Generate row number.
            td.appendChild(document.createTextNode(Integer.toString(sequence++)));
            tr.appendChild(td);
            
            // Generate checkbox.
            td = (HTMLTableCellElement) document.createElement(cellName);
            HTMLInputElement input = (HTMLInputElement) document.createElement("INPUT");
            input.setAttribute("type", "checkbox");
            td.appendChild(input);
            
        } else {
            td.setColSpan(2);
        }
        
        tr.appendChild(td);
        
        for (String dataEntry : dataList) {
            td = (HTMLTableCellElement) document.createElement(cellName);
            String value;
            td.appendChild(document.createTextNode(dataEntry));
            tr.appendChild(td);
        }
        tBody.appendChild(tr);
    }
    
    /**
     * Add rows from data loaded by HTTP request. 
     */
    public void handleEvent(HttpRequest request) {
        if (request.getReadyState() != HttpRequest.STATE_LOADED) {
            return;
        }
        
        if (request.getStatus() != 200 && request.getStatus() != 0) {
            Global.window.alert("Request failed with status " + request.getStatus());
            return;
        }
        
        addRows((List<List<String>>) request.getResponseObject());
    }
    
    /**
     * Adds rows to this table from a list of list of Strings.
     */
    public void addRows(List<List<String>> listOfLists) {
        for (List<String> list : listOfLists) {
            addRow(list);
        }
    }
    
    /**
     * Returns a vector containing all selected rows of type <code>HTMLTableRowElement</code>.  
     */
    public ArrayList<HTMLTableRowElement> getSelectedRows() {
        NodeList children = tBody.getChildNodes();
        ArrayList<HTMLTableRowElement> selectedRows = new ArrayList<HTMLTableRowElement>();
        for (int i=0; i<children.getLength(); i++) {
            HTMLTableRowElement row = (HTMLTableRowElement) children.item(i);
            HTMLInputElement input = 
                (HTMLInputElement) row.getFirstChild().getNextSibling().getFirstChild();
            if (input.getChecked()) {
                selectedRows.add(row);
            }
        }
        return selectedRows;
    }

    /**
     * Returns the content of the specified cell.
     */
    public String getCellHtml(HTMLTableRowElement row, int columnIndex) {
        HTMLElement element = (HTMLElement) row.getCells().item(columnIndex+1);
        return element.getAttribute("innerHTML");
    }

    /**
     * Return the table element.
     */
    public HTMLTableElement getTableElement() {
        return tableElement;
    }
    
}