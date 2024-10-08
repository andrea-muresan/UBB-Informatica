package app;

import app.controller.AppController;
import app.controller.LogInController;
import app.controller.SignUpController;
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
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;


        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IServices server = new ServicesRpcProxy(serverIP, serverPort);

        // logIn
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("logIn.fxml"));
        Parent LogInroot = loader.load();

        LogInController ctrl =
                loader.<LogInController>getController();
        ctrl.setServer(server);

        // signUP
        FXMLLoader signUploader = new FXMLLoader(
                getClass().getClassLoader().getResource("signUp.fxml"));
        Parent signUproot = signUploader.load();

        SignUpController signUpCtrl =
                signUploader.<SignUpController>getController();
        signUpCtrl.setService(server);

        signUpCtrl.setParentLogIn(LogInroot);

        ctrl.setSignUpController(signUpCtrl);
        ctrl.setParentSignUp(signUproot);

        // app
        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("app.fxml"));
        Parent croot = cloader.load();

        AppController appCtrl =
                cloader.<AppController>getController();
        appCtrl.setService(server);

        ctrl.setAppController(appCtrl);
        ctrl.setParentApp(croot);






        primaryStage.setTitle("MPP Travel Agency");
        primaryStage.setScene(new Scene(LogInroot, 600, 400));
        primaryStage.show();

    }
}