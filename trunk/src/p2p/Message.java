/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.Serializable;

/**
 *
 * @author Gebruiker
 */
public class Message implements Serializable {
    
    private String tag;
    private boolean onlineMessage = false, signOutMessage = false;
    
    private static String ONLINE = "<online>",
                          SIGNOUT = "<signout>";
    
    
    public Message(String tag)
    {
        this.tag = tag;
    }
            
    public void checkStatusTag()
    {
        if(tag.equalsIgnoreCase(ONLINE))
            setOnlineMessage(true);
        if(tag.equalsIgnoreCase(SIGNOUT))
            setSignOutMessage(true);
    }

    public boolean isOnlineMessage() {
        return onlineMessage;
    }

    public void setOnlineMessage(boolean onlineMessage) {
        this.onlineMessage = onlineMessage;
    }

    public boolean isSignOutMessage() {
        return signOutMessage;
    }

    public void setSignOutMessage(boolean signOutMessage) {
        this.signOutMessage = signOutMessage;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        if (tag.startsWith("<") && tag.endsWith(">"))
            this.tag = tag;
        else
            throw new IllegalArgumentException("Tag is not in the right format!");
    }
    
    
    
}
