package com.codeit.duckhu.comments;

import java.time.Instant;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

  @Mapping(target = "userNickname" , expression = "java(userName(comment))")
  @Mapping(target = "userId", expression = "java(userId(comment))")
  @Mapping(target = "reviewId", expression = "java(reviewId(comment))")
  abstract CommentDto toDto(Comment comment);

  UUID reviewId(Comment comment){
    return comment.getReview().getId();
  }

  UUID userId(Comment comment){
    return comment.getUser().getId();
  }

  String userName(Comment comment){
    return comment.getUser().getNickname();
  }

}