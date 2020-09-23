package Xarxa;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Incoming extends Thread{
    private int port;
    private int numServers;
    private boolean allConnected;
    private ArrayList<DedicatedIncoming> servers;

    public Incoming(int port, int numServers) {
        this.port = port;
        this.numServers = numServers;
        allConnected = true;
        servers = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(port);
            while (allConnected){
                //esperem a la conexio d'algun usuari dins d'un bucle infinit. A cada usuari li crearem un nou servidor dedicat
                Socket sClient = serverSocket.accept();
                DedicatedIncoming dedicatedIncoming = new DedicatedIncoming(sClient);
                servers.add(dedicatedIncoming);
                dedicatedIncoming.start();
                allConnected = servers.size() != numServers;
                //tots els servers s'han connectat, hora de treballar
            }
            work();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void work() {
        System.out.println("All connected, work time!");
    }
}
