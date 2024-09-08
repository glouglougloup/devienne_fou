package com.deviennefou.weeklycheck.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "devienne_fou_character")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevienneFouCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String race;
    private String wowClass;
    private String profileUrl;
    private String region;
    private String realm;

    @OneToMany(mappedBy = "devienneFouCharacter",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<MythicPlusRunHistory> runHistoryList;
}
