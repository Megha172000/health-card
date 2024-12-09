package com.example.healthCard;

import com.example.healthCard.dto.HospitalDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse {
  private int totalPages;
  private int totalElements;
  private boolean first;
  private boolean last;
  private int numberOfElements;
  private int size;
  private List<HospitalDto> content;
  private Pageable pageable;
}
