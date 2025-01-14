package org.viberrbackend.Report;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReportRepository extends MongoRepository<ReportModel, String> {
    Optional<ReportModel> findByUserIdAndTo(String userId, String to);
}
