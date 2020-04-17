package main.java;

import javafx.scene.control.TableColumn;


/**
 * <b>Class that formats Strings</b>
 *
 * @author Sean Peters
 */
public class Formatter implements Formats {
    public String newRow, oldRow;

    /**
     * Formats a String for the Inventory page
     *
     * @param editedCell Cell that is being edited
     */
    @Override
    public void formatStockFromCellEdit(TableColumn.CellEditEvent editedCell) {
        String oldValue = "-" + editedCell.getOldValue().toString().replace(" ", "-") + "-";
        String newValue = "-" + editedCell.getNewValue().toString().replace(" ", "-") + "-";
        String oldRow = (editedCell.getRowValue().toString()).replace(" ", "-").replace("-in-", "-");
        String newRow = oldRow.replaceAll(oldValue, newValue);
        this.newRow = newRow;
        this.oldRow = oldRow;
    }

    /**
     * Formats a String for the Order Page
     *
     * @param oldString String to formatted
     * @return formatted String
     */
    public String stockFormatter(String oldString) {
        String newString;
        String[] output = oldString.split(" ");
        String dim = output[3].replaceAll("x", " x ") + " in";
        newString = "(" + output[0] + ") " + output[1] + " " + output[2] + " [" + dim + "] - $" + output[5];
        return newString;
    }
}
