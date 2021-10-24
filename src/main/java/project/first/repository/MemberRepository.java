package project.first.repository;

import org.springframework.stereotype.Repository;
import project.first.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    // 회원 가입
    public void save(Member member){
        em.persist(member);
    }




    public Member findById(Long id){
        return em.find(Member.class, id);
    }

    public Member loginFind(String user_id, String user_pw){

        return em.createQuery("select m from Member m where m.user_id = :user_id and m.password = :user_pw",
                Member.class)
                .setParameter("user_id",user_id).setParameter("user_pw", user_pw)
                .getSingleResult();
    }

    public List<Member> findAll(){

        return em.createQuery("select m from Member m", Member.class).getResultList();
    }




}
