import java.io.IOException;
import java.net.*;

public class GroupChat {
    private static final int PORT = 12345;
    private static final String GROUP_ADDRESS = "230.0.0.1";

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(GROUP_ADDRESS);
            MulticastSocket socket = new MulticastSocket(PORT);
            socket.joinGroup(group);

            Thread receiverThread = new Thread(() -> {
                try {
                    byte[] buffer = new byte[1000];
                    while (true) {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        String message = new String(packet.getData(), 0, packet.getLength());
                        System.out.println("Received from " + packet.getAddress().getHostAddress() + ": " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiverThread.start();

            Thread senderThread = new Thread(() -> {
                try {
                    DatagramSocket unicastSocket = new DatagramSocket();
                    while (true) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        String message = reader.readLine();
                        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), group, PORT);
                        socket.send(packet);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            senderThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
