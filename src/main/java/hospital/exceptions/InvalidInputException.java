package hospital.exceptions;
public class InvalidInputException extends Exception {

    private final String fieldName;

    public InvalidInputException(String fieldName, String reason) {
        super("Invalid value for [" + fieldName + "]: " + reason);
        this.fieldName = fieldName;
    }

    public String getFieldName() { return fieldName; }
}