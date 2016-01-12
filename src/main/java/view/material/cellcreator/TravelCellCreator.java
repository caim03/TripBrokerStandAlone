package view.material.cellcreator;

import controller.Constants;
import model.entityDB.ViaggioEntity;
import view.material.EmptyCell;

import java.text.SimpleDateFormat;

public class TravelCellCreator extends SubOfferCellCreator<ViaggioEntity> {

    private static TravelCellCreator me;
    public static TravelCellCreator instance() {

        if (me == null) me = new TravelCellCreator();
        return me;
    }
    protected TravelCellCreator() { super(); }

    @Override
    EmptyCell decorate(EmptyCell cell, ViaggioEntity entity) {

        String vehicle = entity.getMezzo(), image;

        if (Constants.plane.equals(vehicle)) image = "airplane.png"; //Aereo
        else if (Constants.bus.equals(vehicle)) image = "bus.png"; //Bus
        else if (Constants.train.equals(vehicle)) image = "train.png"; //Treno
        else if (Constants.boat.equals(vehicle)) image = "boat.png"; //Nave
        else image = "create.png"; //Default

        cell.setThumbnail(image);

        String departure = "partenza da " + entity.getCittà()
                + " alle " + entity.getOraPartenza() + ":" + "00"
                + " del " + new SimpleDateFormat("dd MMM yyyy").format(entity.getDataInizio());

        String arrival = "arrivo a " + entity.getDestinazione()
                + " alle " + entity.getOraArrivo() + ":" + "00"
                + " del " + new SimpleDateFormat("dd MMM yyyy").format(entity.getDataArrivo());

        cell.addSubHeaders(departure, arrival);

        return cell;
    }
}

