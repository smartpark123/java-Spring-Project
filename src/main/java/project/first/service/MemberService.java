package project.first.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.first.domain.Member;
import project.first.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 객체 생성자 자동 생성
public class MemberService {

    private final MemberRepository memberRepository;

//    @RequiredArgsConstructor 사용 안할 시
//    @Autowired
//    public MemberService(MemberRepository memberRepository){
//        this.memberRepository = memberRepository;
//    }

    @Transactional
    public Long join(Member member){
        memberRepository.save(member);
        return member.getId();
    }

    public Member login(String user_id, String user_pw){
        Member loginList = memberRepository.loginFind(user_id, user_pw);
        if(loginList == null){
            return null;
        }else
            return loginList;

    }

    public List<Member> findMembers(){

        return memberRepository.findAll();
    }


    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public Member money(Member member, int money, int userMoney){
        int TotalMoney = userMoney + money;
        Member find = memberRepository.findById(member.getId());
        find.setMoney(TotalMoney);

        return find;


    }

}
