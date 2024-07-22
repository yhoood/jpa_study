package com.example.repository;


import com.example.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(Long id){
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    public List<Member> findName(String name){
        return em.createQuery("select m from Member m where m.memberName=:name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
