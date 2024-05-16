package org.example.exchangerate.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    private DateUtils() {}

    public static LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(date, formatter);
    }

    public static String formatDateToCompactString(String date) {
        return date.replace("-", "");
    }
}
