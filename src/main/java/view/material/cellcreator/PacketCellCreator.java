package view.material.cellcreator;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.entityDB.PacchettoEntity;
import model.entityDB.OffertaEntity;
import view.material.EmptyCell;

public class PacketCellCreator extends AbstractCellCreator<PacchettoEntity> {

    private static PacketCellCreator me;
    protected static PacketCellCreator instance() {
        if (me == null) me = new PacketCellCreator();
        return me;
    }
    protected PacketCellCreator() { super(); }

    @Override
    protected EmptyCell createCell(PacchettoEntity entity) {

        Label label = new Label(entity.getNome());
        label.setPadding(new Insets(0, 0, 0, 25));
        VBox box = new VBox(label);

        for (OffertaEntity offer : entity.retrieveOffers())
            box.getChildren().add(AbstractCellCreator.getCell(offer));

        EmptyCell cell = new EmptyCell();
        cell.getChildren().add(box);
        return cell;
    }
}
