package ca.fraseric.examscheduler.api.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@jakarta.persistence.Entity
@Table(name = "Room")
@Getter
@Setter
@NoArgsConstructor
public class RoomEntity {
  public enum RoomType {
    CMPT, STANDARD
  }
  @Id
  @NotBlank
  @Setter(AccessLevel.NONE)
  private String roomName;
  @NotNull
  @Enumerated(EnumType.STRING)
  private RoomType roomType;
}

