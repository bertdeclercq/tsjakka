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
    
    private final String[] COLUMNAMES = {"File name", "File size", "Owned"};

    @Override
    public int getRowCount() {
        //Hier moet dan iets komen van onze map vol files. fileMap.size() of zoiets.
       // throw new UnsupportedOperationException("Not supported yet.");
        return 4;
    }

    @Override
    public int getColumnCount() {
        return COLUMNAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //TODO Implementeren
        switch (columnIndex)
        {
            case 0 : return "case0";
            case 1 : return "case1";
            case 2 : return "case2";
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.fireTableDataChanged();
    }
    
}