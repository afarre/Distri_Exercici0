package Xarxa;

import java.io.IOException;
import java.util.ArrayList;

public class Manager {
    private int port;
    private int num;

    private ArrayList<Logic> serverList;
    private int serversReady;
    private int numServers;


    public Manager(int port, int numServers) {
        serversReady = 0;
        num = 0;
        this.numServers = numServers;
        this.port = port;
        serverList = new ArrayList<>();

        for (int i = 0; i < numServers; i++){
            Logic logic = new Logic(port, this);
            logic.start();
            serverList.add(logic);
            port++;
        }
    }

    public void ready() {
        serversReady++;
        if (serversReady == numServers){
            System.out.println("All childs ready");
            for (Logic logic :serverList) {
                int newNum = logic.work(num);
                System.out.println("Got newNum:" + newNum);
                this.num = newNum;
            }
        }
    }

/*



    private void waitForWork() {
        while (true){
            try {
                doStream.writeUTF("Hello from a server!");
                String answer = diStream.readUTF();
                System.out.println("Xarxa answered with: " + answer);
                answer = diStream.readUTF();
                System.out.println("Answer: " + answer);
                if (answer.equals("WORK")){
                    work();
                }
                System.out.println("Told me to " + answer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

 */


}
