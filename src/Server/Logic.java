package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

public class Logic {
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private Socket socket;
    private int port;


    public Logic(int port) {
        this.port = port;
        createOutcomeConnection();
    }

    private void createOutcomeConnection() {
        while (true) {
            // Averiguem quina direccio IP hem d'utilitzar
            InetAddress iAddress;
            try {
                iAddress = InetAddress.getLocalHost();
                String IP = iAddress.getHostAddress();

                socket = new Socket(String.valueOf(IP), port);
                doStream = new DataOutputStream(socket.getOutputStream());
                diStream = new DataInputStream(socket.getInputStream());
                doStream.writeUTF("Hello from a server!");
                String answer = diStream.readUTF();
                System.out.println("Xarxa answered with: " + answer);
                while (true);
            } catch (ConnectException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
