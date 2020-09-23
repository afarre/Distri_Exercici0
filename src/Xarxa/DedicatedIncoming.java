package Xarxa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DedicatedIncoming extends Thread{
    private DataInputStream diStream;
    private DataOutputStream doStream;
    private Socket sClient;

    public DedicatedIncoming(Socket sClient) {
        this.sClient = sClient;
    }

    @Override
    public void run() {
        try {
            diStream = new DataInputStream(sClient.getInputStream());
            doStream = new DataOutputStream(sClient.getOutputStream());
            while (true){
                //System.out.println("Waiting for a request");
                String request = diStream.readUTF();
                readRequest(request);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readRequest(String request) throws IOException {
        System.out.println("Got this request in Xarxa: " + request);
        doStream.writeUTF("Hello server. Welcome to the net.");
    }
}
