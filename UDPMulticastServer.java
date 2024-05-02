import java.util.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPMulticastServer{
public static void sendUDPMessage(String message,String ipAddress, int port) throws IOException {
DatagramSocket socket = new DatagramSocket();
InetAddress group = InetAddress.getByName (ipAddress) ;
byte[] msg = message.getBytes ();
DatagramPacket packet = new DatagramPacket (msg, msg.length, group, port);
socket.send(packet) ;
socket.close();
}

public static void main(String[] args) throws IOException {

Scanner sc = new Scanner (System.in);
String msg;

System.out.println("To exit, enter: \"EXIT\"");

System.out.println("To terminate all clients, enter: \"Emergency.Terminate all communications.\" ");

System.out.print ("Enter message: ");

msg = sc.nextLine();

while(!msg.equals("EXIT")) {

sendUDPMessage(msg, "230.0.0.0", 4321);

if (msg.equals("Emergency. Terminate all communications.")) break;

System.out.print ("Enter message: ");

msg = sc.nextLine();
}
}
}