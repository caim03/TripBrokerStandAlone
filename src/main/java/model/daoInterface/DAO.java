package model.daoInterface;

import model.entityDB.AbstractEntity;
import model.entityDB.ProdottoEntity;

import java.util.List;

public interface DAO {

    List<? extends AbstractEntity> getAll();
    List<? extends AbstractEntity> getByCriteria(String where);
    int store(AbstractEntity entity) throws ClassCastException;
    void delete(AbstractEntity entity) throws ClassCastException;
    void update(AbstractEntity entity) throws ClassCastException;
}
