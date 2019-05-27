package SimpleClientServer;

public interface Listener {
    public void onMessage(User user, String data);
    public void onConnect(User user);
    public void onDisconect(User user);
}
