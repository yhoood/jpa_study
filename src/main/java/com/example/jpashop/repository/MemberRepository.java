package com.example.jpashop.repository;

import com.example.domain.jpashop.Member;
import com.example.jpashop.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //namedQuery
    List<Member> findByMemberName(@Param("memberName") String memberName);

    //method name query (pk)
    List<Member> findByMemberId(long id);

    //method name query (optional)
    Optional<Member> findByMemberIdAndMemberName(long id,String name);

    //method name query (in)
    List<Member> findByMemberIdInOrAddressCity(List<Long> idList, String city);

    //@Query Annotation (in)
    @Query("select m from Member m where m.memberId in :idList or m.address.city=:city")
    List<Member> findByMemberParam(@Param("idList")List<Long> idList,@Param("city")String city);

    //@Query Dto사용
    @Query("select new com.example.jpashop.dto.MemberDto(m.memberName,m.address.city,m.address.street,m.address.zipcode) from Member m")
    List<MemberDto> findAllMemberDto();

    //join fetch
    @Query("select m from Member m join fetch m.orderList ol")
    List<Member> findFetchSelect();

    //left join fetch
    @Query("select distinct m from Member m left join fetch m.orderList ol")
    List<Member> findLeftFetchSelect();
}
