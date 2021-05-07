package com.accenture.surfershells;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DBType {
    static Date parseDate(String date) {
        return Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yy")));
    }

    static Double parseToDouble(String value) {
        return Double.parseDouble(value.replaceAll("€", "").replaceAll(" ", "").replace(",", "."));
    }

    
}
