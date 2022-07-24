package SimpleClientServer;

public interface ConnectionListener {
    public void onMessage(UserConnection userConnection, String data);
    public void onConnect(UserConnection userConnection);
    public void onDisconect(UserConnection userConnection);
}
