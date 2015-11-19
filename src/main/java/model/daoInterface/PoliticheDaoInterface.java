package model.daoInterface;

import model.entityDB.PoliticheEntity;

import java.util.List;

/**
 * Created by Christian on 19/11/2015.
 */
public interface PoliticheDaoInterface {

    public List<PoliticheEntity> getAll();
    public void store(PoliticheEntity politicheEntity);
    public void delete(PoliticheEntity politicheEntity);
    public void update(PoliticheEntity politicheEntity);
}
