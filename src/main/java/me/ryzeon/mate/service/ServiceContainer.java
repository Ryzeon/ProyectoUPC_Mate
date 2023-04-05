package me.ryzeon.mate.service;

import lombok.experimental.UtilityClass;
import me.ryzeon.mate.service.exception.AlreadyRegisterException;
import me.ryzeon.mate.service.exception.NoServiceException;

import java.util.LinkedHashMap;

/**
 * Created by Ryzeon
 * Project: ProjectoUPC_Mate
 * Date: 4/2/23 @ 22:32
 * Twitter: @Ryzeon_ 😎
 * Github: github.ryzeon.me
 */
@UtilityClass
public class ServiceContainer {

    private final LinkedHashMap<Class<? extends IService>, IService> services = new LinkedHashMap<>();

    public <T> T register(Class<T> tClass) {
        if (!IService.class.isAssignableFrom(tClass)) {
            throw new NoServiceException(tClass);
        }

        if (services.containsKey(tClass)) {
            throw new AlreadyRegisterException(tClass);
        }

        IService service;
        try {
            service = (IService) tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return (T) services.put((Class<? extends IService>) tClass, service);
    }

    public void enableServices() {
        services.values().forEach(IService::enable);
    }

    public <T> T get(Class<T> clazz) {
        return (T) services.get(clazz);
    }

    public <T> T bind(Class<T> clazz) {
        return (T) services.get(clazz);
    }

}
