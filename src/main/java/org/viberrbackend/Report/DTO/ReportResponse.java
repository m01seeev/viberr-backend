package org.viberrbackend.Report.DTO;

import lombok.Data;

@Data
public class ReportResponse {
    private String name;
    private String username;
    private String userId;
    private String reason;
    private String fromName;
    private String fromUsername;
    private String picRef;
}
