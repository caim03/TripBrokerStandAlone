package model.daoInterface;

import model.entityDB.PacchettoOffertaEntity;

import java.util.List;

/**
 * Created by Christian on 19/11/2015.
 */
public interface PacchettoOffertaDaoInterface {

    public List<PacchettoOffertaEntity> getAll();
    public void store(PacchettoOffertaEntity pacchettoOffertaEntity);
    public void delete(PacchettoOffertaEntity pacchettoOffertaEntity);
    public void update(PacchettoOffertaEntity pacchettoOffertaEntity);
}
