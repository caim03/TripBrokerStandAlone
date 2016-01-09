package model.daoInterface;

import model.entityDB.AbstractEntity;
import model.entityDB.ProdottoEntity;

import java.util.List;

public interface DAO {

    List<? extends AbstractEntity> getAll();
    List<? extends AbstractEntity> getByCriteria(String where);
    List<? extends AbstractEntity> getByQuery(String query);
    AbstractEntity getById(int id);
    int store(AbstractEntity entity) throws ClassCastException;
    void delete(AbstractEntity entity) throws ClassCastException;
    void update(AbstractEntity entity) throws ClassCastException;
}
