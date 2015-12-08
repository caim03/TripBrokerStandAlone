package view;

import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import model.DBManager;
import model.dao.ProdottoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.ProdottoEntity;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class DBListView extends ListView<AbstractEntity> {

    private String where;

    public DBListView(String where) {

        setWhere(where);
        setCellFactory(param -> new DBCell());
    }

    public void setWhere(String where) {

        this.where = where;
    }

    @Override
    public void refresh() {
        super.refresh();
        System.out.println("REFRESH");
        fill();
    }

    public void fill() {

        getItems().add(AbstractEntity.getInvalidEntity());

        Thread th = new Thread(new AsyncPeek(where, this));
        th.setDaemon(true);
        th.start();
    }

    class AsyncPeek extends Task<Void> {

        String where;
        DBListView view;
        DAO retriever = new DAO() {
            @Override public synchronized List<ProdottoEntity> getByCriteria(String where) {
                DBManager.initHibernate();
                Session session = DBManager.getSession();
                List<ProdottoEntity> entities = session.createQuery(where).list();
                session.close();
                DBManager.shutdown();
                if(entities.isEmpty()) return null;
                else return entities; }
            @Override public List getAll() { return null; }
            @Override public void store(AbstractEntity entity) throws ClassCastException {}
            @Override public void delete(AbstractEntity entity) throws ClassCastException {}
            @Override public void update(AbstractEntity entity) throws ClassCastException {}
        };

        public AsyncPeek(String where, DBListView view) {

            this.where = where;
            this.view = view;
        }

        @Override
        protected Void call() throws Exception {

            List<ProdottoEntity> result = retriever.getByCriteria(where);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    view.getItems().remove(0);
                }
            });

            for (ProdottoEntity entity : result)
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        view.getItems().add(entity);
                    }
                });

            return null;
        }
    }
}
