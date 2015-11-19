package model.daoInterface;

import model.entityDB.DipendentiEntity;

import java.util.List;

/**
 * Created by Christian on 19/11/2015.
 */
public interface DipendentiDaoInterface {

    public List<DipendentiEntity> getAll();
    public void store(DipendentiEntity dipendentiEntity);
    public void delete(DipendentiEntity dipendentiEntity);
    public void update(DipendentiEntity dipendentiEntity);
}
