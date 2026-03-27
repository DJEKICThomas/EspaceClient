package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.AnalyticsController;
import be.depairon.espaceclient.dto.MonthlyTotalDTO;
import be.depairon.espaceclient.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AnalyticsControllerImpl implements AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @Override
    public List<MonthlyTotalDTO> getMonthlyTotals(String userId, int from, int to) {
        return analyticsService.getMonthlyTotals(userId, from, to);
    }
}
