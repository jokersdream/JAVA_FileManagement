
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ObjectOutputStream output; 
    private ObjectInputStream input; 
    private ServerSocket server; 
    private Socket connection; 
    private int counter = 1; 

    public void runServer() {
        try {
            server = new ServerSocket(12345, 100); 
            while (true) {
                try {
                    waitForConnection(); 
                    getStreams(); 
                    processConnection(); 
                } catch (EOFException ex) {
                    System.out.println("\nServer terminated connection");
                } finally {
                    closeConnection(); 
                    counter++;
                } 
            } 
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } 
    } 

    private void waitForConnection() throws IOException {
        System.out.println("Waiting for connection\n");
        connection = server.accept(); 
        System.out.println("Connection " + counter + " received from: "
                + connection.getInetAddress().getHostName());
    }

    private void getStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush(); 
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("\nGot I/O streams\n");
    } 

    private void processConnection() throws IOException {
        String message = "Connection successful";
        sendData(message);
        do {
            try {
                message = (String) input.readObject(); 
                System.out.println("\n" + message);
            } catch (ClassNotFoundException classNotFoundException) {
                System.out.println("\nUnknown object type received");
            }
        } while (!message.equals("CLIENT>>> TERMINATE"));
    }

    private void closeConnection() {
        System.out.println("\nTerminating connection\n");
        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void sendData(String message) {
        try {
            output.writeObject("SERVER>>> " + message);//输出到客户端
            output.flush(); 
            System.out.println("SERVER>>> " + message);
        } catch (IOException ioException) {
            System.out.println("\nError writing object");
        }
    }
}
