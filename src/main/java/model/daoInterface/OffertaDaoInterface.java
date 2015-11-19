package model.daoInterface;

import model.entityDB.OffertaEntity;

import java.util.List;

/**
 * Created by Christian on 19/11/2015.
 */
public interface OffertaDaoInterface {

    public List<OffertaEntity> getAll();
    public void store(OffertaEntity offertaEntity);
    public void delete(OffertaEntity offertaEntity);
    public void update(OffertaEntity offertaEntity);
}
