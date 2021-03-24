package com.glch.base.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface SequencesMapper {
    @SelectProvider(type = SequencesMapper.Sequences.class ,method = "querySeq")
    public String querySeq(String seqName);

    class Sequences {
        public String querySeq(String seqName) {
            String sql = "select " + seqName + ".nextval from dual" ;

            return sql ;
        }
    }
}
