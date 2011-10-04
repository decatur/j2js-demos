package j2js.demo;

import j2js.Global;

import org.w3c.dom.svg.SVGCircleElement;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom5.Element;
import org.w3c.dom5.events.Event;
import org.w3c.dom5.events.EventListener;
import org.w3c.dom5.events.EventTarget;
import org.w3c.dom5.html.HTMLObjectElement;

public class SVGDemo implements EventListener {

    private static String svgNS = "http://www.w3.org/2000/svg";
    private SVGDocument doc;
    
    public static void main(String[] args) {
        // Attach event listener to button.
        Element button = Global.document.getElementById("PLOT");
        ((EventTarget) button).addEventListener("click", new SVGDemo(), false);
    }
    
    public SVGDemo() {
        // Fetch SVG document.
        HTMLObjectElement svgObject = (HTMLObjectElement) 
                Global.document.getElementById("svgObject");
        doc = (SVGDocument) svgObject.getContentDocument();
    }

    
    public void handleEvent(Event evt) {
        String cx = String.valueOf(Math.floor(Math.random() * 1200));
        String cy = String.valueOf(Math.floor(Math.random() * 400));
        String r = String.valueOf(Math.floor(Math.random() * 100));
        
        SVGCircleElement circle = (SVGCircleElement) doc.createElementNS(svgNS, "circle");
        circle.setAttribute("cx", cx);
        circle.setAttribute("cy", cy);
        circle.setAttribute("r",  r);
        circle.setAttribute("fill", "red");
        circle.setAttribute("stroke", "blue");
        circle.setAttribute("stroke-width", "10");

        doc.getDocumentElement().appendChild(circle);
        System.out.println("Added circle at (" + cx + ", " + cy +")");
    }

}
