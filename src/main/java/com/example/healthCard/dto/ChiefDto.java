package com.example.healthCard.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiefDto {
  private String id;

  private String emailAddress;

  private String name;

  private String identityType;

  private String identityNumber;

  private String address;

  private long phoneNumber;

  private LocalDateTime createdAt;

  private List<MemberDto> members;
}
