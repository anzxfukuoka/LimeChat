package jp.anzx.limechat.cli.server;

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

                if(data.startsWith("/set_room_id ")){
                    String roomId = data.split(" ")[1];
                    userConnections.get(userConnections.size() - 1).setRoomID(roomId);
                }

                System.out.println("(" + userConnection + ")" + data);

                //s.broadcastMessage("(" + userConnection + ")" + data);

                for (UserConnection otherConnection : userConnections) {
                    if(otherConnection.getRoomID().equals(userConnection.getRoomID())){
                        otherConnection.sendData(("(" + userConnection + ")" + data).getBytes());
                    }
                }
            }
        };
        s.start();
    }
}
