
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private final String chatServer;
    private Socket client;

    public Client(String host) {
        chatServer = host;
    }

    public void runClient() {
        Runnable r = () -> {
            try {
                connectToServer();
                getStreams();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void connectToServer() throws IOException {
        System.out.println("Attempting connection\n");
        client = new Socket(InetAddress.getByName(chatServer), 12345);
        System.out.println("Connected to: "
                + client.getInetAddress().getHostName());
    }

    public void getStreams() throws IOException {
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();
        input = new ObjectInputStream(client.getInputStream());
        System.out.println("\nGot I/O streams\n\nCLIENT>>> Connection successful");
    }

    public void processConnection(String message) {
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        System.out.println("\n" + message);
    }

    public void closeConnection() {
        System.out.println("\nClosing connection");
        try {
            output.close();
            input.close();
            client.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
