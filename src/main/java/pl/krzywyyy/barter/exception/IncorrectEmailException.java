package pl.krzywyyy.barter.exception;

public class IncorrectEmailException extends Exception {
    public IncorrectEmailException(String field) {
        super(IncorrectEmailException.message(field));
    }

    private static String message(String field) {
        return String.format("Email %s is not student email from Military University of Technology",
                field);
    }
}
