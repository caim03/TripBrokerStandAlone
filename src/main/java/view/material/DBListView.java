package view.material;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.ProdottoEntity;
import model.entityDB.ViaggioEntity;
import org.hibernate.Session;

import java.util.List;

public class DBListView extends ListView<AbstractEntity> {

    private String where;

    public DBListView(String where) {

        setWhere(where);
        setCellFactory(param -> new DBCell());
        getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Platform.runLater(() -> DBListView.this.getSelectionModel().select(-1));
            }
        });
    }

    public void setWhere(String where) {

        this.where = where;
    }

    @Override
    public void refresh() {

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
            @Override public synchronized List<? extends AbstractEntity> getByCriteria(String where) {

                Session session = null;

                try {
                    DBManager.initHibernate();
                    session = DBManager.getSession();
                    List<? extends AbstractEntity> entities = session.createQuery(where).list();
                    if (entities.isEmpty()) return null;
                    else return entities;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                finally {
                    try { if (session != null) session.close(); } catch (Exception ignore) {}
                    DBManager.shutdown();
                }
            }

            @Override public List getAll() { return null; }
            @Override public int store(AbstractEntity entity) throws ClassCastException { return 0; }
            @Override public void delete(AbstractEntity entity) throws ClassCastException {}
            @Override public void update(AbstractEntity entity) throws ClassCastException {}
        };

        public AsyncPeek(String where, DBListView view) {

            this.where = where;
            this.view = view;
        }

        @Override
        protected Void call() throws Exception {

            List<? extends AbstractEntity> result = retriever.getByCriteria(where);
            Platform.runLater(() -> view.getItems().remove(0));

            for (AbstractEntity entity : result)
                Platform.runLater(() -> view.getItems().add(entity));

            return null;
        }
    }
}
