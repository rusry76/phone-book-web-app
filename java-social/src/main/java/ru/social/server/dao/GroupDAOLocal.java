package ru.social.server.dao;

import ru.social.server.model.Group;

import javax.ejb.Local;

@Local
public interface GroupDAOLocal {

    public void add(Group group);

    public void edit(Group group);

    public void remove(int id);

    public Group find(int id);
}
