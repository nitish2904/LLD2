package ATMSystem.model;
public class Card {
    private final String cardNumber;
    private final String pin;
    private final String accountId;
    public Card(String cardNumber, String pin, String accountId) {
        this.cardNumber = cardNumber; this.pin = pin; this.accountId = accountId;
    }
    public String getCardNumber() { return cardNumber; }
    public String getPin() { return pin; }
    public String getAccountId() { return accountId; }
    public boolean validatePin(String inputPin) { return pin.equals(inputPin); }
    @Override public String toString() { return "Card[****" + cardNumber.substring(cardNumber.length()-4) + "]"; }
}
