package controller.command;

import javafx.scene.control.ListView;
import model.entityDB.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * This peculiar implementation of Command is used for ListViews uncoupling: it basically
 * migrates DB entities from one ListView to another. In order to avoid frequent re-instantiation
 * of this class, due to the lack of arguments of execute() (no way to pass the AbstractEntity object
 * from there), execution is performed via execute(AbstractEntity entity) method, with the original
 * execute() being unaccessible to external classes. This has the effect to disrupt the polymorphism
 * benefit of the Command pattern, because TransferRecordCommands cannot be functionally used as
 * mere Command objects.
 */
public class TransferRecordCommand extends Command {

    private List<AbstractEntity> entities = new ArrayList<>();
    ListView list;

    /**
     * Main constructor.
     * @param list ListView: receiver ListView
     */
    public TransferRecordCommand(ListView list) { this.list = list; }

    /**
     * Only accessible method of the class. Requires ad-hoc casting.
     * @param entity AbstractEntity: to-be-transferred entity
     */
    public void execute(AbstractEntity entity) {
         //store entity
        load(entity);
        execute(); //actual execution
    }

    public void execute(List<AbstractEntity> entities) {
        if (entities == null) return;
        entities.forEach(this::load);
        execute(); //actual execution
    }

    public void load(AbstractEntity entity) { entities.add(entity); }

    @Override
    public void execute() {
        if (entities.size() == 0) return; //Fallacious call
        list.getItems().addAll(entities); //Migration
        consume();
    }

    private void consume() { entities.clear(); } //entity attribute is reset after every use
}
