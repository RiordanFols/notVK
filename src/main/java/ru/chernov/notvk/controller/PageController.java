package ru.chernov.notvk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chernov.notvk.entity.Post;
import ru.chernov.notvk.entity.User;
import ru.chernov.notvk.service.PostService;
import ru.chernov.notvk.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Pavel Chernov
 */
@Controller
@RequestMapping("")
public class PageController {

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public PageController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/feed")
    public String feedPage(@AuthenticationPrincipal User user,
                           Model model) {
        Set<Post> feed = postService.getFeed(user.getId());
        Map<Object, Object> data = new HashMap<>();
        data.put("feed", feed);

        model.addAttribute("frontendData", data);
        return "main/feed";
    }

    @GetMapping("/user/{username}")
    public String userPage(@PathVariable String username,
                           @AuthenticationPrincipal User user,
                           Model model) {
        User foundUser = userService.findByUsername(username);
        if (foundUser == null)
            return "error/404";
        if (foundUser.equals(user))
            return "redirect:/me";

        Map<Object, Object> data = new HashMap<>();
        data.put("user", foundUser);
        data.put("userPosts", postService.getUserPosts(foundUser.getId()));

        model.addAttribute("frontendData", data);
        return "user";
    }

    @GetMapping("/me")
    public String myPage(@AuthenticationPrincipal User user,
                         Model model) {
        Map<Object, Object> data = new HashMap<>();
        data.put("user", userService.findById(user.getId()));
        data.put("userPosts", postService.getUserPosts(user.getId()));

        model.addAttribute("frontendData", data);
        return "main/me";
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal User user,
                          Model model) {
        return "main/profile";
    }

    @GetMapping("/messages")
    public String messagesPage(@AuthenticationPrincipal User user,
                           Model model) {
        return "main/messages";
    }

    @GetMapping("/subscriptions")
    public String subscriptionsPage(@AuthenticationPrincipal User user,
                                    Model model) {
        return "main/subscriptions";
    }

}
