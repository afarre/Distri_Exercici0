package Xarxa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

public class Logic extends Thread{
    private DataOutputStream doStream;
    private DataInputStream diStream;
    private Socket socket;
    private int port;
    private Manager parent;


    public Logic(int port, Manager manager) {
        this.port = port;
        parent = manager;
    }

    @Override
    public void run() {
        // Averiguem quina direccio IP hem d'utilitzar
        InetAddress iAddress;
        try {
            iAddress = InetAddress.getLocalHost();
            String IP = iAddress.getHostAddress();
            System.out.println("Connecting to port: " + port);

            socket = new Socket(String.valueOf(IP), port);
            doStream = new DataOutputStream(socket.getOutputStream());
            diStream = new DataInputStream(socket.getInputStream());
            doStream.writeUTF("Connected");
            if (diStream.readUTF().equals("Confirmed")){
                parent.ready();
            }
        } catch (ConnectException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int sendNum(int num) {
        try {
            doStream.writeInt(num);
            return diStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void sendWork() {
        try {
            doStream.writeUTF("WORK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
