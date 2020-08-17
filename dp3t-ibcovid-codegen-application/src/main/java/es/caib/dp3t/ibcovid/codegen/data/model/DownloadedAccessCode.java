package es.caib.dp3t.ibcovid.codegen.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "RDV_DOWNLOADED_ACCESS_CODE")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownloadedAccessCode {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOWNLOADED_ACCESS_CODE_SEQ")
    @SequenceGenerator(sequenceName = "rdv_downloaded_access_code_id", allocationSize = 1, name = "DOWNLOADED_ACCESS_CODE_SEQ")
    private Long id;

    @Basic
    @Column(name = "accessCode", nullable = false)
    private String accessCode;

    @Basic
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "activated_at")
    private LocalDateTime activatedAt;

}
