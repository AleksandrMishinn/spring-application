package com.andersen.repository.impl;

import com.andersen.domain.Role;
import com.andersen.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private SessionFactory sessionFactory;

    @Override
    public List<Role> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Role> query = session.createQuery("from Role", Role.class);
        return query.getResultList();
    }

    @Override
    public Optional<Role> getRole(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Role.class, id));
    }

    @Override
    public Optional<Role> getRole(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Role> query = session.createQuery("from Role where name ='" + name + "'", Role.class);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public void saveRole(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(role);
    }

    @Override
    public void deleteRole(long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Role where id=:roleId");
        query.setParameter("roleId", id);
        query.executeUpdate();
    }

}
