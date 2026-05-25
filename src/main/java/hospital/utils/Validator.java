package hospital.utils;

import hospital.exceptions.InvalidInputException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Validator {

    private static final int MIN_PASSWORD_LENGTH = 8;

    public static void validateNotEmpty(String value, String fieldName)
            throws InvalidInputException {

        if (value == null || value.trim().isEmpty()) {

            throw new InvalidInputException(
                    fieldName,
                    "this field cannot be empty."
            );
        }
    }

    public static void validateEmail(String email)
            throws InvalidInputException {

        if (email == null ||
            !email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")) {

            throw new InvalidInputException(
                    "email",
                    "must be a valid address like user@domain.com"
            );
        }
    }

    public static void validatePassword(String password)
            throws InvalidInputException {

        if (password == null ||
            password.length() < MIN_PASSWORD_LENGTH) {

            throw new InvalidInputException(
                    "password",
                    "must be at least "
                            + MIN_PASSWORD_LENGTH +
                            " characters long."
            );
        }

        if (!password.matches(".*[A-Z].*")) {

            throw new InvalidInputException(
                    "password",
                    "must contain at least one uppercase letter."
            );
        }

        if (!password.matches(".*\\d.*")) {

            throw new InvalidInputException(
                    "password",
                    "must contain at least one digit."
            );
        }
    }

    public static void validatePhone(String phone)
            throws InvalidInputException {

        if (phone == null ||
            !phone.matches("^\\d{10}$")) {

            throw new InvalidInputException(
                    "phone",
                    "must be exactly 10 digits."
            );
        }
    }

    public static void validateAge(int age)
            throws InvalidInputException {

        if (age <= 0 || age > 130) {

            throw new InvalidInputException(
                    "age",
                    "must be between 1 and 130."
            );
        }
    }

    public static void validateDate(LocalDate date)
            throws InvalidInputException {

        if (date == null) {

            throw new InvalidInputException(
                    "date",
                    "date cannot be null."
            );
        }
    }

    public static void validateTime(LocalTime time)
            throws InvalidInputException {

        if (time == null) {

            throw new InvalidInputException(
                    "time",
                    "time cannot be null."
            );
        }
    }

    public static LocalDate parseDate(String date)
            throws InvalidInputException {

        try {

            return LocalDate.parse(date);

        } catch (DateTimeParseException e) {

            throw new InvalidInputException(
                    "date",
                    "must be in format YYYY-MM-DD."
            );
        }
    }

    public static LocalTime parseTime(String time)
            throws InvalidInputException {

        try {

            return LocalTime.parse(time);

        } catch (DateTimeParseException e) {

            throw new InvalidInputException(
                    "time",
                    "must be in format HH:MM."
            );
        }
    }

    public static void validateFee(double fee)
            throws InvalidInputException {

        if (fee < 0) {

            throw new InvalidInputException(
                    "consultationFee",
                    "cannot be negative."
            );
        }
    }
}