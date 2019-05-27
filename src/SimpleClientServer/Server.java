package SimpleClientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Listener{

    boolean alive = false;

    int port;
    ServerSocket serversocket;

    ArrayList<User> users;

    public Server(int port){
        this.port = port;

        users = new ArrayList<>();

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
    public void onMessage(User user, String data) {

    }

    @Override
    public void onConnect(User user) {
        users.add(user);

    }

    @Override
    public void onDisconect(User user) {
        users.remove(user);
    }

    /*
     *
     *
     * */

    //
    public void sendMessage(User user, String msg){
        user.sendData(msg.getBytes());
    }

    //сообщение всем пользователям
    public void broadcastMessage(String msg){
        for (int i = 0; i < users.size(); i++) {
            users.get(i).sendData(msg.getBytes());
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
                    new User(getServer(), clientSocket);//юзер отправит серверу onConnected

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

    public ArrayList<User> getUsers(){
        return users;
    }

    //костыль
    public Server getServer(){
        return this;
    }

}
