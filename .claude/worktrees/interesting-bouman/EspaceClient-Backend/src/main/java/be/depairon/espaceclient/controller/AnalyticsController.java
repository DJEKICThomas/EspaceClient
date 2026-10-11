package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.MonthlyTotalDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/analytics")
public interface AnalyticsController {

    @GetMapping(value = "/monthly-totals", produces = MediaType.APPLICATION_JSON_VALUE)
    List<MonthlyTotalDTO> getMonthlyTotals(
            @RequestParam("userId") String userId,
            @RequestParam("from") int from,
            @RequestParam("to") int to,
            @RequestParam(value = "ncs", required = false) String ncs
    );
}
