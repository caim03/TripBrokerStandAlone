package controller.agent;

import controller.Constants;
import controller.desig.PacketOverseer;
import model.dao.PoliticaDaoHibernate;
import model.entityDB.AbstractEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PoliticaEntity;
import view.agent.GroupTripList;

import java.util.List;

/**
 * Overseer implementation; inherits PacketOverseer base behaviour, but also checks for
 * minimum participants constraint.
 */
public class GroupTripOverseer extends PacketOverseer {

    /** @param groupTripList; a list of all offers in group trip
     *  @param notify; a boolean value that enable or disable the notifications for test **/
    public GroupTripOverseer(GroupTripList groupTripList, boolean notify) { super(groupTripList, notify); }

    /** @param price; represent the new price of the group trip **/
    @Override protected void updateSubject(double price) { subjectList.setPrice(price); }

    @Override
    protected boolean someOtherAddedCheck(Change<? extends AbstractEntity> c, OffertaEntity entity) {

        int qu = entity.getQuantità();

        boolean result = qu >=
                ((PoliticaEntity) PoliticaDaoHibernate.instance().
                        getById(Constants.minGroup)).getValore();

        if (result)  ((GroupTripList) subjectList).setQu(qu);
        return result;
    }

    /** @return String; return a string that represents a warning information **/
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

        ((GroupTripList) subjectList).forceQu(current == 0 ? 99 : current);
    }
}
