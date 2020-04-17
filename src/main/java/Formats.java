package main.java;

import javafx.scene.control.TableColumn;


/**
 * @author Sean Peters
 */
public interface Formats {
    void formatStockFromCellEdit(TableColumn.CellEditEvent editedCell);
}
