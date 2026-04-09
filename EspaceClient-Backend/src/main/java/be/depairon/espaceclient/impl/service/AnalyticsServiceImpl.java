package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.dao.FmovDao;
import be.depairon.espaceclient.dao.ScmAllDocDao;
import be.depairon.espaceclient.dto.MonthlyTotalDTO;
import be.depairon.espaceclient.entity.Fmov;
import be.depairon.espaceclient.entity.ScmAllDoc;
import be.depairon.espaceclient.service.AnalyticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final CustomerDao customerDao;
    private final ScmAllDocDao scmAllDocDao;
    private final FmovDao fmovDao;

    public AnalyticsServiceImpl(CustomerDao customerDao, ScmAllDocDao scmAllDocDao, FmovDao fmovDao) {
        this.customerDao = customerDao;
        this.scmAllDocDao = scmAllDocDao;
        this.fmovDao = fmovDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MonthlyTotalDTO> getMonthlyTotals(String userId, int from, int to, String ncs) {
        List<String> ncsList;
        if (ncs != null && !ncs.isEmpty()) {
            ncsList = List.of(ncs);
        } else {
            ncsList = customerDao.getNcsListByUserId(userId);
        }

        Map<String, BigDecimal[]> monthlyMap = new TreeMap<>();
        for (String n : ncsList) {
            List<ScmAllDoc> orders = scmAllDocDao.findDocsByNcsAndDateRange(n, from, to);
            for (ScmAllDoc doc : orders) {
                String monthKey = toMonthKey(doc.getDateCreation());
                if (monthKey == null) continue;
                BigDecimal[] totals = monthlyMap.computeIfAbsent(monthKey,
                        k -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
                if (doc.getMontantTotal() != null) {
                    totals[0] = totals[0].add(doc.getMontantTotal());
                }
            }

            List<Fmov> invoices = fmovDao.findInvoicesByNcsAndDateRange(n, from, to);
            for (Fmov fmov : invoices) {
                String monthKey = toMonthKey(fmov.getFmovDatDoc());
                if (monthKey == null) continue;
                BigDecimal[] totals = monthlyMap.computeIfAbsent(monthKey,
                        k -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});
                if ("D".equals(fmov.getFmovDc()) && fmov.getFmovValCur() != null) {
                    totals[1] = totals[1].add(fmov.getFmovValCur());
                }
            }
        }

        return monthlyMap.entrySet().stream()
                .map(entry -> new MonthlyTotalDTO(
                        entry.getKey(),
                        entry.getValue()[0],
                        entry.getValue()[1]
                ))
                .collect(Collectors.toList());
    }

    /**
     * Convertit un entier CCYYMMDD en clé "YYYY-MM"
     */
    private String toMonthKey(int dateInt) {
        if (dateInt < 10000000) return null;
        int yearMonth = dateInt / 100; // CCYYMM
        int year = yearMonth / 100;
        int month = yearMonth % 100;
        if (month < 1 || month > 12) return null;
        return String.format("%04d-%02d", year, month);
    }
}
