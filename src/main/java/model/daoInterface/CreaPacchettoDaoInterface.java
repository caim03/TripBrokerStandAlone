package model.daoInterface;

import model.entityDB.CreaPacchettoEntity;

import java.util.List;

/**
 * Created by Christian on 19/11/2015.
 */
public interface CreaPacchettoDaoInterface {

    public List<CreaPacchettoEntity> getAll();
    public void store(CreaPacchettoEntity creaPacchettoEntity);
    public void delete(CreaPacchettoEntity creaPacchettoEntity);
    public void update(CreaPacchettoEntity creaPacchettoEntity);
}
