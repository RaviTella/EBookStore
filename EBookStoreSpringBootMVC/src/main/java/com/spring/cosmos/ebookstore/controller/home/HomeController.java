package com.spring.cosmos.ebookstore.controller.home;

import com.spring.cosmos.ebookstore.model.book.BookRepositoryAsync;
import com.spring.cosmos.ebookstore.model.book.Response;
import com.spring.cosmos.ebookstore.model.cart.CartService;
import com.spring.cosmos.ebookstore.security.SecuredCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BookRepositoryAsync bookRepository;
    private final CartService cartService;

    @Autowired
    public HomeController(BookRepositoryAsync bookRepository, CartService cartService) {
        this.bookRepository = bookRepository;
        this.cartService = cartService;
    }


    @RequestMapping(value = "/ebooks/index", method = { RequestMethod.GET, RequestMethod.POST })
    public String home(Model model, HttpSession session, @AuthenticationPrincipal SecuredCustomer securedUser) {
        model.addAttribute("response",bookRepository.getBooks(18,1));
        model.addAttribute("cartItemCount", cartService.getNumberOfItemsInTheCart(session.getId()));
          model.addAttribute("customer", securedUser);
        return "index";
    }

    @PostMapping(value = "/ebooks/next")
    @ResponseBody
    public Response next(@RequestBody String continuationToken) {
        Response result=bookRepository.getBooks(continuationToken,6,1);
        return result;
    }


    @GetMapping(value = "/ebooks/login")
    public String login(Model model) {
        return "login";
    }


    @GetMapping(value = "/ebooks/loginError")
    public String loginError(Model model) {
        logger.info("Login failed");
        model.addAttribute("loginFailed", "Email ID or Password is invalid");
        return "login";
    }


    @GetMapping(value = "/ebooks/createAccount")
    public String createAccount(Model model) {
        return "createaccount";
    }
}
