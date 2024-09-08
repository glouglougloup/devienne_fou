package com.deviennefou.weeklycheck.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "mythic_plus_run_history")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MythicPlusRunHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date weekStart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    DevienneFouCharacter devienneFouCharacter;

    @Enumerated(EnumType.STRING)
    private WeekStatus weekStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date recordedAt;
}
