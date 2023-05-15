package hello.toexcel.core.converter;

import hello.toexcel.annotation.ExcelHeader;

/**
 * The interface Excel header converter.
 */
public interface ExcelHeaderConverter {

    /**
     * Header key converter string.
     *
     * @param excelHeader the excel header
     * @return the string
     */
    String headerKeyConverter(ExcelHeader excelHeader);
}
