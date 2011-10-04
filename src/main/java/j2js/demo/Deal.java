package j2js.demo;

import java.util.*;

public class Deal {
    
    public static void main(String args[]) {
        int numHands = Integer.parseInt(args[0]);
        int cardsPerHand = Integer.parseInt(args[1]);
        List<Card> deck  = Card.newDeck();
        Collections.shuffle(deck);
        for (int i=0; i < numHands; i++)
            System.out.println(deal(deck, cardsPerHand));
    }

    public static ArrayList<Card> deal(List<Card> deck, int n) {
         int deckSize = deck.size();
         List<Card> handView = deck.subList(deckSize-n, deckSize);
         ArrayList<Card> hand = new ArrayList<Card>(handView);
         handView.clear();
         return hand;
     }
}