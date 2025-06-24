package com.controller;

import com.Entity.AppointmentEntity;
import com.Entity.UserEntity;
import com.Repository.AppointmentRepo;
import com.Repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AppointmentController {

    private final AppointmentRepo appointmentRepo;
    private final UserEntityRepository userRepository;
    @GetMapping("/all")
    public ResponseEntity<List<AppointmentEntity>> getAllAppointments() {
        return ResponseEntity.ok(appointmentRepo.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentEntity> getAppointmentsByUser(@PathVariable Long id) {
        AppointmentEntity appointments =  appointmentRepo.findById(id).orElseThrow(() ->new RuntimeException("Not found Appointment") );
        return ResponseEntity.ok(appointments);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<AppointmentEntity> updateAppointment(@PathVariable Long id, @RequestBody AppointmentEntity dto) {
        AppointmentEntity ById = appointmentRepo.findById(id).orElseThrow(() ->new RuntimeException("Not found Appointment") );
        ById.setAppointmentDateTime(dto.getAppointmentDateTime());
        ById.setDoctorName(dto.getDoctorName());
        ById.setReasonForAppointment(dto.getReasonForAppointment());
        ById.setAdditionalNotes(dto.getAdditionalNotes());
        appointmentRepo.save(ById);
        return ResponseEntity.ok(ById);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createAppointment(
            @RequestParam("userId") Long userId,
            @RequestParam("appointmentDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime appointmentDateTime,
            @RequestParam("doctorSpecialization") String doctorSpecialization,
            @RequestParam("doctorName") String doctorName,
            @RequestParam("appointmentMode") String appointmentMode,
            @RequestParam("reasonForAppointment") String reason,
            @RequestParam("previousMedicalConditions") String previousConditions,
            @RequestParam("medications") String medications,
            @RequestParam("allergies") String allergies,
            @RequestParam("insuranceProvider") String insuranceProvider,
            @RequestParam("policyNumber") String policyNumber,
            @RequestParam("paymentMode") String paymentMode,
            @RequestParam("patientConsent") Boolean patientConsent,
            @RequestParam("additionalNotes") String notes,
            @RequestPart(value = "insuranceDocument", required = false) MultipartFile insuranceDocument,
            @RequestPart(value = "medicalReport", required = false) MultipartFile medicalReport,
            @RequestPart(value = "prescription", required = false) MultipartFile prescription,
            @RequestPart(value = "paymentReceipt", required = false) MultipartFile paymentReceipt
    ) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Build and save AppointmentEntity as needed...

        return ResponseEntity.ok("Appointment created!");
    }


    @PostMapping("/create/bulk")
    public ResponseEntity<?> createBulkAppointments(@RequestBody List<AppointmentEntity> appointments) {
        List<String> results = new ArrayList<>();

        for (AppointmentEntity dto : appointments) {
            try {
                Long userId = dto.getUser().getId();
                UserEntity user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                AppointmentEntity appointment = AppointmentEntity.builder()
                        .appointmentDateTime(dto.getAppointmentDateTime())
                        .doctorSpecialization(dto.getDoctorSpecialization())
                        .doctorName(dto.getDoctorName())
                        .appointmentMode(dto.getAppointmentMode())
                        .reasonForAppointment(dto.getReasonForAppointment())
                        .previousMedicalConditions(dto.getPreviousMedicalConditions())
                        .medications(dto.getMedications())
                        .allergies(dto.getAllergies())
                        .insuranceProvider(dto.getInsuranceProvider())
                        .policyNumber(dto.getPolicyNumber())
                        .insuranceDocumentUrl(dto.getInsuranceDocumentUrl())
                        .medicalReportUrl(dto.getMedicalReportUrl())
                        .prescriptionUrl(dto.getPrescriptionUrl())
                        .paymentMode(dto.getPaymentMode())
                        .paymentReceiptUrl(dto.getPaymentReceiptUrl())
                        .patientConsent(dto.getPatientConsent())
                        .additionalNotes(dto.getAdditionalNotes())
                        .user(user)
                        .build();

                appointmentRepo.save(appointment);
                results.add("Appointment created for " + user.getFirstName());
            } catch (Exception e) {
                results.add("Failed: " + e.getMessage());
            }
        }

        return ResponseEntity.ok(results);
    }

}
