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
}
