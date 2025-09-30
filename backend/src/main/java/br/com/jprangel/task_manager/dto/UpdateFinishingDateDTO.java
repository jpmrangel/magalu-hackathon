package br.com.jprangel.task_manager.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateFinishingDateDTO {
  private LocalDateTime finishingDate;
}
