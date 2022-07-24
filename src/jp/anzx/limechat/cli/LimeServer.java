package jp.anzx.limechat.cli;

import jp.anzx.limechat.SimpleClientServer.Server;
import jp.anzx.limechat.SimpleClientServer.UserConnection;

public class LimeServer {

    static int port = 1707;
    static Server s = null;

    public static void main(String[] args) {
        s = new Server(port){
            @Override
            public void onConnect(UserConnection userConnection) {
                super.onConnect(userConnection); //<-это нужно обязательно

                System.out.println("новый юзер: " + "(" + userConnection + ")");
                s.broadcastMessage("новый юзер: " + "(" + userConnection + ")\n");
                System.out.println("онлайн:" + s.getUsers().size());

                s.sendMessage(userConnection, "добро пожаловать. снова");

            }

            @Override
            public void onDisconect(UserConnection userConnection) {
                super.onDisconect(userConnection); //<-это нужно обязательно

                System.out.println("(" + userConnection + ")" + " дисконекнулся");
                s.broadcastMessage("(" + userConnection + ")" + " дисконекнулся");
                System.out.println("онлайн:" + s.getUsers().size());
            }

            @Override
            public void onMessage(UserConnection userConnection, String data) {
                if (data.isEmpty()) return;
                System.out.println("(" + userConnection + ")" + data);
                s.broadcastMessage("(" + userConnection + ")" + data);
            }
        };
        s.start();
    }
}
