package view;

import model.entityDB.OffertaEntity;

import java.util.List;

public class OfferIterator {

    private int index = 0;
    private List<OffertaEntity> entities;
    private OffertaEntity cache;


    public OfferIterator(List<OffertaEntity> entities) {
        this.entities = entities;
    }

    public boolean first() {

        boolean bool = entities == null || entities.size() == 0;
        if (!(bool)) {

            index = 0;
            cache = entities.get(0);
        }

        return bool;
    }

    public boolean hasNext() {

        return !(entities == null) && index < entities.size();
    }

    public boolean next() {

        if (hasNext()) {

            cache = entities.get(index);
            ++index;
            return true;
        }
        else return false;
    }

    public OffertaEntity getOffer() {

        return cache;
    }

    public List<OffertaEntity> getRemaining() {

        List<OffertaEntity> list = entities.subList(index, entities.size());
        --index;
        return list;
    }
}
