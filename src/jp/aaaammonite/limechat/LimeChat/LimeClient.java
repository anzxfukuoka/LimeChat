package jp.aaaammonite.limechat.LimeChat;

import SimpleClientServer.Client;
import SimpleClientServer.UserConnection;

import java.util.Scanner;

public class LimeClient {

    static int port = 1707;
    static Client c = null;
    static String username = "ANZX";

    static Scanner scan = null;



    public static void main(String[] args) {

        System.out.println("+++++++++++WELCOME TO THE LIMECHAT++++++++++++");

        scan = new Scanner(System.in);

        System.out.print("введите ник: ");
        username = scan.nextLine();

        System.out.print("введите адрес чата: ");
        String addr = scan.nextLine();

        c = new Client(addr, port){
            @Override
            public void onMessage(UserConnection userConnection, String data) {//user == null
                if(data.isEmpty()) return;
                System.out.println(data);
                //c.sendData(data.getBytes());
            }

            @Override
            public void onConnect(UserConnection userConnection) {
                //эту стремную лабуду стоит пофиксить хендлером в класск client
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            //System.out.print(username + ": ");
                            String msg = scan.nextLine();
                            msg = username + ": " + msg;
                            c.sendData(msg.getBytes());
                        }
                    }
                }).start();

            }

            @Override
            public void onDisconect(UserConnection userConnection) {
                super.onDisconect(userConnection);
                System.out.println("сервер " + addr + " оффлайн");
            }
        };

        c.connect();

    }
}
