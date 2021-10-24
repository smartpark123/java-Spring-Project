package project.first.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.first.Manager.SessionConst;
import project.first.domain.Item;
import project.first.domain.Member;
import project.first.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor // final 객체 생성자 자동 생성
@SessionAttributes({"id", "name", "user_id"})
public class MemberController {

    private final MemberService memberService;

    //회원가입 폼 조회
    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberFormDTO());
        return "members/createMemberForm";
    }

    // 회원가입
    @PostMapping("/members/new")
    public String create(@Valid MemberFormDTO form, BindingResult result){
        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Member member = new Member();
        member.setName(form.getUsername());
        member.setUser_id(form.getUser_id());
        member.setPassword(form.getPassword());
        member.setEmail(form.getEmail());
        member.setAddress(form.getAddress());
        member.setMoney(10000);

        //회원가입 로직 수행
        memberService.join(member);

        return "members/SuccessMember";
    }

    @GetMapping("/members/login")
    public String login(Model model){
        model.addAttribute("loginForm", new LoginFormDTO());
        return "members/login";
    }

    @PostMapping("/members/login")
    public String login(@Valid LoginFormDTO form, BindingResult result, HttpServletRequest request){

        if(result.hasErrors()){
            return "members/login";
        }
        Member loginMember = memberService.login(form.getUser_id(),form.getUser_pw());
        if(loginMember == null){
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "members/login";
        }

            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);



            return "redirect:/";
    }

    @PostMapping("/members/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "/members/memberList";
    }

    @GetMapping("/money")
    public String money(@SessionAttribute(SessionConst.LOGIN_MEMBER) Member member, Model model){

        Member user = memberService.findById(member.getId());

        model.addAttribute("member", user);
        return "/members/money";

    }

    @PostMapping("/money")
    public String insertMoney(@RequestParam("payMoney") int money, @SessionAttribute(SessionConst.LOGIN_MEMBER) Member member, Model model){
        Member user = memberService.findById(member.getId());
        memberService.money(member, money, user.getMoney());
        member.setMoney(user.getMoney());
        model.addAttribute("member", user);

        return "/members/SuccessMoney";

    }




    }

