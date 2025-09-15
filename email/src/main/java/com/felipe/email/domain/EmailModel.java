package com.felipe.email.domain;

import com.felipe.email.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_EMAIL")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailModel
{
    @Transient
    private final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID emailId;
    private UUID userId;
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String body;
    private LocalDateTime sendDate;
    @Enumerated(EnumType.STRING)
    private EmailStatus status;
}

