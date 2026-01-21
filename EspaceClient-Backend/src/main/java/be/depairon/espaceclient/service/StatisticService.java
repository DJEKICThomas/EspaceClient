package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.Statistic;
import be.depairon.espaceclient.entity.Statistic2;
import be.depairon.espaceclient.entity.query.StatisticHeader;

import java.util.List;

public interface StatisticService {

    List<StatisticHeader> getList(String customer, String nature, int fromYear);
    List<Statistic2> getDetail(long ncsId, long artId, int fromYear);
    List<Statistic> getWebStatisticDetail(long ncsId, long artId, int fromYear);
}
