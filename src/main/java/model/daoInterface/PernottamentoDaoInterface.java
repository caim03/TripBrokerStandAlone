package model.daoInterface;

import model.entityDB.PernottamentoEntity;

import java.util.List;

/**
 * Created by Christian on 19/11/2015.
 */
public interface PernottamentoDaoInterface {

    public List<PernottamentoEntity> getAll();
    public void store(PernottamentoEntity pernottamentoEntity);
    public void delete(PernottamentoEntity pernottamentoEntity);
    public void update(PernottamentoEntity pernottamentoEntity);
}
