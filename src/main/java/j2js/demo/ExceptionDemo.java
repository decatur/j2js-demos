package j2js.demo;

import j2js.Global;

import org.w3c.dom5.Element;
import org.w3c.dom5.events.Event;
import org.w3c.dom5.events.EventListener;
import org.w3c.dom5.events.EventTarget;

public class ExceptionDemo {
    
    // Define event listener for handled exception.
    class HandledHandler implements EventListener {
        public void handleEvent(Event evt) {
            try {
                crunch();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // Define event listener for unhandled exception.
    class UnhandledHandler implements EventListener {
        public void handleEvent(Event evt) {
            crunch();
        }
    }
    
    public static void main(String args[]) {
        // Attach event listener to button.
        ExceptionDemo demo = new ExceptionDemo();
        Element button;
        
        button = Global.document.getElementById("HANDLED_EXCEPTION");
        ((EventTarget) button).addEventListener("click", demo.new HandledHandler(), false);
        
        button = Global.document.getElementById("UNHANDLED_EXCEPTION");
        ((EventTarget) button).addEventListener("click", demo.new UnhandledHandler(), false);
    }

    void crunch() {
        mash();
    }
    void mash() {
        throw new RuntimeException("Raised exception");
    }
    
}

