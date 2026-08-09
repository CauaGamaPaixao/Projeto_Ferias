package br.com.barbershop.api;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.*;
public final class ApiDtos {
 private ApiDtos() {}
 public record NameRequest(@NotBlank @Size(max=100) String name) {}
 public record ServiceRequest(@NotBlank @Size(max=100) String name,@Min(15) @Max(480) int durationMinutes,@NotNull @DecimalMin("0.01") BigDecimal price) {}
 public record BookingRequest(@NotNull Long professionalId,@NotNull Long serviceId,@NotBlank @Size(max=100) String clientName,@NotBlank @Email @Size(max=120) String clientEmail,@NotNull @Future LocalDateTime startAt) {}
 public record BlockRequest(@NotNull Long professionalId,@NotNull @Future LocalDateTime startAt,@NotNull @Future LocalDateTime endAt,@NotBlank @Size(max=200) String reason) {}
 public record AvailabilitySlot(LocalDateTime startAt,LocalDateTime endAt) {}
}
