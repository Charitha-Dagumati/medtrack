package org.example.medtrack.scheduler;

import org.example.medtrack.entity.Medicine;
import org.example.medtrack.repository.MedicineRepository;
import org.example.medtrack.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ExpiryScheduler {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private EmailService emailService;

    // Prevent duplicate notifications in same day (in-memory)
    private final Set<Long> notifiedToday = new HashSet<>();

    // Reset at midnight daily
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetNotifications() {
        notifiedToday.clear();
        System.out.println("Notification cache cleared");
    }

    // Runs daily at 9 AM
    @Scheduled(cron = "0 0 9 * * ?")
    public void checkExpiry() {

        List<Medicine> medicines = medicineRepository.findAll();
        LocalDate today = LocalDate.now();

        StringBuilder report = new StringBuilder();
        report.append("===== MEDICINE ALERT REPORT =====\n\n");

        for (Medicine med : medicines) {

            // ---------------- EXPIRY CHECK ----------------
            if (med.getExpiryDate() != null) {

                long daysLeft = ChronoUnit.DAYS.between(today, med.getExpiryDate());

                if (daysLeft <= 30 && daysLeft >= 0) {

                    if (!notifiedToday.contains(med.getId())) {

                        report.append("EXPIRING SOON: ")
                                .append(med.getName())
                                .append(" | Days Left: ")
                                .append(daysLeft)
                                .append("\n");

                        notifiedToday.add(med.getId());
                    }
                }

                if (daysLeft < 0) {
                    report.append("EXPIRED: ")
                            .append(med.getName())
                            .append("\n");
                }
            }

            // ---------------- LOW STOCK ----------------
            if (med.getQuantity() < 10) {
                report.append("LOW STOCK: ")
                        .append(med.getName())
                        .append(" | Qty: ")
                        .append(med.getQuantity())
                        .append("\n");
            }
        }

        // Send only if there is something to report
        if (report.length() > "===== MEDICINE ALERT REPORT =====\n\n".length()) {
            emailService.sendDailySummary(
                    "dagumaticharitha@gmail.com",
                    report.toString()
            );
        }

        System.out.println("Daily medicine check completed");
    }
}