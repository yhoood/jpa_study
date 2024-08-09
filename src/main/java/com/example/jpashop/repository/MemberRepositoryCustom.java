package com.example.jpashop.repository;

import com.example.jpashop.dto.MemberDto;
import com.example.jpashop.parameter.MemberSearchParameter;

import java.util.List;

public interface MemberRepositoryCustom {
    List<MemberDto> searchByBuilder(MemberSearchParameter param);
    List<MemberDto> searchWhereParam(MemberSearchParameter param);
    Long searchCount(MemberSearchParameter param);
}
