package iducs.springboot.kchboard.controller;

import iducs.springboot.kchboard.domain.Board;
import iducs.springboot.kchboard.domain.Member;
import iducs.springboot.kchboard.domain.PageRequestDTO;
import iducs.springboot.kchboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/regform")
    public String getRegform(Model model) {
        model.addAttribute("member", Member.builder().build());
        return "/members/regform";
    }

    @PostMapping("")
    public String postMember(@ModelAttribute("member") Member member, Model model ) {
        memberService.create(member);
        model.addAttribute("member", member);
        return "redirect:/home";
    }

    @GetMapping("")
    public String getMembers(PageRequestDTO pageRequestDTO, Model model, HttpSession session){
        if (session.getAttribute("isadmin") != null) {
            model.addAttribute("list", memberService.readListBy(pageRequestDTO));
            return "/members/members";
        }
        else return "authority/authorityMemberList";
    }

    @GetMapping("/{idx}")
    public String getMember(@PathVariable("idx") Long seq, Model model, HttpSession session){
        if ((memberService.readById(seq).getBlock() == 0L && ((Member)session.getAttribute("login")).getSeq() == seq)
        ||(memberService.readById(seq).getBlock() == 0L && session.getAttribute("isadmin") != null)){
            model.addAttribute("member", memberService.readById(seq));
            return "/members/member";
        }
        else if(memberService.readById(seq).getBlock() == 1L) {
            model.addAttribute(seq);
            return "members/memberlimit";
        }
        else return "authority/authorityMember";
    }

    @PostMapping("/{seq}")
    public String memberBlock(@PathVariable Long seq){
        Member member = memberService.readById(seq);
        if (member.getBlock() == 0L){
            member.setBlock(1L);
            memberService.update(member);
        }
        else{
            member.setBlock(0L);
            memberService.update(member);
        }
        return "members/members";
    }

    @GetMapping("/{idx}/upform")
    public String getUpForm(@PathVariable("idx") Long seq, Model model){
        Member member = memberService.readById(seq);
        model.addAttribute("member", member);
        return "/members/upForm";
    }

    @PutMapping("/{idx}")
    public String putMember(@PathVariable("idx") Long seq, @ModelAttribute("member") Member member, Model model){
        member.setBlock(memberService.readById(seq).getBlock());
        memberService.update(member);
        model.addAttribute(member);
        return "/members/member";
    }

    @DeleteMapping("/{idx}")
    public String deleteMember(@PathVariable Long idx, HttpSession session){
        if (((Member) session.getAttribute("login")).getSeq() == idx) {
            memberService.delete(memberService.readById(idx));
            session.invalidate();
            return "redirect:/home";
        }
        else if(session.getAttribute("isadmin") != null){
            memberService.delete(memberService.readById(idx));
        }
        return "redirect:/home";
    }


    @GetMapping("/login")
    public String getLoginform(Model model){
        model.addAttribute("member", Member.builder().build());
        return "/members/login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("member") Member member, HttpServletRequest request){
        Member dto = null;
        if ((dto = memberService.loginByEmail(member)) != null && dto.getBlock() == 0){
            HttpSession session = request.getSession();
            session.setAttribute("login", dto);
            if (dto.getId().contains("admin")){
                session.setAttribute("isadmin", dto.getId());
                System.out.println(session.getAttribute("isadmin"));
            }
            return "redirect:/home/";
        }
        else if((memberService.loginByEmail(member)) != null && dto.getBlock() == 1){
            return "/members/memberlimit";
        }
        else
            return "/members/loginfail";
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session){
        session.invalidate();
        return "redirect:/home/";
    }
}
