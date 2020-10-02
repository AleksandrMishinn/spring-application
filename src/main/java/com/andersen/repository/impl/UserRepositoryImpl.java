package com.andersen.repository.impl;

import com.andersen.domain.User;
import com.andersen.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory sessionFactory;

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public void saveUser(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(user);
    }

    @Override
    public Optional<User> getUser(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return Optional.ofNullable(currentSession.get(User.class, id));
    }

    @Override
    public void deleteUser(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query query = currentSession.createQuery("delete from User where id = :userId");
        query.setParameter("userId", id);
        query.executeUpdate();
    }

}
