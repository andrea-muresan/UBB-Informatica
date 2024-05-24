package app;

import app.controller.LogInController;
import app.repository.*;

import app.repository.hibernate.*;
import app.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private static IBookRepository br;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage LogInWindow) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/logIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        IBookRepository br = new BookHibernateRepository();
        IBookSetRepository bsr = new BookSetHibernateRepository();
        IBookBorrowRepository brr = new BookBorrowHibernateRepository();
        ILibrarianRepository lr = new LibrarianHibernateRepository();
        IClientRepository cr = new ClientHibernateRepository();

        Service srv = new Service(br, bsr, brr, lr, cr);

        LogInController logInController = fxmlLoader.getController();
        logInController.setService(srv);

        LogInWindow.setTitle("Travel Agency");
        LogInWindow.setScene(scene);
        LogInWindow.show();
    }

}