package hello.toexcel.util.mapper;

import hello.toexcel.util.DateConverter;
import org.modelmapper.AbstractConverter;

/**
 * The type String to string converter.
 */
public class StringToStringConverter extends AbstractConverter<String, String> implements DateConverter {

    @Override
    protected String convert(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        return s;
    }
}
