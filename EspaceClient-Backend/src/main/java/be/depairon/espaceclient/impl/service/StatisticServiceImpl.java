package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.StatisticDao;
import be.depairon.espaceclient.entity.Statistic;
import be.depairon.espaceclient.entity.Statistic2;
import be.depairon.espaceclient.entity.query.StatisticHeader;
import be.depairon.espaceclient.repository.Statistic2Repository;
import be.depairon.espaceclient.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    private final StatisticDao statisticDao;
    private final Statistic2Repository statistic2Repository;

    @Autowired
    public StatisticServiceImpl(StatisticDao statisticDao, Statistic2Repository statistic2Repository) {
        this.statisticDao = statisticDao;
        this.statistic2Repository = statistic2Repository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<StatisticHeader> getList(String customer, String nature, int fromYear) {
//        return statisticDao.getList(customer, fromYear);
        return statisticDao.getList2(customer, nature, fromYear);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Statistic2> getDetail(long ncsId, long artId, int fromYear) {
        return statistic2Repository.findAllByCustomer_IdAndArticle_IdAndYear1(ncsId, artId, fromYear);
    }

    @Override
    public List<Statistic> getWebStatisticDetail(long ncsId, long artId, int fromYear) {
        return statisticDao.getWebStatisticList(ncsId, artId, fromYear);
    }
}
