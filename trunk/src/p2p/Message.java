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
    
    private String tag, typeObject;
    private Object content;
    private String ownAddress, hostName;
    private boolean onlineMessage = false, signOutMessage = false;
    
    private static String ONLINE = "<online>",
                          SIGNOUT = "<signout>";

    public Message(String tag, String ownAddress, String hostName, Object content) {
        this.tag = tag;
        this.ownAddress = ownAddress;
        this.hostName = hostName;
        this.content = content;
        typeObject = content.getClass().getName();
    }
    
    public Message() {
        
    }

    public Message(String tag) {
        this.tag = tag;

    }
    
    public Message(String tag, Object content)
    {
        this.tag = tag;
        this.content = content;
        this.typeObject = content.getClass().getName(); 
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

    public String getTypeObject() {
        return typeObject;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getOwnAddress() {
        return ownAddress;
    }

    public void setOwnAddress(String ownAddress) {
        this.ownAddress = ownAddress;
    }
    
     
    
    
    
    
    
}
