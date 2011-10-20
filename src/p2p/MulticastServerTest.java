/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;
import java.io.*;
import java.net.*;
import java.util.*;
 
public class MulticastServerTest {
 
    public static void main(String[] args) throws IOException {
 
        MulticastSocket socket = new MulticastSocket(4446);
        InetAddress address = InetAddress.getByName("230.0.0.1");
    socket.joinGroup(address);
 
        DatagramPacket packet;
        DatagramPacket sendPacket;
     
    
 
        byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            do{
            socket.receive(packet);
 
            String received = new String(packet.getData(), 0, packet.getLength());
            byte[] buf2 = new byte[256];
            String antwoord = "antwoord";
            buf2 = antwoord.getBytes();
            sendPacket = new DatagramPacket(buf2, buf2.length, packet.getAddress(), 4447);
            socket.send(sendPacket);
           
           
            
            }while (true);
    
 
    
    }
 
}