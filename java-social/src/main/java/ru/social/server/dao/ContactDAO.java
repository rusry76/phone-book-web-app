package ru.social.server.dao;

import ru.social.server.model.Contact;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class ContactDAO  implements ContactDAOLocal {

    private String filter;

    @PersistenceContext(unitName = "addressbook")
    private EntityManager manager;

    @Override
    public void add(Contact contact) {
        manager.persist(contact);
    }

    @Override
    public void edit(Contact contact) {
        manager.merge(contact);
    }

    @Override
    public void remove(int id) {
        manager.remove(find(id));
    }

    @Override
    public Contact find(int id) {
        return manager.find(Contact.class, id);
    }

    @Override
    public List<Contact> findAll() {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Contact> query = cb.createQuery(Contact.class);
        Root<Contact> contact = query.from(Contact.class);
        query.select(contact);
        TypedQuery<Contact> typedQuery = manager.createQuery(query);

        return typedQuery.getResultList();
    }

    @Override
    public List<Contact> findAll(String filter, int start, int resultsPerPage) {
        this.filter = filter;

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Contact> query = cb.createQuery(Contact.class);
        Root<Contact> contact = query.from(Contact.class);
        query.select(contact);
        query.where(
                cb.or(
                        cb.like(
                                contact.get("firstName"), filter + "%"),
                        cb.like(
                                contact.get("lastName"), filter + "%"),
                        cb.like(
                                contact.get("phoneNumber"), filter + "%")
                ));

        query.orderBy(cb.asc(contact.get("firstName")));

        TypedQuery<Contact> typedQuery = manager.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(resultsPerPage);

        return typedQuery.getResultList();
    }

    @Override
    public Long getAmountOfContacts(String filter) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Contact> contact = query.from(Contact.class);
        query.select(cb.count(contact));
        query.where(
                cb.or(
                        cb.like(
                                contact.get("firstName"), filter + "%"),
                        cb.like(
                                contact.get("lastName"), filter + "%"),
                        cb.like(
                                contact.get("phoneNumber"), filter + "%")
                ));

        return manager.createQuery(query).getSingleResult();
    }
}
