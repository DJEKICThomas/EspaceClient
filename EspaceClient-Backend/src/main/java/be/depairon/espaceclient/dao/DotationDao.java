package be.depairon.espaceclient.dao;


import be.depairon.espaceclient.entity.Dotation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DotationDao {
    List<Dotation> getList(String customer);
}
