package ru.social.server.dao;

import ru.social.server.model.Group;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GroupDAO implements GroupDAOLocal {

    @PersistenceContext(unitName = "addressbook")
    private EntityManager manager;

    @Override
    public void add(Group group) {
        manager.persist(group);
    }

    @Override
    public void edit(Group group) {
        manager.merge(group);
    }

    @Override
    public void remove(int id) {
        manager.remove(find(id));
    }

    @Override
    public Group find(int id) {
        return manager.find(Group.class, id);
    }
}
