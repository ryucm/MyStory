package com.mystory.controller;


import com.mystory.domain.Post;
import com.mystory.domain.User;
import com.mystory.dto.PostInfoDto;
import com.mystory.dto.PostRequestDto;
import com.mystory.security.UserDetailslmpl;
import com.mystory.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    public String index(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
                        Pageable pageable, Model model) {

        Page<Post> list = postService.postList(pageable);
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "index";
    }

    @GetMapping("/post")
    public String view(Long id, Model model) {
        PostInfoDto postInfoDto = postService.postView(id);
        model.addAttribute("post", postInfoDto);
        return "post";
    }

    @GetMapping("/post/modify/{id}")
    public String modify(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", postService.postView(id));
        model.addAttribute("id", id);
        return "postModify";
    }

    @PostMapping("/post/update/{id}")
    public String update(@PathVariable("id") Long id, PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailslmpl userDetails) {
        postService.update(id, postRequestDto, userDetails);
        return "redirect:/";
    }

    @GetMapping("/post/write")
    public String write() {
        return "postWrite";
    }

    @PostMapping("/post/create")
    public String create(PostRequestDto postRequestDto, Model model, @AuthenticationPrincipal UserDetailslmpl userDetailslmpl) {
        User user = userDetailslmpl.getUser();
        postService.create(postRequestDto, user);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/");
        return "message";
    }

    @GetMapping("/post/delete/{id}")
    public String delete(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailslmpl userDetailslmpl) {
        postService.delete(id,userDetailslmpl);
        return "redirect:/";
    }
}
