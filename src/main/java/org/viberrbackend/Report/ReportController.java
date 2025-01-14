package org.viberrbackend.Report;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.viberrbackend.Report.DTO.DeclineReport;
import org.viberrbackend.Report.DTO.ReportRequest;
import org.viberrbackend.Report.DTO.ReportResponse;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PutMapping
    public void deleteReport(@RequestBody DeclineReport reportDto) {
        reportService.deleteReport(reportDto);
    }

    @GetMapping
    public List<ReportResponse> getReports() {
        return reportService.getReports();
    }

    @PostMapping("/{userId}")
    public String addReport(@PathVariable String userId, @RequestBody ReportRequest reportDto) {
        return reportService.addReport(reportDto, userId);
    }
}
