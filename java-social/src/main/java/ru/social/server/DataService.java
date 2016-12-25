package ru.social.server;

import ru.social.server.dao.ContactDAOLocal;
import ru.social.server.dao.GroupDAOLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class DataService {

    @EJB
    private ContactDAOLocal contactDAO;

    @EJB
    private GroupDAOLocal groupDAO;

    public DataService() {
    }

    public ContactDAOLocal getContactDAO() {
        return contactDAO;
    }

    public GroupDAOLocal getGroupDAO() {
        return groupDAO;
    }
}
