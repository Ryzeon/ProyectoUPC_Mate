package me.ryzeon.mate.backend;

import lombok.Getter;
import me.ryzeon.mate.service.IService;
import me.ryzeon.mate.utils.HibernateUtil;
import org.hibernate.SessionFactory;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/19/23 @ 19:19
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
@Getter
public class SQLService implements IService {

    private SessionFactory sessionFactory;

    @Override
    public void enable() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void disable() {
        this.sessionFactory.close();
    }
}
