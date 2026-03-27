package be.depairon.espaceclient.service;

import be.depairon.espaceclient.dto.MonthlyTotalDTO;

import java.util.List;

public interface AnalyticsService {

    List<MonthlyTotalDTO> getMonthlyTotals(String userId, int from, int to);
}
