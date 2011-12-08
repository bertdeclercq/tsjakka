package domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The broadcaster class broadcasts every 3 seconds whether the user is online or not. When he is online a list of files he wants to share is broadcasted.
 */
public class Broadcaster implements Runnable {

    private MulticastSocket multiSocket;
    private InetAddress multiGroup;
    private InetAddress ownIp;
    private DatagramPacket multiPacket;
    private boolean flag = false;

    /**
     * Creates a broadcast whether the user is online or not specified by the flag.
     * 
     * @param flag specifies if the user wants to share files or not
     */
    public Broadcaster(boolean flag) {
        this.flag = flag;
    }

    /**
     * Sets a boolean whether the user want to share files or not.
     * 
     * @param flag specifies if the user wants to share files or not
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    /**
     * Every 3 seconds a message will be broadcasted whether the user is online or not. If the user is online, he will broadcast a list of the files he's sharing.
     */
    @Override
    public void run() {
        try {
            multiSocket = new MulticastSocket(4446);
            multiGroup = InetAddress.getByName("230.0.0.1");
            ownIp = InetAddress.getLocalHost();
            multiSocket.joinGroup(multiGroup);
            do {
                if (flag) {
                    sendOnlineMessage();
                } else {
                    sendSignoutMessage();
                }
                Thread.sleep(1000);
            } while (true);
        } catch (InterruptedException ex) {
            Logger.getLogger(Broadcaster.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Broadcaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendOnlineMessage() throws IOException {
        Message message;
        byte[] buf = new byte[65535];
        ByteArrayOutputStream b_out = new ByteArrayOutputStream();
        ObjectOutputStream o_out = new ObjectOutputStream(b_out);
//        SharedFiles.getInstance().setIp(ownIp.getHostAddress());
        message = new Message("<online>", ownIp.getHostAddress().toString(), ownIp.getHostName(), SharedFiles.getInstance().getSharedList());
        o_out.writeObject(message);
        buf = b_out.toByteArray();
        multiPacket = new DatagramPacket(buf, buf.length, multiGroup, 4446);
        multiSocket.send(multiPacket);
        b_out.close();

    }

    private void sendSignoutMessage() throws IOException {
        Message message;
        byte[] buf = new byte[65535];
        ByteArrayOutputStream b_out = new ByteArrayOutputStream();
        ObjectOutputStream o_out = new ObjectOutputStream(b_out);
        message = new Message("<signout>", ownIp.getHostAddress().toString());
        o_out.writeObject(message);
        buf = b_out.toByteArray();
        multiPacket = new DatagramPacket(buf, buf.length, multiGroup, 4446);
        multiSocket.send(multiPacket);
        b_out.close();
    }
}
