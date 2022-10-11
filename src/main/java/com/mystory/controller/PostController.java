package com.mystory.controller;


import com.mystory.domain.Post;
import com.mystory.dto.PostInfoDto;
import com.mystory.dto.PostRequestDto;
import com.mystory.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

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

    @GetMapping("/view")
    public String view(Long id, Model model) {
        PostInfoDto postInfoDto = postService.postView(id);
        model.addAttribute("post", postInfoDto);
        return "view";
    }

    @GetMapping("/view/modify/{id}")
    public String modify(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", postService.postView(id));
        model.addAttribute("id", id);
        return "modify";
    }

    @PostMapping("/view/update/{id}")
    public String update(@PathVariable("id") Long id, PostRequestDto postRequestDto) {
        postService.update(id, postRequestDto);
        return "redirect:/";
    }

    @GetMapping("/write")
    public String write() {

        return "write";
    }
    @PostMapping("/create")
    public String create(PostRequestDto postRequestDto, Model model) {
        postService.create(postRequestDto);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/");
        return "message";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        postService.delete(id);
        return "redirect:/";
    }
}
