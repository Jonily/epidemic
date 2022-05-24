package com.tcloudsoft.web.provider.mapper;

import com.tcloudsoft.web.provider.model.Isolation;
import com.tcloudsoft.web.provider.model.IsolationExam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcloudsoft.web.provider.vo.IsolationVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2021-12-09
 */
public interface IsolationExamMapper extends BaseMapper<IsolationExam> {

    @Select("select id ,id_card as idCard, type , creator_time as creatorTime from  t_isolation where id_card = #{idCard}")
    Isolation getIsolation(@Param("idCard")String idCard);

}
