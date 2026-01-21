package be.depairon.espaceclient.dao;

import be.depairon.espaceclient.entity.Statistic;
import be.depairon.espaceclient.entity.query.StatisticHeader;

import java.util.List;

public interface StatisticDao {

    List<Statistic> getList(String customer, int fromYear);

    List<Statistic> getWebStatisticList(long ncsid, long artid, int year);

    List<StatisticHeader> getList2(String customer, String nature, int fromYear);
}
