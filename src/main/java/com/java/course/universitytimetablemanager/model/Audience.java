package com.java.course.universitytimetablemanager.model;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "audiences", schema = "university_schedule")
public class Audience {

    @Id
    @SequenceGenerator(
            name = "audiences_sequence",
            sequenceName = "audiences_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "audiences_sequence"

    )
    @Column(name = "location_id")
    private Integer locationId;
    @Column(name = "audiences_name")
    private String audienceName;

    public Audience(Integer locationId, String audienceName) {
        this.locationId = locationId;
        this.audienceName = audienceName;
    }

    public Audience() {

    }

    public Audience(String s) {
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getAudienceName() {
        return audienceName;
    }

    public void setAudienceName(String audiencesName) {
        this.audienceName = audiencesName;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Audience audience = (Audience) o;
        return locationId != null && Objects.equals(locationId, audience.locationId);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Audience{" +
                "location_id=" + locationId +
                ", audiences_name='" + audienceName + '\'' +
                '}';
    }
}
