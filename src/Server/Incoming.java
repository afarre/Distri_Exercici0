package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Incoming extends Thread{
    private int port;
    private int numServers;
    private int num;
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private boolean readType;

    public Incoming(int port) {
        this.port = port;
        this.numServers = numServers;
        num = 0;
        setType();
    }

    private void setType() {
        boolean configured = true;
        do {
            System.out.println("Select type: Read or Write:");
            Scanner sc = new Scanner(System.in);
            String type = sc.nextLine();
            if (type.compareToIgnoreCase("read") == 0){
                readType = true;
                configured = false;
                System.out.println("Read type selected.");
            }else if (type.compareToIgnoreCase("write") == 0){
                readType = false;
                configured = false;
                System.out.println("Write type selected.");
            }
        }while (configured);
    }


    @Override
    public void run() {
        try {
            //creem el nostre socket
            ServerSocket serverSocket = new ServerSocket(port);
            //esperem a la conexio d'algun usuari dins d'un bucle infinit. A cada usuari li crearem un nou servidor dedicat
            Socket sClient = serverSocket.accept();
            System.out.println("Post accept");
            diStream = new DataInputStream(sClient.getInputStream());
            doStream = new DataOutputStream(sClient.getOutputStream());
            while (true){
                String request = diStream.readUTF();
                readRequest(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readRequest(String request) throws IOException {
        System.out.println("Got this request in Server: " + request);
      //  doStream.writeUTF("Hello server. Welcome to the net.");
        switch (request){
            case "WORK":
                System.out.println("got work");
                work();
                break;
            case "Connected":
                doStream.writeUTF("Confirmed");
                break;
          }
    }

    private void work() {
        try {
            if (readType){
                num = diStream.readInt();
                System.out.println("Reading number: " + num);
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(1000);
                    System.out.println("Done sleeping 1");
                }
            }else {
                num = diStream.readInt();
                System.out.println("Reading number: " + num);
                for (int i = 0; i < 10; i++) {
                    num++;
                    System.out.println("Updating number: " + num);
                    Thread.sleep(1000);
                    System.out.println("Done sleeping 2");
                }
            }
            doStream.writeInt(num);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
