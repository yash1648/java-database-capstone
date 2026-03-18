package com.project.back_end.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    @Id
    private String id;
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private List<Medication> medications;
    private String doctorNotes;
    private List<Attachment> attachments;
    private List<String> tags;
    private LocalDateTime createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Medication {
        private String name;
        private String dosage;
        private String frequency;
        private String duration;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Attachment {
        private String fileName;
        private String fileType;
    }
}
