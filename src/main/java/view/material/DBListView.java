package view.material;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import org.hibernate.Session;
import view.desig.PacketList;

import java.util.List;

public class DBListView extends ListView<AbstractEntity> {
    /*
     * Custom ListView; it defines no graphic customization, only filling behaviour.
     * It is directly associated to the Hibernate DataBase and queries it as specified by the List creator.
     * Data is recovered concurrently to GUI Thread, then inserted into the ListView.
     */

    private String where; //SQL WHERE clause

    public DBListView() {
        setCellFactory(param -> new DBCell()); //DBListView uses DBCells to represent items
        getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
                Platform.runLater(() -> DBListView.this.getSelectionModel().clearSelection()));
        //On item selected, clear selection to avoid persistent selection behaviour
    }

    public DBListView(String where) {
        this();
        setWhere(where);
    }

    public DBListView(ObservableList list) {

        this();
        setItems(list);
    }

    public void setWhere(String where) { this.where = where; }

    @Override
    public void refresh() {
        getItems().clear();
        fill();
    }
    //Refresh routine hijacked and used as filling routine to overcome Polymorphism issues

    public void fill() {

        spin(); //ProgressCircle loading effect

        Thread th = new Thread(new AsyncPeek()); //Concurrent filling routine
        th.setDaemon(true); //No need to update if Application is no longer displayed
        th.start();
    }

    public void spin() {
        getItems().add(AbstractEntity.getInvalidEntity());
    }

    class AsyncPeek extends Task<Void> {

        /*
         * Utility class for concurrent progressive ListView filling.
         * It defines the querying routine and progressively fills up the View with DB records.
         */

        DAO retriever = new DAO() {
            /* AsyncPeek personal DAO object, used for custom queries. Benefits are:
             * - no need to get the proper DAO object (DBListView manages various object types);
             * - no need to specify useless methods (DAO is only requested to retrieve objects via getByCriteria method). */
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
                    System.out.println("WHERE " + where);
                    return null;
                }
                finally {
                    try { if (session != null) session.close(); } catch (Exception ignore) {}
                    DBManager.shutdown();
                }
            }

            @Override
            public List<? extends AbstractEntity> getByQuery(String query) {
                return null;
            }

            @Override
            public AbstractEntity getById(int id) {
                return null;
            }

            @Override public List getAll() { return null; }
            @Override public int store(AbstractEntity entity) throws ClassCastException { return 0; }
            @Override public void delete(AbstractEntity entity) throws ClassCastException {}
            @Override public void update(AbstractEntity entity) throws ClassCastException {}
        };

        @Override
        protected Void call() throws Exception {

            List<? extends AbstractEntity> result = retriever.getByCriteria(where); //Get requested records
            Platform.runLater(() -> getItems().remove(0));
            //Once records are retrieved, dismiss loading animation

            for (AbstractEntity entity : result)
                Platform.runLater(() -> getItems().add(entity));

            return null;
        }
    }
}
