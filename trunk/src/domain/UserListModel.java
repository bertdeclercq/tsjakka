/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractListModel;

/**
 *
 * Provides the capability to create a listmodel and fill the gui with data from DomeinController.
 */
public class UserListModel extends AbstractListModel implements Observer {
    
    private DomeinController dc;

    /**
     * Initializes a newly created Filter object.
     * @param dc The current used domeincontroller in the program
     */
    public UserListModel(DomeinController dc) {
        this.dc = dc;
        dc.addObserver(this);
    }

    /**
     * Returns the size of the usermap
     * @return the size of the usermap.
     */
    @Override
    public int getSize() {
        return dc.getUserMapSize();
    }

    /**
     * Returns the specified element at the given index.
     * @param index The index of the list
     * @return the username of the user.
     */
    @Override
    public Object getElementAt(int index) {
       return dc.getUserNameUser(index);
        
    }
    /**
     * Updates the userlist when a users leaves or enters the program. 
     */
    @Override
    public void update(Observable o, Object arg) {
       this.fireContentsChanged(this, 0, dc.getUserMapSize());
    }
    
}
