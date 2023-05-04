package me.ryzeon.mate.services;

import me.ryzeon.mate.backend.SQLService;
import me.ryzeon.mate.model.travel.TravelInfo;
import me.ryzeon.mate.service.IService;
import me.ryzeon.mate.service.ServiceContainer;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 5/3/23 @ 22:40
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
public class TravelInfoService implements IService {

    private final SQLService sqlService = ServiceContainer.get(SQLService.class);

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    public List<TravelInfo> getTravelInfo(int buyerId) {
        try (Session session = sqlService.getSessionFactory().openSession()) {
            return session.createQuery("FROM TravelInfo WHERE buyerId = :buyerId", TravelInfo.class).setParameter("buyerId", buyerId).getResultList();
        }
    }

    public void saveTravelInfo(TravelInfo travelInfo) {
        travelInfo.setBuyerId(ServiceContainer.bind(UserService.class).getUser().getId());
        try (Session session = sqlService.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(travelInfo);
            session.getTransaction().commit();
        }
    }
}
