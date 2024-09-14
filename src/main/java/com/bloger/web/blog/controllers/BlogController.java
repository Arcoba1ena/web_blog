package com.bloger.web.blog.controllers;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.ui.Model;
import com.bloger.web.blog.models.PostEntity;
import com.bloger.web.blog.models.CommentsEntity;
import org.springframework.stereotype.Controller;
import com.bloger.web.blog.services.RegistrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.bloger.web.blog.repository.PostEntityRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.bloger.web.blog.repository.CommentsEntityRepository;

@Controller
public class BlogController {
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private PostEntityRepository postEntityRepository;

    @Autowired
    private CommentsEntityRepository commentsEntityRepository;

    //просмотр статей
    @GetMapping("/blogs")
    public String blogPage(Model model) {
        Iterable<PostEntity> postEntities = postEntityRepository.findAll();
        model.addAttribute("posts", postEntities);
        return "blogs";
    }

    //добавление статьи
    @GetMapping("/blog/add")
    public String blogAddGetPage(Model model) {
        model.addAttribute("page", "Написать статью");
        System.out.println("/blog/add" + registrationService.getAllCredentials());
        if (!registrationService.getAllCredentials().isEmpty()) {
            for (var cred : registrationService.getAllCredentials()) {
                if (cred.get("isAuthentication").getAsBoolean()) {
                    return "blog-add";
                }
            }
        }
        return "auth";
    }

    @PostMapping("/blog/add")
    public String blogAddPostPage(
            @RequestParam String title,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam String author,
            @RequestParam String comment,
            Model model) {
        PostEntity postEntity = new PostEntity(title, text, tag, author, comment);
        postEntityRepository.save(postEntity);
        return "redirect:/blogs";
    }


    @GetMapping("/blog/{id}")
    public String blogUserDetailsPage(@PathVariable(value = "id") long userId, Model model) {
        if (!registrationService.getAllCredentials().isEmpty()) {
            for (var cred : registrationService.getAllCredentials()) {
                if (cred.get("isAuthentication").getAsBoolean()) {
                    if (!postEntityRepository.existsById(userId)) {
                        return "redirect:/blogs";
                    }
                    Optional<PostEntity> postEntity = postEntityRepository.findById(userId);
                    List<PostEntity> resultList = new ArrayList<>();
                    postEntity.ifPresent(resultList::add);
                    model.addAttribute("post", resultList);
                    return "blog-details";
                }
            }
        }
        return "auth";
    }

    //редактирование статьи
    @GetMapping("/blog/{id}/edit")
    public String blogEditPage(@PathVariable(value = "id") long userId, Model model) {
        if (!postEntityRepository.existsById(userId)) {
            return "redirect:/blogs";
        }
        Optional<PostEntity> postEntity = postEntityRepository.findById(userId);
        List<PostEntity> resultList = new ArrayList<>();
        postEntity.ifPresent(resultList::add);
        model.addAttribute("post", resultList);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogAddPostUpdatePage(

            @PathVariable(value = "id") long id,
            @RequestParam String title,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam String comment,
            Model model) {
        PostEntity postEntity = postEntityRepository.findById(id).orElseThrow();
        postEntity.setTitle(title);
        postEntity.setText(text);
        postEntity.setTag(tag);
        postEntity.setComment(comment);
        postEntityRepository.save(postEntity);
        return "redirect:/blogs";
    }

    //удаление статьи
    @PostMapping("/blog/{id}/remove")
    public String blogAddPostDeletePage(@PathVariable(value = "id") long id, Model model) {
        PostEntity postEntity = postEntityRepository.findById(id).orElseThrow();
        postEntityRepository.delete(postEntity);
        return "redirect:/blogs";
    }

    //комментировать статью
    @GetMapping("/blog/{id}/comments/add")
    public String blogAddGetPage(@PathVariable(value = "id") long id, Model model) {
        if (!postEntityRepository.existsById(id)) {
            return "redirect:/blogs";
        }
        Optional<PostEntity> postEntity = postEntityRepository.findById(id);
        System.out.println(id);
        List<PostEntity> resultList = new ArrayList<>();
        postEntity.ifPresent(resultList::add);
        model.addAttribute("post", resultList);
        return "blog-comment-add";
    }

    @GetMapping("/comments/user/{userId}")
    public String getCommentsByUserId(@PathVariable("userId") Long userId, Model model) {
        List<CommentsEntity> comments = commentsEntityRepository.findByUserId(userId);
        model.addAttribute("comments", comments);
        model.addAttribute("userId", userId);
        return "comments-list"; // Название HTML-шаблона для отображения списка комментариев
    }

    @PostMapping("/comment/add")
    public String commentAddPage(
            @RequestParam(value = "userId") long id,
            @RequestParam Long userId,
            @RequestParam String comment,
            Model model) {
        CommentsEntity commentsEntity = new CommentsEntity(userId, comment);
        commentsEntity.setUserId(id);
        commentsEntity.setComment(comment);
        commentsEntityRepository.save(commentsEntity);
        return "redirect:/blogs";
    }
}