package hello.toexcel.util;


import hello.toexcel.annotation.ExcelHeader;
import hello.toexcel.core.converter.ExcelHeaderDefatulConverter;
import hello.toexcel.core.model.ToTitleKey;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * The interface To excel csv converter.
 */
public interface ToExcelCsvConverter {

    /**
     * Convert csv str string.
     *
     * @return the string
     */
    default String convertCsvStr() {
        return convertCsvStr(",");
    }

    default String convertCsvStr(String separator) {

        List<Field> fields = getDeclaredFields(this.getClass());

        AtomicInteger fieldCount = new AtomicInteger();
        List<ToTitleKey> keys = fields.stream().filter(field -> field.getAnnotation(ExcelHeader.class) != null)
                .map(field -> new ToTitleKey(field, fieldCount.getAndIncrement(), new ExcelHeaderDefatulConverter()))
                .sorted().collect(Collectors.toList());

        return keys.stream().map(ToTitleKey::getField).map(this::getFieldValue).collect(Collectors.joining(separator));
    }

    /**
     * Gets field value.
     *
     * @param fd the fd
     * @return the field value
     */
    default String getFieldValue(Field fd) {
        try {
            fd.setAccessible(true);
            Object obj = fd.get(this);
            if (obj == null) {
                return "";
            }
            return String.valueOf(obj);
        } catch (IllegalAccessException e) {
            return "";
        }
    }

    /**
     * Gets declared fields.
     *
     * @param type the type
     * @return the declared fields
     */
    default List<Field> getDeclaredFields(Class type) {
        List<Field> fields = Arrays.stream(type.getDeclaredFields()).collect(Collectors.toList());
        if (type.getSuperclass() != null && !type.getSuperclass().equals(Object.class)) {
            fields.addAll(getDeclaredFields(type.getSuperclass()));
        }
        return fields;
    }
}
