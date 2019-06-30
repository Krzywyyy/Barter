package pl.krzywyyy.barter.exception;

public class ObjectNotExistsException extends Exception {

    public ObjectNotExistsException(Class clazz, String field) {
        super(ObjectNotExistsException.message(clazz.getSimpleName(),field));
    }

    private static String message(String simpleName, String field) {
        return String.format("%s: %s doesn't exists",simpleName,field);
    }
}
