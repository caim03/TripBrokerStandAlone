package view;

import javafx.scene.control.TableCell;
import model.entityDB.DipendentiEntity;
import view.material.ProgressCircle;


public class ProgressCell extends TableCell<DipendentiEntity, Boolean> {
    final private ProgressCircle progress;

    public ProgressCell(){
        progress = ProgressCircle.miniCircle();
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if(!empty){
            setGraphic(progress);
        }
    }
}
