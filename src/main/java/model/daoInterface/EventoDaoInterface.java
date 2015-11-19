package model.daoInterface;

import model.entityDB.EventoEntity;

import java.util.List;

/**
 * Created by Christian on 19/11/2015.
 */
public interface EventoDaoInterface {

    public List<EventoEntity> getAll();
    public void store(EventoEntity eventoEntity);
    public void delete(EventoEntity eventoEntity);
    public void update(EventoEntity eventoEntity);
}
