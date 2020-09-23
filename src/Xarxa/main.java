package Xarxa;

public class main {
    public static void main(String[] args) {
        Incoming incoming = new Incoming(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        incoming.start();
    }
}
