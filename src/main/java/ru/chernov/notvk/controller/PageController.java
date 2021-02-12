package ru.chernov.notvk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chernov.notvk.domain.entity.Post;
import ru.chernov.notvk.domain.entity.User;
import ru.chernov.notvk.service.MessageService;
import ru.chernov.notvk.service.PostService;
import ru.chernov.notvk.service.UserService;

import java.util.HashMap;
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
    private final MessageService messageService;

    @Autowired
    public PageController(UserService userService, PostService postService, MessageService messageService) {
        this.userService = userService;
        this.postService = postService;
        this.messageService = messageService;
    }

    @GetMapping("/feed")
    public String feedPage(@AuthenticationPrincipal User user,
                           Model model) {
        Set<Post> feed = postService.getFeed(user.getId());
        Map<Object, Object> data = new HashMap<>();
        data.put("feed", feed);
        data.put("me", user);

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
        foundUser.formatBirthday();

        Map<Object, Object> data = new HashMap<>();
        data.put("me", user);
        data.put("user", foundUser);
        data.put("userPosts", postService.getUserPosts(foundUser.getId()));

        model.addAttribute("frontendData", data);
        return "user";
    }

    @GetMapping("/me")
    public String myPage(@AuthenticationPrincipal User user,
                         Model model) {
        User me = userService.findById(user.getId());
        me.formatBirthday();

        Map<Object, Object> data = new HashMap<>();
        data.put("me", me);
        data.put("userPosts", postService.getUserPosts(user.getId()));

        model.addAttribute("frontendData", data);
        return "main/me";
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal User user,
                              Model model) {
        Map<Object, Object> data = new HashMap<>();
        data.put("me", userService.findById(user.getId()));

        model.addAttribute("frontendData", data);
        return "main/profile";
    }

    @GetMapping("/messenger")
    public String messenger(@AuthenticationPrincipal User user,
                            Model model) {

        Map<Object, Object> data = new HashMap<>();
        // get all users who user contacted with
        data.put("userList", userService.getAllContacts(user.getId()));
        data.put("me", user);

        model.addAttribute("frontendData", data);
        return "main/messenger";
    }

    @GetMapping("/messenger/{username}")
    public String chat(@PathVariable String username,
                       @AuthenticationPrincipal User user,
                       Model model) {
        User target = userService.findByUsername(username);
        Map<Object, Object> data = new HashMap<>();
        data.put("userList", userService.getAllContacts(user.getId()));
        data.put("messages", messageService.getCorrespondence(target.getId(), user.getId()));
        data.put("me", user);
        data.put("target", target);

        model.addAttribute("frontendData", data);
        return "main/messenger";
    }

    @GetMapping("/subscriptions")
    public String subscriptionsPage(@AuthenticationPrincipal User user,
                                    Model model) {

        Map<Object, Object> data = new HashMap<>();
        data.put("subscriptions", userService.findById(user.getId()).getSubscriptions());
        data.put("me", user);

        model.addAttribute("frontendData", data);
        return "main/subscriptions";
    }

}
