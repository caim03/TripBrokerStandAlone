package model.dao;

import model.entityDB.AbstractEntity;
import java.util.List;

/*** The all classes that implements DAO Interface must override all methods.
 *   This Interface is used for using polymorphism and factory method to create the right Dao object;
 *   in fact exists a Dao object for each entity in DataBase, and each class override this methods to:
 *      1. Retrieve all entities from DataBase
 *      2. Retrieve entities from DataBase with a specific where clause
 *      3. Retrieve entities from DataBase with a new query
 *      4. Retrieve a single entity from DataBase using identifier
 *      5. Save a new entity to DataBase
 *      6. Delete an existing entity from DataBase
 *      7. Update an existing entity from DataBase
 *
 *   Every Dao that implement this interface also implement the Singleton Pattern,
 *   then there is only one instance in the system ***/

public interface DAO {

    List<? extends AbstractEntity> getAll();
    List<? extends AbstractEntity> getByCriteria(String where);
    List<? extends AbstractEntity> getByQuery(String query);
    AbstractEntity getById(int id);
    int store(AbstractEntity entity) throws ClassCastException;
    void delete(AbstractEntity entity) throws ClassCastException;
    void update(AbstractEntity entity) throws ClassCastException;
}
