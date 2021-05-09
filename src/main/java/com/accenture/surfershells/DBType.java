package com.accenture.surfershells;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DBType {
    static Date parseDate(String date) {//e.g. date =20.05.20
        return Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yy")));
    }

    static Double parseToDouble(String value) {
        return Double.parseDouble(value.replace("€", "").replace(" ", "").replace(",", "."));
    }

    
}
