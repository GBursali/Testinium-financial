package cl.testinium.utils;

import com.machinezoo.noexception.Exceptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtils {

    public static double convertToDouble(String value){
        return Exceptions
                .wrap(e -> new IllegalStateException(JsonReader.getExceptionMessage("Balance is not a valid number"), e))
                .get(()-> NumberFormat.getInstance().parse(value).doubleValue());
    }

    public static String formatToText(double value){
        return DecimalFormat.getInstance().format(value);
    }
}
