
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server {
    private int counter = 1;    
    public void runServer() {
        try {
            ServerSocket s = new ServerSocket(12345);
            System.out.println("Waiting for connection:");
            while (true) {
                ServerThread temp = new ServerThread(s);
                temp.start();
                if (counter == 8) {
                    break;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    class ServerThread extends Thread{

        private ServerSocket server;
        private Socket connection;
        private ObjectOutputStream output;
        private ObjectInputStream input;

        public ServerThread(ServerSocket i) {
            server = i;
            try {
                connection = server.accept();
                System.out.println("\nConnection " + counter++ + " received from: "
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
                    System.out.println("\nCLIENT(" + this.getName() + ") " + message);
                } catch (ClassNotFoundException classNotFoundException) {
                    System.out.println("\nUnknown object type received");
                }
            } while (!message.equals("CLIENT>>> TERMINATE"));
            System.out.println("CLIENT>>> TERMINATE");
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
