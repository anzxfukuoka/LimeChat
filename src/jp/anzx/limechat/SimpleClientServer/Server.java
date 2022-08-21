package jp.anzx.limechat.SimpleClientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements ConnectionListener {

    boolean alive = false;

    int port;
    ServerSocket serversocket;

    protected ArrayList<UserConnection> userConnections;

    public Server(int port){
        this.port = port;

        userConnections = new ArrayList<>();

    }

    //вкл
    public void start(){
        new ServerThread().start();
    }
    //выкл
    public void stop(){
        alive = false;
    }

    /*
    *
    *
    * */

    @Override
    public void onMessage(UserConnection userConnection, String data) {

    }

    @Override
    public void onConnect(UserConnection userConnection) {
        userConnections.add(userConnection);

    }

    @Override
    public void onDisconect(UserConnection userConnection) {
        userConnections.remove(userConnection);
    }

    /*
     *
     *
     * */

    //
    public void sendMessage(UserConnection userConnection, String msg){
        userConnection.sendData(msg.getBytes());
    }

    //сообщение всем пользователям
    public void broadcastMessage(String msg){
        for (int i = 0; i < userConnections.size(); i++) {
            userConnections.get(i).sendData(msg.getBytes());
        }
    }

    class ServerThread extends Thread{

        @Override
        public void run() {
            alive = true;

            try {
                serversocket = new ServerSocket(port);

                while (alive){//можно дернуть через start/stop

                    Socket clientSocket = serversocket.accept();// accept() будет ждатьj
                    new UserConnection(getServer(), clientSocket);//юзер отправит серверу onConnected

                }

            } catch (IOException e) {
                //e.printStackTrace();
            }finally {
                try {
                    serversocket.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }


        }
    }

    public ArrayList<UserConnection> getUsers(){
        return userConnections;
    }

    //костыль
    public Server getServer(){
        return this;
    }

}
