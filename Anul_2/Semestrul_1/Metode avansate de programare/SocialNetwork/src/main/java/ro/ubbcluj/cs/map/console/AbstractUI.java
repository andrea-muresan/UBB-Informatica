package ro.ubbcluj.cs.map.console;

import ro.ubbcluj.cs.map.service.ServiceI;

public abstract class AbstractUI implements UI {
    ServiceI srv;

    public AbstractUI(ServiceI srv) {
        this.srv = srv;
    }
}