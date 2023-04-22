package me.ryzeon.mate.services;

import me.ryzeon.mate.backend.SQLService;
import me.ryzeon.mate.model.user.User;
import me.ryzeon.mate.service.IService;
import me.ryzeon.mate.service.ServiceContainer;
import me.ryzeon.mate.utils.Utils;
import me.ryzeon.mate.utils.security.SecurityFactory;
import org.hibernate.Session;

import java.util.Optional;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/22/23 @ 04:22
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class UserService implements IService {

    private final SQLService sqlService = ServiceContainer.get(SQLService.class);

    @Override
    public void enable() {

    }

    public Optional<User> getUser(String username) {
        try (Session session = sqlService.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE username = :username", User.class).setParameter("username", username).uniqueResultOptional();
        }
    }

    public Optional<User> getUser(int id) {
        try (Session session = sqlService.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE id = :id", User.class).setParameter("id", id).uniqueResultOptional();
        }
    }

    public Optional<User> getUserByEmail(String email) {
        try (Session session = sqlService.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE email = :email", User.class).setParameter("email", email).uniqueResultOptional();
        }
    }

    public void saveUser(User user) {
        user.setSaltKey(SecurityFactory.getFactory().createRandomSecretKey());
        try {
            user.setPassword(SecurityFactory.getFactory().getSecurity().encrypt(user.getPassword(), user.getSaltKey()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Session session = sqlService.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteUser(User user) {
        Session session = sqlService.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    public String getNicePassword(User user) {
        try {
            return SecurityFactory.getFactory().getSecurity().decrypt(user.getPassword(), user.getSaltKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isLoggedIn() {
        String[] localCredentials = Utils.recoverPassword();
        if (localCredentials == null) {
            return false;
        }
        Optional<User> user = getUser(localCredentials[0]);
        if (user.isEmpty()) {
            return false;
        }
        try {
            return SecurityFactory.getFactory().getSecurity().decrypt(user.get().getPassword(), user.get().getSaltKey()).equals(localCredentials[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disable() {

    }
}
