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

import j2js.Global;
import j2js.net.FormHttpRequest;
import j2js.net.HttpRequest;
import j2js.demo.table.Table;

import java.util.ArrayList;

import org.w3c.dom5.Element;
import org.w3c.dom5.events.Event;
import org.w3c.dom5.events.EventListener;
import org.w3c.dom5.events.EventTarget;
import org.w3c.dom5.html.HTMLElement;
import org.w3c.dom5.html.HTMLSelectElement;
import org.w3c.dom5.html.HTMLTableRowElement;

import com.j2js.prodmode.net.ScriptHttpRequest;
import com.j2js.prodmode.net.XMLHttpRequest;


/**
 * @author j2js.com
 */
public final class TableDemo {
    
    // Begin onLoad
    public static void main(String[] args) {
        
        EventListener listener = new EventListener() {
            public void handleEvent(Event evt) {
                HTMLElement target = (HTMLElement) evt.getTarget();
                String id = target.getId();
                if (id.equals("create")) create();
                else if (id.equals("addRow")) addRow();
                else if (id.equals("fetch")) fetch();
                else if (id.equals("removeSelection")) removeSelection();
                else if (id.equals("clear")) clear();
                else throw new RuntimeException("Illegal action: " + id);
            }
        };
        
        String[] ids = new String[] {"create", "fetch", "addRow", "removeSelection", "clear"};
        for (int i=0; i<ids.length; i++) {
            Element elem = Global.document.getElementById(ids[i]);
            ((EventTarget) elem).addEventListener("click", listener, false);
        }
    }
    // End onLoad

    static Table table;
    
    // Begin create
    /**
     * Creates the table with headers.
     */
    public static void create() {
        if (table != null) {
            throw new RuntimeException("Table already created");
        }
        // Label{1}
        table = new Table(Global.document, "MyDiv");
        ArrayList<String> headers = new ArrayList<String>();
        headers.add("Name");
        headers.add("Address");
        table.setHeaders(headers);
    }
    // End create
    
    private static void checkCreated() {
        if (table == null) {
            String message = "Please create table first";
            Global.window.alert(message);
            throw new RuntimeException(message);
        }
    }
    
    // Begin fetch
    /**
     * Fetches some rows from the server.
     */
    public static void fetch() {
        checkCreated();
        HttpRequest request;
        String method;
        HTMLSelectElement select =
            (HTMLSelectElement) Global.document.getElementById("HttpRequestType");
        if (select.getSelectedIndex() == 0) {
            request = XMLHttpRequest.getSingleton();
            method = "POST";
        } else if (select.getSelectedIndex() == 1) {
            request = FormHttpRequest.getSingleton();
            method = "POST";
        } else {
            request = ScriptHttpRequest.getSingleton();
            method = "GET";
        }
        
        request.open(method, "fetchTable.php", true);
        request.setReadyStateChangeListener(table);
        
        request.send(null);
    }
    // End fetch
    
    /**
     * Prompts for a new entry and adds it to the table.
     */
    static public void addRow() {
        checkCreated();
        String name = Global.window.prompt("Enter Name", "John Doe");
        String address = Global.window.prompt("Enter Address", "AnyWhere Circle");
        ArrayList<String> dataList = new ArrayList<String>();
        dataList.add(name);
        dataList.add(address);
        table.addRow(dataList);
    }
    
    /**
     * Removes the selected rows.
     */
    static public void removeSelection() {
        checkCreated();
        ArrayList<HTMLTableRowElement> rows  = table.getSelectedRows();
        if (rows.size() == 0) {
            Global.window.alert("Selection is empty!");
        } else {
            for (int i =0; i<rows.size(); i++) {
                table.removeRow(rows.get(i));
            }
        }
    }
    
    /**
     * Clears the table.
     */
    public static void clear() {
        checkCreated();
        table.clear();
    }

}
