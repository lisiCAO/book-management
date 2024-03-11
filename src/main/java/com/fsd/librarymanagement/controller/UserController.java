package com.fsd.librarymanagement.controller;

import com.fsd.librarymanagement.entity.Book;
import com.fsd.librarymanagement.entity.User;
import com.fsd.librarymanagement.exception.CustomException;
import com.fsd.librarymanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final PasswordEncoder passwordEncoder; // Injected PasswordEncoder for encoding passwords
    private final UserService userService; // Injected UserService for user-related operations

    /**
     * Constructor
     */
    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model theModel) {
        List<User> theUsers = userService.findAllUsers();
        theModel.addAttribute("users", theUsers);

        return "users/list-users";
    }


    /**
     * Method to display the user creation page
     */
    @GetMapping("/showCreateUserPage")
    public String showLoginPage(Model theModel) {
        theModel.addAttribute("user", new User()); // Add a new User object to the model for form binding
        String role = "";
        theModel.addAttribute("role", role); // Add an empty role string to the model for role selection
        return "/users/user-form";
    }


    @GetMapping("/confirm")
    public String showUserConfirmation() {
        return "/users/user-confirmation"; // 这里假设有一个位于 `templates/users/user-confirmation.html` 的视图
    }

    /**
     * Method to process the user creation form submission
     */
    @PostMapping("/create")
    public String createUser(
            @Valid @ModelAttribute("user") User theUser, BindingResult theBindingResult, @ModelAttribute("role") String role
    ) {
        if (theBindingResult.hasErrors()) { // Check for validation errors in the form
            return "users/user-form"; // Return to the form in case of validation errors
        }
            String encodedPassword = passwordEncoder.encode(theUser.getPassword()); // Encode the user's password
            theUser.setPassword(encodedPassword); // Set the encoded password to the user object

            try {
                userService.save(theUser, role); // Save the user with the specified role using UserService
            } catch (CustomException e) {
                theBindingResult.rejectValue("username", "error.user", "An account already exists for this username."); // Handle custom exception if username already exists
                return "users/user-form"; // Return to the form in case of exception
            }
            return "users/user-confirmation"; // Redirect to a confirmation page after successful user creation
        }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") Long userId, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(userId);

            redirectAttributes.addFlashAttribute("success", "User deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error occurred while deleting user.");
        }
        return "redirect:/leaders";
    }

}
