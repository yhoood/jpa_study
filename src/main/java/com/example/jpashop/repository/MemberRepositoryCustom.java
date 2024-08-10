package com.example.jpashop.repository;

import com.example.jpashop.dto.MemberDto;
import com.example.jpashop.parameter.MemberSearchParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberDto> searchByBuilder(MemberSearchParameter param);
    List<MemberDto> searchWhereParam(MemberSearchParameter param);
    Long searchCount(MemberSearchParameter param);
    Page<MemberDto> searchPageComplex(MemberSearchParameter param, Pageable pageable);
}
