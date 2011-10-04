package j2js.demo;

import j2js.Global;

// Begin imports
// End imports
import org.w3c.dom5.Element;
import org.w3c.dom5.events.Event;
import org.w3c.dom5.events.EventListener;
import org.w3c.dom5.events.EventTarget;

public class HelloWorld2 {
    
    // Begin main
    public static void main(String[] args) {
        Global.init();
        
        // Define event listener.
        EventListener listener = new EventListener() {
            public void handleEvent(Event evt) {
                System.out.println("Hello World");
            }
        };
        
        // Attach event listener to button.
        Element button = Global.document.getElementById("Greetings");
        ((EventTarget) button).addEventListener("click", listener, false);
    }
    // End main
}

