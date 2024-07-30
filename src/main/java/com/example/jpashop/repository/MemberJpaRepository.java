package com.example.jpashop.repository;


import com.example.domain.jpashop.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        List<Member> mbmbers =em.createQuery("select m from Member m",Member.class)
                                .getResultList();
    return mbmbers;

    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.memberName=:name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
