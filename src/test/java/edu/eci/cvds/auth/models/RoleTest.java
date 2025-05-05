package edu.eci.cvds.auth.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    @Test
    public void testEnumValuesPresence() {
        assertNotNull(Role.ADMIN);
        assertNotNull(Role.PROFESSOR);
        assertNotNull(Role.STUDENT);
        assertNotNull(Role.DOCTOR);
        assertNotNull(Role.TRAINER);
        assertNotNull(Role.MONITOR);
    }

    @Test
    public void testValueOf() {
        assertEquals(Role.ADMIN, Role.valueOf("ADMIN"));
        assertEquals(Role.STUDENT, Role.valueOf("STUDENT"));
        assertEquals(Role.MONITOR, Role.valueOf("MONITOR"));
    }

    @Test
    public void testValuesOrder() {
        Role[] roles = Role.values();
        assertEquals(6, roles.length);
        assertArrayEquals(new Role[] {
                Role.ADMIN,
                Role.PROFESSOR,
                Role.STUDENT,
                Role.DOCTOR,
                Role.TRAINER,
                Role.MONITOR
        }, roles);
    }
}
