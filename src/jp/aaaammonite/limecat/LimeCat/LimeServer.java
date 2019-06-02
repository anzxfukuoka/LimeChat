package jp.aaaammonite.limecat.LimeCat;

import SimpleClientServer.Server;
import SimpleClientServer.User;

public class LimeServer {

    static int port = 1707;
    static Server s = null;

    public static void main(String[] args) {
        s = new Server(port){
            @Override
            public void onConnect(User user) {
                super.onConnect(user); //<-это нужно обязательно

                System.out.println("новый юзер: " + "(" + user + ")");
                s.broadcastMessage("новый юзер: " + "(" + user + ")\n");
                System.out.println("онлайн:" + s.getUsers().size());

                s.sendMessage(user, "добро пожаловать. снова");

            }

            @Override
            public void onDisconect(User user) {
                super.onDisconect(user); //<-это нужно обязательно

                System.out.println("(" + user + ")" + " дисконекнулся");
                s.broadcastMessage("(" + user + ")" + " дисконекнулся");
                System.out.println("онлайн:" + s.getUsers().size());
            }

            @Override
            public void onMessage(User user, String data) {
                if (data.isEmpty()) return;
                System.out.println("(" + user + ")" + data);
                s.broadcastMessage("(" + user + ")" + data);
            }
        };
        s.start();
    }
}
