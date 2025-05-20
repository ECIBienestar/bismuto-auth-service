package edu.eci.cvds.auth.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import edu.eci.cvds.auth.models.enums.Specialty;

/**
 * Entity representing staff members in the university wellness system.
 * Extends the base User class with staff-specific attributes.
 * 
 * @author Jesús Pinzón (Team Bismuto)
 * @version 1.1
 * @since 2025-05-09
 */
@Entity
@Table(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Staff extends User {

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Specialty specialty;
}
