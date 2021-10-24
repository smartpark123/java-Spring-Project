package project.first.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.first.Manager.SessionConst;
import project.first.domain.Item;
import project.first.domain.Member;
import project.first.repository.MemberRepository;
import project.first.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@SessionAttributes({"id","name"})
public class HomeController {

    private final MemberRepository memberRepository;
    private final ItemService itemService;
//    @RequestMapping("/")
//    public String home(){
//        log.info("home controller");
//
//        return "home";
//    }

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        if (session == null) {
            model.addAttribute("login", "false");
            return "home";
        }
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            model.addAttribute("login", "false");
            return "home";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("login", "true");
        model.addAttribute("member", loginMember);
        return "home";
    }

}
