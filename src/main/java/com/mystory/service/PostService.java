package com.mystory.service;

import com.mystory.domain.Post;
import com.mystory.dto.PostInfoDto;
import com.mystory.dto.PostRequestDto;
import com.mystory.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> postList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public PostInfoDto postView(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NullPointerException("해당 POST가 존재하지 않습니다."));

        PostInfoDto postInfoDto = new PostInfoDto(post);
        return postInfoDto;
    }

    @Transactional
    public void create(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.save(post);
    }

    @Transactional
    public void update(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("없다 새킫야"));
        post.update(postRequestDto);
        postRepository.save(post);
    }
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
