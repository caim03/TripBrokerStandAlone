package controller;

import model.DBManager;
import model.dao.EventoDaoHibernate;
import model.dao.PernottamentoDaoHibernate;
import model.dao.ViaggioDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.EventoEntity;
import model.entityDB.PernottamentoEntity;
import model.entityDB.ViaggioEntity;

public class InsertOfferController {

    public static void insertOfferEvent(EventoEntity entity){
        DAO dao = EventoDaoHibernate.instance();
        DBManager.initHibernate();
        dao.store(entity);
        DBManager.shutdown();
    }

    public static void insertOfferStay(PernottamentoEntity entity){
        DAO dao = PernottamentoDaoHibernate.instance();
        DBManager.initHibernate();
        dao.store(entity);
        DBManager.shutdown();
    }

    public static void insertOfferTravel(ViaggioEntity entity){
        DAO dao = ViaggioDaoHibernate.instance();
        DBManager.initHibernate();
        dao.store(entity);
        DBManager.shutdown();
    }
}
