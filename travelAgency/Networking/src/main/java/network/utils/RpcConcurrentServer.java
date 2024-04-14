package app.network.utils;

import app.network.rpcprotocol.ClientRpcReflectionWorker;
import app.services.IServices;


import java.net.Socket;

public class RpcConcurrentServer extends AbsConcurrentServer {

    private IServices server;

    public RpcConcurrentServer(int port, IServices chatServer) {
        super(port);
        this.server = chatServer;
        System.out.println("App - RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket socket) {
        ClientRpcReflectionWorker worker = new ClientRpcReflectionWorker(server, socket);
        return new Thread(worker);
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}