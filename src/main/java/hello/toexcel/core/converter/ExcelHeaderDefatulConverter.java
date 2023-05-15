package hello.toexcel.core.converter;

import hello.toexcel.annotation.ExcelHeader;

/**
 * The type Excel header defatul converter.
 */
public class ExcelHeaderDefatulConverter implements ExcelHeaderConverter {

    @Override
    public String headerKeyConverter(ExcelHeader excelHeader) {
        return excelHeader.headerName();
    }
}
