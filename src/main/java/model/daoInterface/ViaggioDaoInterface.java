package model.daoInterface;

import model.entityDB.ViaggioEntity;

import java.util.List;

/**
 * Created by Christian on 19/11/2015.
 */
public interface ViaggioDaoInterface {

    public List<ViaggioEntity> getAll();
    public void store(ViaggioEntity viaggioEntity);
    public void delete(ViaggioEntity viaggioEntity);
    public void update(ViaggioEntity viaggioEntity);
}
