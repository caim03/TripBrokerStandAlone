package model.daoInterface;

import model.entityDB.AbstractEntity;

import java.util.List;

public interface DAO {

    public List getAll();
    public AbstractEntity getByCriteria(String where);
    public void store(AbstractEntity entity) throws ClassCastException;
    public void delete(AbstractEntity entity) throws ClassCastException;
    public void update(AbstractEntity entity) throws ClassCastException;
}
