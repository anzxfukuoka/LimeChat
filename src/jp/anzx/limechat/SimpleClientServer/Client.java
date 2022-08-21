package jp.anzx.limechat.SimpleClientServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements ConnectionListener {

    public static final String DEFAULT_ROOM = "default_room";
    OutputStream os;
    InputStream is;

    Socket serversocket;
    Socket socket;

    int port;
    String addr;

    String roomID = DEFAULT_ROOM;

    public Client(String addr, int port){
        this.addr = addr;
        this.port = port;
    }

    public void setRoomID(String roomID)
    {
        this.roomID = roomID;
    }

    //подключится к серверу
    public void connect(){
        try {
            serversocket = new Socket(addr, port);

            new ClientThread().start();

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("ошибка подключения");
            dissconect();
        }
    }

    //отключится
    public void dissconect(){
        try {
            serversocket.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void getData(){
        try {
            is = serversocket.getInputStream();
            byte buf[] = new byte[64*1024];
            int r = is.read(buf);

            String data = new String(buf, 0, r);
            onMessage(null, data);

        } catch (IOException e) {
            //e.printStackTrace();
            dissconect();
        }
    }

    public void sendData(byte[] data){
        try {
            os = serversocket.getOutputStream();
            os.write(data);
            os.flush();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("чет пошло не так...");
            dissconect();
        }
    }

    /*
     *
     *
     * */

    @Override
    public void onMessage(UserConnection userConnection, String data) {//USER == NULL

    }

    @Override
    public void onConnect(UserConnection userConnection) {//USER == NULL

    }

    @Override
    public void onDisconect(UserConnection userConnection) {//USER == NULL

    }

    /*
     *
     *
     * */

    class ClientThread extends Thread{
        @Override
        public void run() {

            onConnect(null);

            sendData(("/set_room_id " + roomID).getBytes());

            while (!serversocket.isClosed()){
                getData();
            }
            onDisconect(null);
        }
    }
}
