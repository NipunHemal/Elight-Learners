package lk.ijse.elight_driving_school.service;

import lk.ijse.elight_driving_school.dao.custom.impl.*;
import lk.ijse.elight_driving_school.enums.ServiceTypes;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;
    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return serviceFactory == null ? (serviceFactory = new ServiceFactory()) : serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceTypes serviceTypes) {
        return switch (serviceTypes) {
            case COURSE ->  (T) new CourServiceImpl();
        };
    }
}
