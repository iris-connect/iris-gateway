package de.healthIMIS.iris.public_server.core.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable
{

    @CreatedDate
    @Column(name = "created")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified")
    private LocalDateTime lastModifiedDate;
}
