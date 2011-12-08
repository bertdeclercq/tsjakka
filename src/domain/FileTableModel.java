package domain;

import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;

/**
 * Provides the capability to create a tablemodel and fill the gui with data from DomeinController.
 */
public class FileTableModel extends AbstractTableModel implements Observer {

    private final String COLUMNAMES[] = {"Filename", "File size (MB)"};
    private DomeinController dc;

    /**
     * Creates a FileTableModel to add data it gets from DomeinController to the gui.
     * 
     * @param dc an instance from DomeinController
     */
    public FileTableModel(DomeinController dc) {
        this.dc = dc;
        dc.addObserver(this);
    }

    /**
     * Returns the name of the specified column.
     * 
     * @param column the columnnumber
     * 
     * @return the name of the column
     */
    @Override
    public String getColumnName(int column) {
        return COLUMNAMES[column];
    }

    /**
     * Returns the number of rows this FileTableModel contains.
     * 
     * @return the number of rows
     */
    @Override
    public int getRowCount() {
        return dc.getSharedTsjakkaMapSize();
    }

    /**
     * return the number of columns this FileTableModel contains.
     * 
     * @return the number of columns
     */
    @Override
    public int getColumnCount() {
        return COLUMNAMES.length;
    }

    /**
     * Returns the value from the FileTableModel at the specified row and column.
     * 
     * @param rowIndex the row number
     * @param columnIndex the column number
     * 
     * @return the value at the specified row and column
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return dc.getFileName(rowIndex);
            case 1:
                return Double.toString(dc.getFileSize(rowIndex));
        }
        return null;
    }

    /**
     * TODO WTF!
     * 
     * @param o
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
        this.fireTableDataChanged();
    }

    /**
     * Returns the ip address as a string associated with the file at the specified position.
     * 
     * @param index the position of the file from which you want to get the ip
     * 
     * @return the ip address as a string
     */
    public String getIp(int index) {
        return dc.getFileIp(index);
    }

    /**
     * Returns the class from the item at the specified position.
     * 
     * @param columnIndex the position from which you want to get the class
     * 
     * @return the class from the item at the specified position.
     */
    @Override
    public Class getColumnClass(int columnIndex) {
        Object o = getValueAt(0, columnIndex);
        if (o == null) {
            return Object.class;
        } else {
            return o.getClass();
        }
    }
}
