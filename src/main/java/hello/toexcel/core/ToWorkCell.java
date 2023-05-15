package hello.toexcel.core;

import hello.toexcel.core.style.ToWorkBookStyle;
import hello.toexcel.core.type.ToWorkCellType;
import hello.toexcel.util.ExcelHeaderHelper;
import lombok.Getter;
import lombok.NonNull;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;

import java.util.Calendar;
import java.util.Date;

/**
 * The type To work cell.
 */
public class ToWorkCell implements ExcelHeaderHelper {

    private final ToWorkSheet sheet;
    private final Cell _cell;

    @Getter
    private ToWorkBookStyle style;
    private ToWorkCellType cellType;


    /**
     * Instantiates a new To work cell.
     *
     * @param sheet the sheet
     * @param cell  the cell
     * @param value the value
     */
    public ToWorkCell(@NonNull ToWorkSheet sheet, @NonNull Cell cell, Object value) {

        this.sheet = sheet;
        this._cell = cell;
        this.cellType = ToWorkCellType.VALUE;

        this.style = new ToWorkBookStyle(value);
        this._cell.setCellStyle(this.sheet.getWorkBook().createStyle(this));

        if (value == null) {
            return;
        }
        this.updateValue(value);
    }

    /**
     * Instantiates a new To work cell.
     *
     * @param sheet the sheet
     * @param cell  the cell
     * @param value the value
     * @param style the style
     */
    public ToWorkCell(@NonNull ToWorkSheet sheet, @NonNull Cell cell, Object value, @NonNull ToWorkBookStyle style) {

        this(sheet, cell, value);

        this.style = style;
        this.cellType = ToWorkCellType.CUSTOM;
        this._cell.setCellStyle(this.sheet.getWorkBook().createStyle(this));
    }

    /**
     * Instantiates a new To work cell.
     *
     * @param sheet the sheet
     * @param cell  the cell
     * @param value the value
     * @param type  the type
     */
    public ToWorkCell(@NonNull ToWorkSheet sheet, @NonNull Cell cell, Object value, @NonNull ToWorkCellType type) {

        this(sheet, cell, value);
        this.cellType = type;

        if (type.isTitle()) {
            this.style.updateTitleType();
        }
        this._cell.setCellStyle(this.sheet.getWorkBook().createStyle(this));

    }


    /**
     * Update style.
     *
     * @param cellStyle the cell style
     */
    public void updateStyle(@NonNull CellStyle cellStyle) {
        this._cell.setCellStyle(cellStyle);
    }

    private ToWorkCell updateValue(Object value) {
        if (value instanceof Double) {
            this._cell.setCellValue((double) value);
        } else if ( value instanceof Integer ){
            this._cell.setCellValue((int) value);
        } else if ( value instanceof Long ){
            this._cell.setCellValue((long) value);
        } else if (value instanceof Boolean) {
            this._cell.setCellValue((Boolean) value);
        } else if (value instanceof RichTextString) {
            this._cell.setCellValue((RichTextString) value);
        } else if (value instanceof Date) {
            this._cell.setCellValue((Date) value);
        } else if (value instanceof Calendar) {
            this._cell.setCellValue((Calendar) value);
        } else {
            this._cell.setCellValue(String.valueOf(value));
        }
        return this;
    }
}
