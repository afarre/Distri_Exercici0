package Xarxa;

import java.util.ArrayList;

public class Manager {
    private int num;
    private ArrayList<Logic> serverList;
    private int serversReady;
    private int numServers;


    public Manager(int port, int numServers) {
        serversReady = 0;
        num = 0;
        this.numServers = numServers;
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
                logic.sendWork();
            }

            while (true){
                for (Logic logic :serverList) {
                    int newNum = logic.sendNum(num);
                    System.out.println("Got new number update: " + newNum);
                    this.num = newNum;
                }
            }
        }
    }
}
