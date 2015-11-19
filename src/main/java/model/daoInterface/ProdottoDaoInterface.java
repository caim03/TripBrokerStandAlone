package model.daoInterface;

import model.entityDB.ProdottoEntity;

import java.util.List;

/**
 * Created by Christian on 19/11/2015.
 */
public interface ProdottoDaoInterface {

    public List<ProdottoEntity> getAll();
    public void store(ProdottoEntity prodottoEntity);
    public void delete(ProdottoEntity prodottoEntity);
    public void update(ProdottoEntity prodottoEntity);
}
