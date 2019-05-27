package SimpleClientServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements Listener{

    OutputStream os;
    InputStream is;

    Socket serversocket;
    Socket socket;

    int port;
    String addr;

    public Client(String addr, int port){
        this.addr = addr;
        this.port = port;
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
    public void onMessage(User user, String data) {//USER == NULL

    }

    @Override
    public void onConnect(User user) {//USER == NULL

    }

    @Override
    public void onDisconect(User user) {//USER == NULL

    }

    /*
     *
     *
     * */

    class ClientThread extends Thread{
        @Override
        public void run() {
            onConnect(null);
            while (!serversocket.isClosed()){
                getData();
            }
            onDisconect(null);
        }
    }
}
