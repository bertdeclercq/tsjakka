/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author Jimmy
 */
public class MulticastClient {

  private static MulticastSocket socket, socket2;
  private static InetAddress group;

    public static void main(String []args){
        try{
     socket = new MulticastSocket(4446);
     socket2 = new MulticastSocket(4447);
       group = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(group);
        boolean ontvangen = false;

        DatagramPacket packet;
         
            byte[] buf = new byte[256];
            String message = "testing";
            buf = message.getBytes();
            packet = new DatagramPacket(buf, buf.length, group, 4446);
            
            socket.send(packet);
            System.out.println("sent");
            
            
            do{
            socket2.receive(packet);
 
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println(""+received);
            
            }while (true);
            
        
        

    }
   
    catch(IOException e

    
        )
    {e.printStackTrace();
    }
        finally{
    
    socket.close ();}}
}
