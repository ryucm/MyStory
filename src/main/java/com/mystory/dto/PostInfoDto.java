package com.mystory.dto;

import com.mystory.domain.Post;
import com.mystory.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostInfoDto {
    private Long postId;
    private Member userId;
    private String title;
    private String contents;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


    public PostInfoDto(Post post) {
        this.postId = post.getPostId();
//        this.userId = post.getUser();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
