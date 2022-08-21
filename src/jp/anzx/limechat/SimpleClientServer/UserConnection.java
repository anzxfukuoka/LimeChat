package jp.anzx.limechat.SimpleClientServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class UserConnection {

    OutputStream os;
    InputStream is;

    Server server;

    Socket socket;
    String addr;

    String roomID;

    public void setRoomID(String roomID){
        this.roomID = roomID;
    }

    public String getRoomID(){
        return this.roomID;
    }

    public UserConnection(Server server, Socket socket){
        this.server = server;
        this.socket = socket;

        addr = socket.getRemoteSocketAddress().toString();

        new UserThread().start();
    }

    public void sendData(byte[] data){
        try {
            os = socket.getOutputStream();
            os.write(data);
            os.flush();
        } catch (IOException e) {
            //e.printStackTrace();
            dissconect();
        }
    }

    public void getData(){
        try {
            is = socket.getInputStream();
            byte buf[] = new byte[64*1024];
            int r = is.read(buf);

            String data = new String(buf, 0, r);//тут какая-то дич
            server.onMessage(getUser(), data);

        } catch (IOException e) {
            //e.printStackTrace();
            dissconect();
        }
    }

    //бан
    public void dissconect(){
        try {
            socket.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }


    class UserThread extends Thread{
        @Override
        public void run() {

            server.onConnect(getUser());
            while (!socket.isClosed()){
                getData();
            }
            server.onDisconect(getUser());
        }
    }

    public UserConnection getUser(){
        return this;
    }

    @Override
    public String toString() {
        return addr;
    }
}
