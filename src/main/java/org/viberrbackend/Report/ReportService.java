package org.viberrbackend.Report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.viberrbackend.Profile.ProfileModel;
import org.viberrbackend.Profile.ProfileRepository;
import org.viberrbackend.Report.DTO.DeclineReport;
import org.viberrbackend.Report.DTO.ReportRequest;
import org.viberrbackend.Report.DTO.ReportResponse;
import org.viberrbackend.User.UserModel;
import org.viberrbackend.User.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public List<ReportResponse> getReports() {
        List<ReportResponse> reports = new ArrayList<>();
        List<ReportModel> reportModels = reportRepository.findAll();
        for (ReportModel reportModel : reportModels) {
            ReportResponse reportResponse = new ReportResponse();
            Optional<UserModel> user = userRepository.findById(reportModel.getTo());
            Optional<UserModel> from = userRepository.findById(reportModel.getUserId());
            ProfileModel fromProfile = profileRepository.findByUserId(reportModel.getUserId());
            ProfileModel profile = profileRepository.findByUserId(reportModel.getTo());
            reportResponse.setName(profile.getFirstName() + " " + profile.getLastName());
            reportResponse.setUsername(user.get().getUsername());
            reportResponse.setReason(reportModel.getReason());
            reportResponse.setFromName(fromProfile.getFirstName() + " " + fromProfile.getLastName());
            reportResponse.setFromUsername(from.get().getUsername());
            reportResponse.setPicRef(profile.getPicRef());
            reportResponse.setUserId(user.get().getId());
            reports.add(reportResponse);
        }
        return reports;
    }

    public String addReport(ReportRequest reportDto, String userId) {
        ReportModel reportModel = new ReportModel();
        UserModel userModel = userRepository.findByUsername(reportDto.getUsername());
        Optional<ReportModel> report = reportRepository.findByUserIdAndTo(userId, userModel.getId());
        if (report.isPresent()) {
            return "Вы уже пожаловались";
        } else {
            reportModel.setReason(reportDto.getReason());
            reportModel.setUserId(userId);
            reportModel.setTo(userModel.getId());
            reportRepository.save(reportModel);
            return "Жалоба отправлена";
        }
    }

    public void deleteReport(DeclineReport reportDto) {
        UserModel user1 = userRepository.findByUsername(reportDto.getUsername());
        UserModel user2 = userRepository.findByUsername(reportDto.getFromUsername());
        Optional<ReportModel> report = reportRepository.findByUserIdAndTo(user2.getId(), user1.getId());
        report.ifPresent(reportRepository::delete);
    }
}
