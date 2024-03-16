package com.tqs108287.App;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class Utils {

    public static LocalDate localDateFromDateParts(String year, String month, String day){
        return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    public static LocalDate isoTextToLocalDate(String date){

        return LocalDate.parse(date);
    }

}
