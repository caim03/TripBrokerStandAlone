package view;

import javafx.scene.control.ListView;
import model.entityDB.ProdottoEntity;

public class ProdottoListView extends ListView<ProdottoEntity> {

    String where;
    public ProdottoListView(String where) {

        this.where = where;
    }

    public void fill() {


        Thread th = new Thread(new AsyncTask(where, this));
        th.setDaemon(true);
        th.start();
    }

    public void erase() {

        getItems().remove(0, getItems().size());
    }
}
