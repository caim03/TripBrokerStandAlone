package view;

import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import model.entityDB.ProdottoEntity;

public class AsyncTask extends Task<ProdottoEntity> {

    String type;
    ListView view;

    public AsyncTask(String type, ListView view) {

        this.type = type;
        this.view = view;
    }

    @Override
    protected ProdottoEntity call() throws Exception {

        /*DBManager.initHibernate();
        List<ProdottoEntity> list = ((ProdottoDaoHibernate)ProdottoDaoHibernate.instance()).getType(type);
        DBManager.shutdown();

        for (ProdottoEntity p : list) if (p != null) Platform.runLater(new Runnable() { @Override public void run()
        { view.getItems().add(p); } });*/
        return null;
    }
}
