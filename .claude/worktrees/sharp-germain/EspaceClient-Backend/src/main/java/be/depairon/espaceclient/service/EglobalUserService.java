package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.EglobalUser;

public interface EglobalUserService {
    EglobalUser getByUserId(String userid);
}
