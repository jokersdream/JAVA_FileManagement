
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Server {
    
    public void runServer() {
        try {
            ServerSocket s = new ServerSocket(12345);
            System.out.println("Waiting for connection:");
            while (true) {
                Runnable r = new ServerThread(s);
                Thread t = new Thread(r);
                t.start();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    class ServerThread extends Thread implements Runnable {

        private ServerSocket server;
        private Socket connection;
        private ObjectOutputStream output;
        private ObjectInputStream input;
        private int counter = 1;

        public ServerThread(ServerSocket i) {
            server = i;
            try {
                connection = server.accept();
                System.out.println("\nConnection " + counter + " received from: "
                    + connection.getInetAddress().getHostName());
                this.getStreams();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }

        public void run() {
            try {
                this.processConnection();
            } catch (IOException ex) {
                System.out.println("\nServer " + getName() + " terminated connection");
            } finally {
                counter++;
            }
        }

        private void getStreams() throws IOException {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            System.out.println("Got I/O streams");
        }

        private void processConnection() throws IOException {
            String message = "Connection successful";
            sendData(message);
            do {
                try {
                    message = (String) input.readObject();
                    System.out.println("\nCLIENT(" + getName() + ") " + message);
                } catch (ClassNotFoundException classNotFoundException) {
                    System.out.println("\nUnknown object type received");
                }
            } while (!message.equals("CLIENT>>> TERMINATE"));
            closeConnection();
        }

        private void closeConnection() {
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
}
