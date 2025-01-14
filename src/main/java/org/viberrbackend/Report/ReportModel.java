package org.viberrbackend.Report;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "reports")
public class ReportModel {
    @Id
    private String id;
    private String reason;
    private String userId;
    private String to;
}
