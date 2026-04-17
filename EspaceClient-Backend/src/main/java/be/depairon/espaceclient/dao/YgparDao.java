package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.Ygpar;

public interface YgparDao {
    Ygpar findLogoByCompany(String com);
}
