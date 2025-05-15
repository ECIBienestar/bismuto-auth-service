package edu.eci.cvds.auth.dto;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class ErrorResponseDTOTest {

    @Test
    void builderShouldSetAllFields() {
        LocalDateTime testTime = LocalDateTime.of(2023, 1, 1, 12, 0);
        ErrorResponseDTO dto = ErrorResponseDTO.builder()
                .timestamp(testTime)
                .status(404)
                .error("Not Found")
                .message("Resource not found")
                .path("/api/resource")
                .build();

        assertEquals(testTime, dto.getTimestamp());
        assertEquals(404, dto.getStatus());
        assertEquals("Not Found", dto.getError());
        assertEquals("Resource not found", dto.getMessage());
        assertEquals("/api/resource", dto.getPath());
    }

    @Test
    void builderShouldUseDefaultTimestamp() {
        ErrorResponseDTO dto = ErrorResponseDTO.builder().build();
        assertNotNull(dto.getTimestamp());
        assertTrue(dto.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)) ||
                dto.getTimestamp().isEqual(LocalDateTime.now()));
    }

    @Test
    void builderToStringShouldContainAllFields() {
        ErrorResponseDTO.ErrorResponseDTOBuilder builder = ErrorResponseDTO.builder()
                .status(500)
                .error("Server Error");

        String builderStr = builder.toString();
        assertTrue(builderStr.contains("status=500"));
        assertTrue(builderStr.contains("error=Server Error"));
        assertTrue(builderStr.contains("ErrorResponseDTOBuilder"));
    }

    @Test
    void timestampSetterShouldWorkInBuilder() {
        LocalDateTime customTime = LocalDateTime.of(2023, 6, 15, 10, 30);
        ErrorResponseDTO dto = ErrorResponseDTO.builder()
                .timestamp(customTime)
                .build();

        assertEquals(customTime, dto.getTimestamp());
    }

    @Test
    void buildShouldCreateCompleteObject() {
        ErrorResponseDTO dto = ErrorResponseDTO.builder()
                .status(400)
                .error("Bad Request")
                .message("Invalid input")
                .path("/api/endpoint")
                .build();

        assertNotNull(dto);
        assertNotNull(dto.getTimestamp());
        assertEquals(400, dto.getStatus());
        assertEquals("Bad Request", dto.getError());
        assertEquals("Invalid input", dto.getMessage());
        assertEquals("/api/endpoint", dto.getPath());
    }

    @Test
    void shouldHandleNullFieldsInBuilder() {
        ErrorResponseDTO dto = ErrorResponseDTO.builder()
                .error(null)
                .message(null)
                .path(null)
                .build();

        assertNull(dto.getError());
        assertNull(dto.getMessage());
        assertNull(dto.getPath());
        assertNotNull(dto.getTimestamp());
    }

    @Test
    void equalsShouldReturnTrueForSameObject() {
        ErrorResponseDTO dto = new ErrorResponseDTO();
        assertEquals(dto, dto);
    }

    @Test
    void equalsShouldReturnTrueForEqualObjects() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponseDTO dto1 = new ErrorResponseDTO(now, 400, "Error", "Message", "/path");
        ErrorResponseDTO dto2 = new ErrorResponseDTO(now, 400, "Error", "Message", "/path");
        assertEquals(dto1, dto2);
    }

    @Test
    void equalsShouldReturnFalseForDifferentObjects() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponseDTO dto1 = new ErrorResponseDTO(now, 400, "Error", "Message", "/path");
        ErrorResponseDTO dto2 = new ErrorResponseDTO(now, 404, "Not Found", "Message", "/path");
        assertNotEquals(dto1, dto2);
    }

    @Test
    void equalsShouldReturnFalseForNull() {
        ErrorResponseDTO dto = new ErrorResponseDTO();
        assertNotEquals(null, dto);
    }

    @Test
    void equalsShouldReturnFalseForDifferentClass() {
        ErrorResponseDTO dto = new ErrorResponseDTO();
        assertNotEquals("String", dto);
    }

    // Test hashCode() method
    @Test
    void hashCodeShouldBeEqualForEqualObjects() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponseDTO dto1 = new ErrorResponseDTO(now, 400, "Error", "Message", "/path");
        ErrorResponseDTO dto2 = new ErrorResponseDTO(now, 400, "Error", "Message", "/path");
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void hashCodeShouldBeDifferentForDifferentObjects() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponseDTO dto1 = new ErrorResponseDTO(now, 400, "Error", "Message", "/path");
        ErrorResponseDTO dto2 = new ErrorResponseDTO(now, 404, "Not Found", "Message", "/path");
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void hashCodeShouldHandleNullFields() {
        ErrorResponseDTO dto = new ErrorResponseDTO(null, 0, null, null, null);
        assertDoesNotThrow(dto::hashCode);
    }

    // Test toString() method
    @Test
    void toStringShouldContainAllFields() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponseDTO dto = new ErrorResponseDTO(now, 500, "Error", "Message", "/path");
        String str = dto.toString();

        assertTrue(str.contains("timestamp=" + now));
        assertTrue(str.contains("status=500"));
        assertTrue(str.contains("error=Error"));
        assertTrue(str.contains("message=Message"));
        assertTrue(str.contains("path=/path"));
    }

    @Test
    void toStringShouldHandleNullFields() {
        ErrorResponseDTO dto = new ErrorResponseDTO(null, 0, null, null, null);
        assertDoesNotThrow(dto::toString);
    }

    // Test constructors
    @Test
    void noArgsConstructorShouldInitializeFields() {
        ErrorResponseDTO dto = new ErrorResponseDTO();
        assertNotNull(dto.getTimestamp());
        assertEquals(0, dto.getStatus());
        assertNull(dto.getError());
        assertNull(dto.getMessage());
        assertNull(dto.getPath());
    }

    @Test
    void allArgsConstructorShouldSetAllFields() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponseDTO dto = new ErrorResponseDTO(now, 400, "Error", "Message", "/path");

        assertEquals(now, dto.getTimestamp());
        assertEquals(400, dto.getStatus());
        assertEquals("Error", dto.getError());
        assertEquals("Message", dto.getMessage());
        assertEquals("/path", dto.getPath());
    }

    // Test getters and setters
    @Test
    void settersAndGettersShouldWorkForAllFields() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponseDTO dto = new ErrorResponseDTO();

        dto.setTimestamp(now);
        dto.setStatus(400);
        dto.setError("Error");
        dto.setMessage("Message");
        dto.setPath("/path");

        assertEquals(now, dto.getTimestamp());
        assertEquals(400, dto.getStatus());
        assertEquals("Error", dto.getError());
        assertEquals("Message", dto.getMessage());
        assertEquals("/path", dto.getPath());
    }

    @Test
    void canEqualShouldReturnTrueForSameClass() {
        ErrorResponseDTO dto1 = new ErrorResponseDTO();
        ErrorResponseDTO dto2 = new ErrorResponseDTO();
        assertTrue(dto1.canEqual(dto2));
    }

    @Test
    void canEqualShouldReturnFalseForDifferentClass() {
        ErrorResponseDTO dto = new ErrorResponseDTO();
        assertFalse(dto.canEqual("String"));
    }

    @Test
    void equalsShouldCoverAllBranches() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime later = now.plusHours(1);

        // 1. Objeto base para comparaciones
        ErrorResponseDTO base = new ErrorResponseDTO(now, 400, "Error", "Message", "/path");

        // 2. Test de igualdad consigo mismo (reflexividad)
        assertEquals(base, base);

        // 3. Test con null
        assertNotEquals(null, base);

        // 4. Test con clase diferente
        assertNotEquals("No soy un DTO", base);

        // 5. Test de igualdad con objeto idéntico
        ErrorResponseDTO identical = new ErrorResponseDTO(now, 400, "Error", "Message", "/path");
        assertEquals(base, identical);

        // 6. Test de diferencia por cada campo
        assertNotEquals(base, new ErrorResponseDTO(later, 400, "Error", "Message", "/path"));
        assertNotEquals(base, new ErrorResponseDTO(now, 404, "Error", "Message", "/path"));
        assertNotEquals(base, new ErrorResponseDTO(now, 400, "Different", "Message", "/path"));
        assertNotEquals(base, new ErrorResponseDTO(now, 400, "Error", "Different", "/path"));
        assertNotEquals(base, new ErrorResponseDTO(now, 400, "Error", "Message", "/different"));

        // 7. Test con campos null
        ErrorResponseDTO withNulls = new ErrorResponseDTO(null, 0, null, null, null);
        assertNotEquals(base, withNulls);

        ErrorResponseDTO nullTimestamp = new ErrorResponseDTO(null, 400, "Error", "Message", "/path");
        assertNotEquals(base, nullTimestamp);

        // 8. Test de igualdad cuando ambos tienen campos null
        ErrorResponseDTO allNull1 = new ErrorResponseDTO(null, 0, null, null, null);
        ErrorResponseDTO allNull2 = new ErrorResponseDTO(null, 0, null, null, null);
        assertEquals(allNull1, allNull2);

        // 9. Test de diferencia cuando solo uno tiene campo null
        ErrorResponseDTO partialNull = new ErrorResponseDTO(now, 0, null, null, null);
        assertNotEquals(allNull1, partialNull);
    }
}