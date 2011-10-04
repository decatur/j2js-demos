package j2js.demo;

/**
 * Checks for valid credit card number using Luhn algorithm.
 * <br/>
 * Taken from an online article by Michael Gilleland.
 */
public abstract class CreditCardValidator {

    /**
     * Validates a list of credit card numbers. If no numbers are specified,
     * a hard-coded list of four numbers is validated.
     * 
     * @param args the list of credit card numbers.
     */
    public static void main(String[] args) {

        if (args == null || args.length == 0) {
            args = new String[]{
                    "4408 0412 3456 7890",
                    "4408 0412 3456 7893",
                    "4417 1234 5678 9112",
                    "4417 1234 5678 9113"};
        }
        
        for (String cardNumber : args) {
            System.out.println (cardNumber + ": " + isValid(cardNumber));
        }
    }

   /**
    * Perform Luhn check.
    */
   public static boolean isValid(String cardNumber) {
     // Filter out non-digit characters.
     String digitsOnly = cardNumber.replaceAll("\\D", "");
     int sum = 0;
     boolean timesTwo = false;

     for (int i = digitsOnly.length () - 1; i >= 0; i--) {
         int digit = Integer.parseInt (digitsOnly.substring (i, i + 1));
         if (timesTwo) {
             digit *= 2;
             if (digit > 9) {
                 digit -= 9;
             }
         }
         sum += digit;
         timesTwo = !timesTwo;
     }

     return sum % 10 == 0;
   }
}
