package view.admin;

import controller.Constants;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.DBManager;
import model.dao.StatusDaoHibenate;
import model.entityDB.StatusEntity;
import view.material.ProgressCircle;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CompanyStatusView extends GridPane {

    public CompanyStatusView() {

        setPadding(new Insets(25));
        setStyle("-fx-background-color: white");
        setAlignment(Pos.TOP_LEFT);

        add(new Label("Costi"), 0, 0);
        add(new Label("Ricavi"), 0, 1);
        add(new Label("Margine"), 0, 2);
        ProgressCircle circle0 = ProgressCircle.miniCircle(),
                       circle1 = ProgressCircle.miniCircle(),
                       circle2 = ProgressCircle.miniCircle();
        add(circle0, 1, 0);
        add(circle1, 1, 1);
        add(circle2, 1, 2);

        circle0.start();
        circle1.start();
        circle2.start();

        final double result[] = { 0.0 };
        Lock lock1 = new ReentrantLock(), lock2 = new ReentrantLock();
        new Thread(() -> {
            lock1.lock();
            DBManager.initHibernate();
            double value = ((StatusEntity) StatusDaoHibenate.getInstance().getById(Constants.costs)).getValore();
            DBManager.shutdown();
            result[0] -= value;
            lock1.unlock();
            Platform.runLater(() -> {
                getChildren().remove(circle0);
                add(new Label(Double.toString(value)), 1, 0);
            });
        }).start();

        new Thread(() -> {
            lock2.lock();
            DBManager.initHibernate();
            double value = ((StatusEntity) StatusDaoHibenate.getInstance().getById(Constants.entries)).getValore();
            DBManager.shutdown();
            result[0] += value;
            lock2.unlock();
            Platform.runLater(() -> {
                getChildren().remove(circle1);
                add(new Label(Double.toString(value)), 1, 1);
            });
        }).start();

        new Thread(() -> {
            lock1.lock();
            lock1.unlock();
            lock2.lock();
            lock2.unlock();
            Platform.runLater(() -> {
                getChildren().remove(circle2);
                add(new Label(Double.toString(result[0])), 1, 2);
            });
        }).start();
    }
}
