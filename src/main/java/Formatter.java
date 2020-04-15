package main.java;

import javafx.scene.control.TableColumn;


public class Formatter implements Formats {
    public String newRow, oldRow;

    @Override
    public void formatStockFromCellEdit(TableColumn.CellEditEvent editedCell) {
        String oldValue = "-" + editedCell.getOldValue().toString().replace(" ", "-") + "-";
        String newValue = "-" + editedCell.getNewValue().toString().replace(" ", "-") + "-";
        String oldRow = (editedCell.getRowValue().toString()).replace(" ", "-").replace("-in-", "-");
        String newRow = oldRow.replaceAll(oldValue, newValue);
        this.newRow = newRow;
        this.oldRow = oldRow;
    }

    public void formatStockForConfirmOrder(String item) {

    }

    public String stockFormatter(String oldString) {
        String newString;
        String[] output = oldString.split(" ");
        String dim = output[3].replaceAll("x", " x ") + " in";
        newString = "(" + output[0] + ") " + output[1] + " " + output[2] + " [" + dim + "] - $" + output[5];
        return newString;
    }
}

/*
0: ID
1: Metal
2: Type
3: Dimensions
4: Quantity
5: Price
 */
