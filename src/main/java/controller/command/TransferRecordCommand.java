package controller.command;

import javafx.scene.control.ListView;
import model.entityDB.AbstractEntity;

public class TransferRecordCommand extends Command {

    static AbstractEntity entity;
    ListView list;

    public TransferRecordCommand(ListView list) { this.list = list; }
    public static void loadEntity(AbstractEntity entity) { TransferRecordCommand.entity = entity; }

    @Override
    public void execute() {

        if (entity == null) return;

        list.getItems().add(entity);
        consume();
    }

    private static void consume() { entity = null; }
}
