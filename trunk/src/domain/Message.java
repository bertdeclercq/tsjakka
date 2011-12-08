package domain;

import java.io.Serializable;

/**
 * 
 * This is our own defined Messageprotocol to send messages over a network connection. 
 * It also contains certain attributes to define which user is sending the message.
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1;
    private String tag, typeObject;
    private Object content;
    private String ownAddress, hostName;
    private boolean onlineMessage = false, signOutMessage = false;
    private static String ONLINE = "<online>", SIGNOUT = "<signout>";
    
    /**
     * Initializes a newly created Message object. 
     * @param tag This tag defines which kind of message it is
     * @param ownAddress The address of the sender of the message
     */
    public Message(String tag, String ownAddress) {
        this.tag = tag;
        this.ownAddress = ownAddress;
    }
    
    /**
     * Initializes a newly created Message object.
     * @param tag This tag defines which kind of message it is
     * @param ownAddress The address of the sender of the message
     * @param hostName The hostname of the sender of the message
     * @param content An object that can be added to the message
     */
    public Message(String tag, String ownAddress, String hostName, Object content) {
        this(tag, ownAddress);
        this.hostName = hostName;
        this.content = content;
        typeObject = content.getClass().getName();
    }

    /**
     * Checks the tag of the message and sets the corresponding flag to true
     */
    public void checkStatusTag() {
        if (tag.equalsIgnoreCase(ONLINE)) {
            setOnlineMessage(true);
        }
        if (tag.equalsIgnoreCase(SIGNOUT)) {
            setSignOutMessage(true);
        }
    }

    /**
     * Returns if the message is an online message.
     * @return True if the message is an online message, false if not
     */
    public boolean isOnlineMessage() {
        return onlineMessage;
    }

    /**
     * Sets the flag of onlineMessage
     * @param onlineMessage boolean
     */
    public void setOnlineMessage(boolean onlineMessage) {
        this.onlineMessage = onlineMessage;
    }

    /**
     * Returns if the message is an offline message.
     * @return True if the message is an offline message, false if not
     */
    public boolean isSignOutMessage() {
        return signOutMessage;
    }

    /**
     * Sets the flag onlineMessage
     * @param signOutMessage boolean
     */
    public void setSignOutMessage(boolean signOutMessage) {
        this.signOutMessage = signOutMessage;
    }

    /**
     * Returns the tag
     * @return the tag of the message.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the value the tag.
     * @param tag Tag that has to be set.
     */
    public void setTag(String tag) {
        if (tag.startsWith("<") && tag.endsWith(">")) {
            this.tag = tag;
        } else {
            throw new IllegalArgumentException("Tag is not in the right format!");
        }
    }

    /**
     * Returns the type of the object.
     * @return attribute typeObject
     */
    public String getTypeObject() {
        return typeObject;
    }

    /**
     * Returns the content of the message
     * @return attribute content
     */
    public Object getContent() {
        return content;
    }

    /**
     * Sets the content of the message.
     * @param content The content to be set. 
     */
    public void setContent(Object content) {
        this.content = content;
    }

    /**
     * Returns the hostname of the sender of the message.
     * @return atribute hostname
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the hostname of the sender of the message.
     * @param hostName the hostname that has to be set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Returns the address of the sender of the message.
     * @return atttribute ownAddress
     */
    public String getOwnAddress() {
        return ownAddress;
    }

    /**
     * Sets the address of the sender of the message.
     * @param ownAddress 
     */
    public void setOwnAddress(String ownAddress) {
        this.ownAddress = ownAddress;
    }
}
