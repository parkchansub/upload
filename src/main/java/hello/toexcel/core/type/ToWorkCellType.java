package hello.toexcel.core.type;

/**
 * The enum To work cell type.
 */
public enum ToWorkCellType {

    /**
     * Title to work cell type.
     */
    TITLE,
    /**
     * Value to work cell type.
     */
    VALUE,
    /**
     * Custom to work cell type.
     */
    CUSTOM;


    /**
     * Is title boolean.
     *
     * @return the boolean
     */
    public boolean isTitle(){
        return TITLE.equals(this);
    }
}
