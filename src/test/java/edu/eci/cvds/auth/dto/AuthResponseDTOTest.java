package edu.eci.cvds.auth.dto;

import edu.eci.cvds.auth.models.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseDTOTest {

    @Test
    void shouldCreateWithBuilder() {
        AuthResponseDTO dto = AuthResponseDTO.builder()
                .token("testToken")
                .refreshToken("refreshToken")
                .type("Bearer")
                .id("123")
                .fullName("Test User")
                .email("test@example.com")
                .role(Role.STUDENT)
                .build();

        assertNotNull(dto);
        assertEquals("testToken", dto.getToken());
        assertEquals("refreshToken", dto.getRefreshToken());
        assertEquals("Bearer", dto.getType());
        assertEquals("123", dto.getId());
        assertEquals("Test User", dto.getFullName());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals(Role.STUDENT, dto.getRole());
    }

    @Test
    void shouldHaveDefaultType() {
        AuthResponseDTO dto = new AuthResponseDTO();
        assertEquals("Bearer", dto.getType());
    }

    @Test
    void shouldSetAndGetAllFields() {
        AuthResponseDTO dto = new AuthResponseDTO();

        dto.setToken("newToken");
        dto.setRefreshToken("newRefresh");
        dto.setType("Custom");
        dto.setId("456");
        dto.setFullName("New User");
        dto.setEmail("new@example.com");
        dto.setRole(Role.ADMINISTRATOR);

        assertEquals("newToken", dto.getToken());
        assertEquals("newRefresh", dto.getRefreshToken());
        assertEquals("Custom", dto.getType());
        assertEquals("456", dto.getId());
        assertEquals("New User", dto.getFullName());
        assertEquals("new@example.com", dto.getEmail());
        assertEquals(Role.ADMINISTRATOR, dto.getRole());
    }

    @Test
    void shouldHaveAllArgsConstructor() {
        AuthResponseDTO dto = new AuthResponseDTO(
                "token123",
                "refresh123",
                "Bearer",
                "789",
                "Another User",
                "another@example.com",
                Role.MEDICAL_STAFF
        );

        assertEquals("token123", dto.getToken());
        assertEquals("refresh123", dto.getRefreshToken());
        assertEquals("Bearer", dto.getType());
        assertEquals("789", dto.getId());
        assertEquals("Another User", dto.getFullName());
        assertEquals("another@example.com", dto.getEmail());
        assertEquals(Role.MEDICAL_STAFF, dto.getRole());
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        AuthResponseDTO dto = new AuthResponseDTO();
        assertNull(dto.getToken());
        assertNull(dto.getRefreshToken());
        assertEquals("Bearer", dto.getType());
        assertNull(dto.getId());
        assertNull(dto.getFullName());
        assertNull(dto.getEmail());
        assertNull(dto.getRole());
    }

    @Test
    void shouldImplementEqualsAndHashCode() {
        AuthResponseDTO dto1 = AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("refresh1")
                .id("1")
                .build();

        AuthResponseDTO dto2 = AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("refresh1")
                .id("1")
                .build();

        AuthResponseDTO dto3 = AuthResponseDTO.builder()
                .token("different")
                .refreshToken("different")
                .id("2")
                .build();

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, "some string");

        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void shouldImplementToString() {
        AuthResponseDTO dto = AuthResponseDTO.builder()
                .token("t")
                .refreshToken("r")
                .id("1")
                .build();

        String toString = dto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("token=t"));
        assertTrue(toString.contains("refreshToken=r"));
        assertTrue(toString.contains("id=1"));
    }

    @Test
    void shouldHandleNullFieldsInEquals() {
        AuthResponseDTO dto1 = new AuthResponseDTO();
        AuthResponseDTO dto2 = new AuthResponseDTO();

        assertEquals(dto1, dto2);

        dto1.setToken("token");
        assertNotEquals(dto1, dto2);

        dto2.setToken(null);
        assertNotEquals(dto1, dto2);

        dto2.setToken("token");
        assertEquals(dto1, dto2);
    }

    @Test
    void equalsShouldCoverAllBranches() {
        // Setup base object
        AuthResponseDTO base = AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("refresh1")
                .type("Bearer")
                .id("1")
                .fullName("Name")
                .email("email@test.com")
                .role(Role.STUDENT)
                .build();

        // Test equality with same object
        assertEquals(base, base);

        // Test equality with equal object
        AuthResponseDTO same = AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("refresh1")
                .type("Bearer")
                .id("1")
                .fullName("Name")
                .email("email@test.com")
                .role(Role.STUDENT)
                .build();
        assertEquals(base, same);

        // Test inequality cases
        assertNotEquals(base, null);
        assertNotEquals(base, "Not a DTO");

        // Field-by-field inequality tests
        assertNotEquals(base, AuthResponseDTO.builder()
                .token("different")
                .refreshToken("refresh1")
                .type("Bearer")
                .id("1")
                .fullName("Name")
                .email("email@test.com")
                .role(Role.STUDENT)
                .build());

        assertNotEquals(base, AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("different")
                .type("Bearer")
                .id("1")
                .fullName("Name")
                .email("email@test.com")
                .role(Role.STUDENT)
                .build());

        // Test null field cases
        AuthResponseDTO withNullToken = AuthResponseDTO.builder()
                .token(null)
                .refreshToken("refresh1")
                .build();
        assertNotEquals(base, withNullToken);

        AuthResponseDTO bothNullToken = AuthResponseDTO.builder()
                .token(null)
                .refreshToken("refresh1")
                .build();
        assertEquals(withNullToken, bothNullToken);
    }

    @Test
    void hashCodeShouldBeConsistent() {
        AuthResponseDTO dto1 = AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("refresh1")
                .build();

        AuthResponseDTO dto2 = AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("refresh1")
                .build();

        // Equal objects should have equal hash codes
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Consistency check
        assertEquals(dto1.hashCode(), dto1.hashCode());

        // Test with null fields
        AuthResponseDTO nullDto = AuthResponseDTO.builder()
                .token(null)
                .refreshToken(null)
                .build();
        assertDoesNotThrow(nullDto::hashCode);
    }

    @Test
    void toStringShouldContainAllFields() {
        AuthResponseDTO dto = AuthResponseDTO.builder()
                .token("testToken")
                .refreshToken("testRefresh")
                .type("Bearer")
                .id("123")
                .fullName("Test Name")
                .email("test@test.com")
                .role(Role.ADMINISTRATOR)
                .build();

        String str = dto.toString();
        assertNotNull(str);
        assertTrue(str.contains("testToken"));
        assertTrue(str.contains("testRefresh"));
        assertTrue(str.contains("Bearer"));
        assertTrue(str.contains("123"));
        assertTrue(str.contains("Test Name"));
        assertTrue(str.contains("test@test.com"));
        assertTrue(str.contains("ADMINISTRATOR"));
    }

    @Test
    void builderShouldCreateCompleteObject() {
        AuthResponseDTO.AuthResponseDTOBuilder builder = AuthResponseDTO.builder()
                .token("bToken")
                .refreshToken("bRefresh")
                .type("bType")
                .id("bId")
                .fullName("bName")
                .email("bEmail")
                .role(Role.MONITOR);

        AuthResponseDTO built = builder.build();

        assertEquals("bToken", built.getToken());
        assertEquals("bRefresh", built.getRefreshToken());
        assertEquals("bType", built.getType());
        assertEquals("bId", built.getId());
        assertEquals("bName", built.getFullName());
        assertEquals("bEmail", built.getEmail());
        assertEquals(Role.MONITOR, built.getRole());
    }

    @Test
    void builderToStringShouldShowAllFields() {
        AuthResponseDTO.AuthResponseDTOBuilder builder = AuthResponseDTO.builder()
                .token("builderToken")
                .id("builderId");

        String builderStr = builder.toString();
        assertNotNull(builderStr);
        assertTrue(builderStr.contains("builderToken"));
        assertTrue(builderStr.contains("builderId"));
        assertTrue(builderStr.contains("AuthResponseDTO.AuthResponseDTOBuilder"));
    }

    @Test
    void hashCodeShouldBeConsistentAndCorrect() {
        AuthResponseDTO dto1 = AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("refresh1")
                .id("id1")
                .build();

        AuthResponseDTO dto2 = AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("refresh1")
                .id("id1")
                .build();
        assertEquals(dto1.hashCode(), dto2.hashCode(), "Equal objects must have equal hash codes");

        int initialHash = dto1.hashCode();
        assertEquals(initialHash, dto1.hashCode(), "Hash code should be consistent");

        AuthResponseDTO nullDto = AuthResponseDTO.builder()
                .token(null)
                .refreshToken(null)
                .id(null)
                .build();
        assertDoesNotThrow(nullDto::hashCode, "Hash code should handle null fields");

        AuthResponseDTO differentDto = AuthResponseDTO.builder()
                .token("different")
                .refreshToken("different")
                .id("different")
                .build();
        assertNotEquals(dto1.hashCode(), differentDto.hashCode(),
                "Different objects should have different hash codes");

        AuthResponseDTO base = AuthResponseDTO.builder()
                .token("t")
                .refreshToken("r")
                .type("Bearer")
                .id("i")
                .fullName("f")
                .email("e")
                .role(Role.ADMINISTRATOR)
                .build();

        int baseHash = base.hashCode();

        assertNotEquals(baseHash, AuthResponseDTO.builder()
                .token("X")
                .refreshToken("r")
                .type("Bearer")
                .id("i")
                .fullName("f")
                .email("e")
                .role(Role.ADMINISTRATOR)
                .build().hashCode(), "Token should affect hash code");
    }

    @Test
    void equalsAndHashCodeShouldFollowContract() {
        AuthResponseDTO a = AuthResponseDTO.builder()
                .token("t")
                .refreshToken("r")
                .id("1")
                .build();

        AuthResponseDTO b = AuthResponseDTO.builder()
                .token("t")
                .refreshToken("r")
                .id("1")
                .build();

        AuthResponseDTO c = AuthResponseDTO.builder()
                .token("t")
                .refreshToken("r")
                .id("1")
                .build();

        assertEquals(a, a);

        assertEquals(a, b);
        assertEquals(b, a);

        assertEquals(a, b);
        assertEquals(b, c);
        assertEquals(a, c);

        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a.hashCode(), a.hashCode());

        assertNotEquals(a, null);
    }

    @Test
    void equalsShouldCoverAllCases() {
        AuthResponseDTO base = AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("refresh1")
                .type("Bearer")
                .id("user1")
                .fullName("John Doe")
                .email("john@example.com")
                .role(Role.STUDENT)
                .build();

        AuthResponseDTO same = AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("refresh1")
                .type("Bearer")
                .id("user1")
                .fullName("John Doe")
                .email("john@example.com")
                .role(Role.STUDENT)
                .build();

        assertEquals(base, same, "Objetos idénticos deben ser iguales");
        assertEquals(base, base, "Objeto debe ser igual a sí mismo");

        assertNotEquals(base, AuthResponseDTO.builder()
                .token("different")
                .refreshToken("refresh1")
                .type("Bearer")
                .id("user1")
                .fullName("John Doe")
                .email("john@example.com")
                .role(Role.STUDENT)
                .build(), "Diferente token debe hacer desiguales");

        assertNotEquals(base, AuthResponseDTO.builder()
                .token("token1")
                .refreshToken("different")
                .type("Bearer")
                .id("user1")
                .fullName("John Doe")
                .email("john@example.com")
                .role(Role.STUDENT)
                .build(), "Diferente refreshToken debe hacer desiguales");

        assertNotEquals(base, null, "Comparación con null debe ser false");

        AuthResponseDTO allNulls = AuthResponseDTO.builder().build();
        assertNotEquals(base, allNulls, "Comparación con objeto con campos null");

        assertNotEquals(base, "No soy un DTO", "Comparación con diferente tipo");
    }
}