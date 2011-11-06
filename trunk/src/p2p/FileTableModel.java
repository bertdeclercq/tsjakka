/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Gebruiker
 */
public class FileTableModel extends AbstractTableModel implements Observer {
    
    private final String[] COLUMNAMES = {"File name", "File size (MB)"};
    private DomeinController dc;

    public FileTableModel(DomeinController dc) {
        this.dc = dc;
        dc.addObserver(this);
    }
    
    
    
    public String getColumnName(int kolom){
        return COLUMNAMES[kolom];
    }

    @Override
    public int getRowCount() {
        return dc.getSharedTsjakkaMapSize();
    }

    @Override
    public int getColumnCount() {
        return COLUMNAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex)
        {
            case 0 : return dc.getFileName(rowIndex);
            case 1 : return dc.getFileSize(rowIndex);
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.fireTableDataChanged();
    }
    
}
