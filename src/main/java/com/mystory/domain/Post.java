package com.mystory.domain;


import com.mystory.dto.PostRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends TimeStamped{

    public Post(PostRequestDto postRequestDto, User user) {
        this.user = user;
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

    public Post(String title, String userName, String userId, String password, String contents) {
        this.title = title;
        this.contents = contents;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @ManyToOne()
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = true)
    private String contents;

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
    }

}
