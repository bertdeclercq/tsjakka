/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jimmy
 */
public class NuNeGoeieClient_1 extends Thread {

    private static MulticastSocket socket;
    private static InetAddress group;
    private static InetAddress ownAdress, receivedFromAddress;
    private static DatagramPacket packet;
    private static String eigenIp;
    private static final int PORT = 4446;
    private static List<String> stringList;

    public static void main(String[] args) {
        sendBroadcast();
        while (true) {
            receiveAnswer();
        }
    }

    public static void sendBroadcast() {
        try {
            socket = new MulticastSocket(PORT);
            group = InetAddress.getByName("230.0.0.1");
            socket.joinGroup(group);

            ownAdress = InetAddress.getByName("192.168.1.169");
            eigenIp = "/" + ownAdress.getHostAddress().toString();
            byte[] buf = new byte[256];
            String message = eigenIp;
            buf = message.getBytes();

            packet = new DatagramPacket(buf, buf.length, group, PORT);
            socket.send(packet);

            System.out.println("Broadcast sent, waiting for responses...");

        } catch (IOException ex) {
            Logger.getLogger(NuNeGoeieClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void receiveAnswer() {
        try {
            socket.receive(packet);
        } catch (IOException ex) {
            Logger.getLogger(NuNeGoeieClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        String received = new String(packet.getData(), 0, packet.getLength());

        // stringList = Arrays.asList(received.split(" "));
        String token[] = received.split(" ");

        if (!token[0].equals(eigenIp)) {
            System.out.println("Answer received.");
            System.out.println(token[0] + " is aangemeld");
            try {
                receivedFromAddress = InetAddress.getByName(token[0].substring(1, token[0].length()));
            } catch (UnknownHostException ex) {
                Logger.getLogger(NuNeGoeieClient.class.getName()).log(Level.SEVERE, null, ex);
            }


            if (token.length < 2) {
                stuurOnlineStatusBijNieuweAanmelding();
            }
        }


    }

    private static void stuurOnlineStatusBijNieuweAanmelding() {
        Thread thr = new Thread();
        try {
            System.out.println("3 seconden wachten...");
            thr.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(NuNeGoeieClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("begin stuur onlinestatus");
        
        byte[] buf = new byte[256];
        String message = eigenIp + " ookOnline";
        buf = message.getBytes();

        packet = new DatagramPacket(buf, buf.length, receivedFromAddress, PORT);
        try {
            socket.send(packet);

        } catch (IOException ex) {
            Logger.getLogger(NuNeGoeieClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("online status teruggestuurd.");
    }
}
