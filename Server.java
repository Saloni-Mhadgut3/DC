import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

// Server class to handle group chat
class Server {
    private final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        new Server().startServer(8888);
    }

    public void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);

                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast message to all clients
    public synchronized void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(sender.getClientName() + ": " + message);
            }
        }
    }

    // Unicast message to a specific client
    public synchronized void unicastMessage(String message, String recipient, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equalsIgnoreCase(recipient)) {
                client.sendMessage(sender.getClientName() + " (private): " + message);
                return;
            }
        }

        sender.sendMessage("User " + recipient + " not found.");
    }
}

// ClientHandler class to handle individual clients
class ClientHandler implements Runnable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private String clientName;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.clientName = "User" + System.currentTimeMillis();
    }

    public String getClientName() {
        return clientName;
    }

    // Send a message to the client
    public void sendMessage(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                if (message.startsWith("/broadcast ")) {
                    Server server = getServer();
                    server.broadcastMessage(message.substring(11), this);
                } else if (message.startsWith("/unicast ")) {
                    String[] parts = message.substring(9).split(" ", 2);
                    Server server = getServer();
                    server.unicastMessage(parts[1], parts[0], this);
                } else {
                    sendMessage("Invalid command. Use /broadcast or /unicast.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    // Get the reference to the server
    private Server getServer() {
        return (Server) Thread.currentThread();
    }

    // Close the client connection
    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Client class for testing
class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8888);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Start a thread to read messages from the server
            new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Send messages to the server
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while ((input = consoleReader.readLine()) != null) {
                writer.write(input + "\n");
                writer.flush();
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

// Server class to handle group chat
class Server {
    private final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        new Server().startServer(8888);
    }

    public void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket);

                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast message to all clients
    public synchronized void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(sender.getClientName() + ": " + message);
            }
        }
    }

    // Unicast message to a specific client
    public synchronized void unicastMessage(String message, String recipient, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client.getClientName().equalsIgnoreCase(recipient)) {
                client.sendMessage(sender.getClientName() + " (private): " + message);
                return;
            }
        }

        sender.sendMessage("User " + recipient + " not found.");
    }
}

// ClientHandler class to handle individual clients
class ClientHandler implements Runnable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private String clientName;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.clientName = "User" + System.currentTimeMillis();
    }

    public String getClientName() {
        return clientName;
    }

    // Send a message to the client
    public void sendMessage(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                if (message.startsWith("/broadcast ")) {
                    Server server = getServer();
                    server.broadcastMessage(message.substring(11), this);
                } else if (message.startsWith("/unicast ")) {
                    String[] parts = message.substring(9).split(" ", 2);
                    Server server = getServer();
                    server.unicastMessage(parts[1], parts[0], this);
                } else {
                    sendMessage("Invalid command. Use /broadcast or /unicast.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    // Get the reference to the server
    private Server getServer() {
        return (Server) Thread.currentThread();
    }

    // Close the client connection
    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Client class for testing
class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8888);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Start a thread to read messages from the server
            new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Send messages to the server
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while ((input = consoleReader.readLine()) != null) {
                writer.write(input + "\n");
                writer.flush();
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
