package hello.toexcel.core.style;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * The type To work book font.
 */
@Builder
@EqualsAndHashCode
public class ToWorkBookFont {

    private String fontName;
    private Short fontHeight;
    private Short fontHeightInPoints;
    private Boolean italic;
    private Boolean strikeout;
    private Short color;
    private Short typeOffset;
    private Byte underline;
    private Integer charSet;
    private Short boldWeight;

    /**
     * Convert font font.
     *
     * @param wb the wb
     * @return the font
     */
    public Font convertFont(@NonNull Workbook wb){
        Font font = wb.createFont();

        if(fontName != null){
            font.setFontName(fontName);
        }
        if(fontHeight != null){
            font.setFontHeight(fontHeight);
        }
        if(fontHeightInPoints != null){
            font.setFontHeightInPoints(fontHeightInPoints);
        }
        if(italic != null){
            font.setItalic(italic);
        }
        if(strikeout != null){
            font.setStrikeout(strikeout);
        }
        if(color != null){
            font.setColor(color);
        }
        if(typeOffset != null){
            font.setTypeOffset(typeOffset);
        }
        if(underline != null){
            font.setUnderline(underline);
        }
        if(charSet != null){
            font.setCharSet(charSet);
        }
        if(boldWeight != null){
            font.setBoldweight(boldWeight);
        }
        return font;
    }
}
