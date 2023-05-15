package hello.toexcel.core;

import hello.toexcel.core.style.ToWorkBookStyle;
import hello.toexcel.core.type.ToWorkBookType;
import hello.toexcel.exception.SheetNotFoundException;
import lombok.Getter;
import lombok.NonNull;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The type To work book.
 */
public class ToWorkBook {

    /**
     * The constant MAX_EXCEL_ROW.
     */
    private final Workbook _wb;
    private final Map<ToWorkBookStyle, CellStyle> _styleMap = new HashMap<>();

    @Getter
    private final ToWorkBookType type;

    @Getter
    private List<ToWorkSheet> sheets = new ArrayList<>();

    /**
     * Instantiates a new To work book.
     *
     * @param type the type
     */
    public ToWorkBook(@NonNull ToWorkBookType type) {
        this.type = type;
        this._wb = type.createWorkBookInstance();
    }


    /**
     * Instantiates a new To work book.
     *
     * @param file the file
     * @throws IOException the io exception
     */
    public ToWorkBook(@NonNull File file) throws IOException {
        this(ToWorkBookType.findWorkBookType(file.getName()), file);
    }

    /**
     * Instantiates a new To work book.
     *
     * @param type the type
     * @param file the file
     * @throws IOException the io exception
     */
    public ToWorkBook(@NonNull ToWorkBookType type, @NonNull File file) throws IOException {
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }

        this.type =type;
        this._wb = type.createWorkBookInstance(file);
        this.__initSheet();
    }

    /**
     * Instantiates a new To work book.
     *
     * @param type the type
     * @param fis  the fis
     * @throws IOException the io exception
     */
    public ToWorkBook(@NonNull ToWorkBookType type, @NonNull FileInputStream fis) throws IOException {
        this.type = type;
        this._wb = type.createWorkBookInstance(fis);
        this.__initSheet();
    }

    /**
     * Create sheet to work sheet.
     *
     * @return the to work sheet
     */
    public ToWorkSheet createSheet() {
        ToWorkSheet sheet = new ToWorkSheet(this, this._wb);
        this.sheets.add(sheet);
        return sheet;
    }

    /**
     * Create sheet to work sheet.
     *
     * @param name the name
     * @return the to work sheet
     */
    public ToWorkSheet createSheet(String name) {
        ToWorkSheet sheet = new ToWorkSheet(this, this._wb, name);
        this.sheets.add(sheet);
        return sheet;
    }


    /**
     * Write.
     *
     * @param filePath the file path
     * @throws IOException the io exception
     */
    public void write(@NonNull String filePath) throws IOException {
        String fp = this.type.translateFileName(filePath);
        File file = new File(fp);
        Files.createDirectories(file.getParentFile().toPath());

        FileOutputStream fileOutputStream = new FileOutputStream(fp);
        this._wb.write(fileOutputStream);
    }

    public String getFileName(String name){
       if( this.type == null) { return name; }
       return type.translateFileName(name);
    }

    /**
     * Write.
     *
     * @param outputStream the output stream
     * @throws IOException the io exception
     */
    public void write(@NonNull OutputStream outputStream) throws IOException {
        this._wb.write(outputStream);
    }

    /**
     * Sheet size int.
     *
     * @return the int
     */
    public int sheetSize() {
        return this.sheets.size();
    }

    /**
     * Gets sheet at.
     *
     * @param idx the idx
     * @return the sheet at
     */
    public ToWorkSheet getSheetAt(int idx) {
        return this.sheets.get(idx);
    }

    /**
     * Gets sheet.
     *
     * @param name the name
     * @return the sheet
     */
    public ToWorkSheet getSheet(@NonNull String name) {
        return this.sheets.stream().filter(st -> name.equalsIgnoreCase(st.getName())).findFirst().orElseThrow(() -> new SheetNotFoundException("Not found sheet " + name));
    }



    private void __initSheet() {
        this.sheets = IntStream.range(0, this._wb.getNumberOfSheets()).mapToObj(this._wb::getSheetAt)
                .map(sheet -> new ToWorkSheet(this, sheet)).collect(Collectors.toList());
    }


    public synchronized CellStyle createStyle(@NonNull ToWorkCell cell) {
        CellStyle cellStyle = _styleMap.get(cell.getStyle());
        if (cellStyle != null) {
            return cellStyle;
        }

        cellStyle = cell.getStyle().convertOriginStyle(this._wb);

        this._styleMap.put(cell.getStyle(), cellStyle);
        return cellStyle;
    }
}
