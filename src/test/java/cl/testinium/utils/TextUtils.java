package cl.testinium.utils;

import com.gbursali.strings.Placeholders;
import com.machinezoo.noexception.Exceptions;
import de.svenjacobs.loremipsum.LoremIpsum;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TextUtils {

    public static Placeholders commonPlaceholders = Placeholders.of()
            .definePrefix("<")
            .defineSuffix(">")
            .add("random",()-> RandomStringUtils.randomAlphabetic(6))
            .add("huge text",()-> new LoremIpsum().getWords(200));

    public static double convertToDouble(String value){
        return Exceptions
                .wrap(e -> new IllegalStateException(JsonReader.getExceptionMessage("Balance is not a valid number"), e))
                .get(()-> NumberFormat.getInstance().parse(value).doubleValue());
    }

    public static String formatToText(double value){
        return DecimalFormat.getInstance().format(value);
    }
}
