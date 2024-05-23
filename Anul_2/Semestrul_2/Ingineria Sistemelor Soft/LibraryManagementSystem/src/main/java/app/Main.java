package app;

import app.controller.LogInController;
import app.domain.BookRental;
import app.domain.Librarian;
import app.repository.*;
import app.repository.DBRepositories.BookDBRepository;
import app.repository.DBRepositories.ClientDBRepository;
import app.repository.DBRepositories.LibrarianDBRepository;

import app.repository.hibernate.*;
import app.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


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
        IBookRentalRepository brr = new BookRentalHibernateRepository();
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