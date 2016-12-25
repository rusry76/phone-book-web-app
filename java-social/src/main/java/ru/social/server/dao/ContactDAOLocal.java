package ru.social.server.dao;

import ru.social.server.model.Contact;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ContactDAOLocal {

    public void add(Contact contact);

    public void edit(Contact contact);

    public void remove(int id);

    public Contact find(int id);

    public List<Contact> findAll();

    public List<Contact> findAll(String filter, int start, int resultsPerPage);

    public Long getAmountOfContacts(String filter);
}
