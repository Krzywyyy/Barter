package pl.krzywyyy.barter.exception;

public class OfferAlreadyConsideredException extends Exception {

    public OfferAlreadyConsideredException(int offerId) {
        super(OfferAlreadyConsideredException.message(offerId));
    }

    private static String message(int offerId) {
        return String.format("Offer with ID: %s is already considered", offerId);
    }
}
