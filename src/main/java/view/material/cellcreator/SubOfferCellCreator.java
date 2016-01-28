package view.material.cellcreator;

import model.entityDB.OffertaEntity;
import view.material.EmptyCell;

    abstract class SubOfferCellCreator<T extends OffertaEntity> extends AbstractCellCreator<T> {

    private OfferCellCreator superFactory;
    protected SubOfferCellCreator() { superFactory = OfferCellCreator.instance(); }

    @Override public EmptyCell createCell(T entity) { return decorate(superFactory.createCell(entity), entity); }
    abstract EmptyCell decorate(EmptyCell cell, T entity) ;
}
