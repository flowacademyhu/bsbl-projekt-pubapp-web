package org.flow.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_achievement")
public class UserAchievement {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name ="id", nullable = false)
    private Long id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date created;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updated;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    /*
    @ManyToOne
    @JoinColumn(name = "achievement_id")
    //private Achievement achievement;
    */
}
