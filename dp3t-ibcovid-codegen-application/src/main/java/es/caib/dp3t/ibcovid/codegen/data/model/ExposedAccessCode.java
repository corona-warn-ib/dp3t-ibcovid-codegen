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
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "t_exposed_access_code")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExposedAccessCode implements Serializable {
    private static final long serialVersionUID = 7026731172876252772L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "accessCode", nullable = false)
    private String accessCode;

    @Basic
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Basic
    @Column(name = "onsetDate", nullable = false)
    private LocalDate onsetDate;

    @Basic
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "expire_at", nullable = false)
    private LocalDateTime expireAt;

    @Basic
    @Column(name = "activated_at", nullable = false)
    private LocalDateTime activatedAt;

}
