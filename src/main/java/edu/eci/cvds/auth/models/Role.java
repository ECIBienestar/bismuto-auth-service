package edu.eci.cvds.auth.models;

/**
 * Enum representing different user roles within the system.
 * <p>
 * These roles define the level of access and permissions assigned to a user.
 * Each role corresponds to a specific set of privileges.
 */
public enum Role {
    ADMIN,
    PROFESSOR,
    STUDENT,
    DOCTOR,
    TRAINER,
    MONITOR
}