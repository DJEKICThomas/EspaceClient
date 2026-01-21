package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.StatisticController;
import be.depairon.espaceclient.dto.StatisticDTO;
import be.depairon.espaceclient.dto.StatisticDetailDTO;
import be.depairon.espaceclient.dto.StatisticHeaderDTO;
import be.depairon.espaceclient.entity.Statistic;
import be.depairon.espaceclient.entity.Statistic2;
import be.depairon.espaceclient.entity.query.StatisticHeader;
import be.depairon.espaceclient.service.StatisticService;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StatisticControllerImpl implements StatisticController {

    private final StatisticService statisticService;
    private final int year;
    private final int yearMinus1;
    private final int yearMinus2;

    @Autowired
    public StatisticControllerImpl(StatisticService statisticService) {
        this.statisticService = statisticService;
        this.year = LocalDate.now().getYear();
        this.yearMinus1 = this.year - 1;
        this.yearMinus2 = this.yearMinus1 - 1;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getListByCustomer(String customer, String nature, String token) {
        List<StatisticHeaderDTO> dtoList = new ArrayList<>();
        List<StatisticHeader> list = statisticService.getList(customer, nature, yearMinus2);
        list.forEach(statisticHeader -> dtoList.add(convert(statisticHeader)));
        return ResponseEntity.ok(dtoList);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getDetails(long cusId, long artId, String token) {
//        System.out.println("Hello detail");
        List<StatisticDetailDTO> detailDTOList = new ArrayList<>();
        List<Statistic> statisticsYearMinus2 = statisticService.getWebStatisticDetail(cusId, artId, yearMinus2);
        List<Statistic> statisticsYearMinus1 = statisticService.getWebStatisticDetail(cusId, artId, yearMinus1);
        List<Statistic> statisticsYear = statisticService.getWebStatisticDetail(cusId, artId, year);
        for (int i = 0; i < 12; i++) {
            StatisticDetailDTO dto = new StatisticDetailDTO();
            int monthValue = i + 1;
            dto.setMonth(getMonth(monthValue));
            dto.setYear1Count(getCounterOnMoth(statisticsYearMinus2, monthValue)); // plus petite année
            dto.setYear2Count(getCounterOnMoth(statisticsYearMinus1, monthValue)); // moyen
            dto.setYear3Count(getCounterOnMoth(statisticsYear, monthValue)); // plus grand
            detailDTOList.add(dto);
        }
//        List<Statistic2> detail = statisticService.getDetail(cusId, artId, yearMinus2);
//        for (int i = 0; i < 12; i++) {
//            StatisticDetailDTO dto = new StatisticDetailDTO();
//            dto.setMonth(getMonth(i + 1));
//            dto.setYear1Count(0);
//            dto.setYear2Count(0);
//            dto.setYear3Count(0);
//            detailDTOList.add(dto);
//        }
//
//        for (Statistic2 statistic2 : detail) {
//            int index = statistic2.getMonth1() - 1;
//            detailDTOList.remove(index);
//            detailDTOList.add(index, convert(statistic2));
//        }

        // detail.forEach(statistic2 -> detailDTOList.add(convert(statistic2)));
        return ResponseEntity.ok(detailDTOList);
    }

    @NotNull
    private Integer getCounterOnMoth(List<Statistic> statisticList, int monthValue) {
        return statisticList.stream().filter(s -> s.getMonth() == monthValue).findFirst().map(Statistic::getCounter).orElse(0);
    }

    private StatisticDetailDTO convert(Statistic2 statistic2) {
        StatisticDetailDTO dto = new StatisticDetailDTO();
        if (statistic2 != null) {
            dto.setMonth(getMonth(statistic2.getMonth1()));
            dto.setYear1Count(statistic2.getCounter1());
            dto.setYear2Count(statistic2.getCounter2());
            dto.setYear3Count(statistic2.getCounter3());
        }
        return dto;
    }

    private StatisticHeaderDTO convert(StatisticHeader statisticHeader) {
        StatisticHeaderDTO dto = new StatisticHeaderDTO();
        if (statisticHeader != null) {
            dto.setNcsId(statisticHeader.getNcsId());
            dto.setArtId(statisticHeader.getArtId());
            dto.setSite(statisticHeader.getSite());
            dto.setArtDes(statisticHeader.getArt_des());
            dto.setYear1(yearMinus2);
            dto.setSumYear1(statisticHeader.getSum_year1());
            dto.setYear2(yearMinus1);
            dto.setSumYear2(statisticHeader.getSum_year2());
            dto.setYear3(year);
            dto.setSumYear3(statisticHeader.getSum_year3());
        }
        return dto;
    }

    private String getMonth(int i) {
        return switch (i) {
            case 1 -> "janvier";
            case 2 -> "février";
            case 3 -> "mars";
            case 4 -> "avril";
            case 5 -> "mai";
            case 6 -> "juin";
            case 7 -> "juillet";
            case 8 -> "août";
            case 9 -> "septembre";
            case 10 -> "octobre";
            case 11 -> "novembre";
            case 12 -> "décembre";
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }

    private StatisticDTO convert(Statistic statistic) {
        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setId(statistic.getId());
        statistic.getArticle().getDescriptions()
                .stream().filter(des -> des.getArtDesLng().equals("fr")).findFirst()
                .ifPresent(artDes -> statisticDTO.setArtDes(artDes.getArtDesDes().trim()));
        statisticDTO.setCounter(statistic.getCounter());
        statisticDTO.setSite(statistic.getCustomer().getCusNm1().trim());
        statisticDTO.setMonth(getMonth(statistic.getMonth()));
        statisticDTO.setYear(statistic.getYear());
        return statisticDTO;
    }
}
