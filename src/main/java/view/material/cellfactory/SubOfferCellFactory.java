package view.material.cellfactory;

import model.entityDB.OffertaEntity;
import view.material.EmptyCell;

    abstract class SubOfferCellFactory<T extends OffertaEntity> extends AbstractCellFactory<T> {

    private OfferCellFactory superFactory;
    SubOfferCellFactory() { superFactory = OfferCellFactory.instance(); }

    @Override public EmptyCell createCell(T entity) { return decorate(superFactory.createCell(entity), entity); }
    abstract EmptyCell decorate(EmptyCell cell, T entity) ;
}
