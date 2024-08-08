package com.example.jpashop.parameter;

import com.example.domain.jpashop.Address;
import com.example.domain.jpashop.Member;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
@RequiredArgsConstructor
@Slf4j
public class InitMember {
    private final InitMemberService initMemberService;
    @PostConstruct
    public void init() {
        initMemberService.init();
    }
    @Component
    static class InitMemberService {
        @PersistenceContext
        EntityManager em;
        @Transactional
        public void init() {

            log.info("Initializing member");
//            for(int i = 0; i<100; i++){
//                Address address = new Address("city"+i, "street"+i, "zipcode"+i);
//                Member member = new Member("member"+i, i, address);
//                em.persist(member);
//            }
        }
    }
}
