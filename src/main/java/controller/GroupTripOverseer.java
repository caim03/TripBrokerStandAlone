package controller;

import model.dao.PoliticheDaoHibernate;
import model.entityDB.AbstractEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PoliticheEntity;
import view.agent.GroupTripList;

import java.util.List;

public class GroupTripOverseer extends PacketOverseer {

    public GroupTripOverseer(GroupTripList groupTripList) { super(groupTripList); }
    @Override protected void updateSubject(double price) { subjectList.setPrice(price); }

    @Override
    protected boolean someOtherAddedCheck(Change<? extends AbstractEntity> c, OffertaEntity entity, int pos) {

        int qu = entity.getQuantità();

        boolean result = qu <
                ((PoliticheEntity) PoliticheDaoHibernate.instance().
                        getById(Constants.minGroup)).getValore();
        if (result)
            c.getList().remove(pos, c.getList().size());
        else ((GroupTripList) subjectList).setQu(qu);

        return !result;
    }

    @Override
    protected String someOtherAddedMessage() {
        return "Il numero di biglietti disponibili è insufficiente per formare un viaggio di gruppo";
    }

    @Override
    protected void someOtherRemovedCheck(Change<? extends AbstractEntity> c) {

        List list = c.getList();
        int current = 0, qu;
        for (Object aList : list)
            current = current <= (qu = ((OffertaEntity) aList).getQuantità()) ?
                    qu : current;

        ((GroupTripList) subjectList).forceQu(current == 0 ? 999 : current);
    }
}
