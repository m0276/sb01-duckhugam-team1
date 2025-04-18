package com.codeit.duckhu.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

  @NotNull
  private UUID id;

  @NotBlank
  private String content;

  @NotNull
  private int rating;

  @NotNull
  private int likeCount;

  @NotNull
  private int commentCount;

  private boolean likedByMe;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

}
