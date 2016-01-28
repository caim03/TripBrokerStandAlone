package view.popup;

import controller.Constants;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.DBManager;
import model.dao.PoliticheDaoHibernate;
import model.entityDB.*;
import view.material.ProgressCircle;

public class OfferPopup<T extends OffertaEntity> extends PopupView {

    protected T entity;
    protected boolean selling;

    protected OfferPopup(T entity, boolean selling) {
        this.entity = entity;
        this.selling = selling;
    }
    protected OfferPopup(T entity) { this(entity, false); }

    public T getEntity() { return this.entity; }

    public static OfferPopup getCatalogPopup(OffertaEntity entity) { return getPopup(entity, false); }
    public static OfferPopup getSellingPopup(OffertaEntity entity) { return getPopup(entity, true); }
    private static OfferPopup getPopup(OffertaEntity entity, boolean selling) {
        if (entity instanceof ViaggioEntity) return new TravelPopup((ViaggioEntity) entity, selling);
        else if (entity instanceof PernottamentoEntity) return new StayPopup((PernottamentoEntity) entity, selling);
        else if (entity instanceof EventoEntity) return new EventPopup((EventoEntity) entity, selling);
        else return null;
    }

    @Override protected Parent generatePopup() {

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25));

        int i = selling ? 0 : 1;

        pane.add(new Label("Nome:"), 0, 0);
        pane.add(new Label("Prezzo:"), 0, 1);
        pane.add(new Label("Quantità:"), 0, 2);
        if (!selling) {
            pane.add(new Label("Prenotazioni:"), 0, 3);
            pane.add(new Text(Integer.toString(entity.getPrenotazioni())), 1, 3);
        }
        pane.add(new Label("Città:"), 0, 3 + i);

        pane.add(new Text(entity.getNome()), 1, 0);

        if (selling) {
            ProgressCircle circle = ProgressCircle.miniCircle();
            pane.add(circle, 1, 1);
            new Thread(() -> {
                DBManager.initHibernate();
                double overprice = ((PoliticheEntity) PoliticheDaoHibernate.instance().getById(Constants.minOverprice)).getValore();
                DBManager.shutdown();
                Platform.runLater(() -> {
                    pane.getChildren().remove(circle);
                    pane.add(new Text(Double.toString(entity.getPrezzo() * overprice)), 1, 1);
                });
            }).start();
        }
        else pane.add(new Text(Double.toString(entity.getPrezzo())), 1, 1);

        Text quantity = new Text();
        if (selling) {
            boolean available = entity.getQuantità() - entity.getPrenotazioni() > 0;
            quantity.setText( available ? "Disponibile" : "Non disponibile");
        }
        else quantity.setText(Integer.toString(entity.getQuantità()));

        pane.add(quantity, 1, 2);
        pane.add(new Text(entity.getCittà()), 1, 3 + i);

        return pane;
    }
}
