package app;

import app.controller.AppController;
import app.controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.rpcprotocol.ServicesRpcProxy;
import services.IServices;

import java.io.IOException;
import java.util.Properties;

public class StartRpcClient extends Application {
    //private Stage primaryStage;
    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try{
            clientProps.load(StartRpcClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set.");
            clientProps.list(System.out);
        }
        catch(IOException e){
            System.err.println("Cannot find client.properties " + e);
            return;
        }

        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultPort;
        try{
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        }
        catch (NumberFormatException ex){
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultPort);
        }

        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IServices service = new ServicesRpcProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("log-in.fxml"));
        Parent root = loader.load();

        LogInController ctrl = loader.getController();
        ctrl.setServer(service);

        FXMLLoader mainLoader = new FXMLLoader(getClass().getClassLoader().getResource("main-view.fxml"));
        Parent mainRoot = mainLoader.load();

        AppController mainCtrl = mainLoader.getController();
        mainCtrl.setService(service);

        ctrl.setAppController(mainCtrl);
        ctrl.setParent(mainRoot);

        primaryStage.setTitle("Basketball ticketing system");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}