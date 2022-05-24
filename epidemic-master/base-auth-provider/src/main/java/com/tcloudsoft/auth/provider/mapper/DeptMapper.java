package com.tcloudsoft.auth.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tcloudsoft.auth.provider.model.Dept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @since 2021-07-09
 */
@Repository
public interface DeptMapper extends BaseMapper<Dept> {

    @Select("select max(id) as id from t_dept d where d.id like #{linkId} and length(d.id)=#{length}")
    String getMaxLinkId(@Param("linkId") String linkId, @Param("length") int length);


    @Select("SELECT * FROM `t_dept` where pid =#{deptId}")
    List<Dept> childDept(@Param("deptId")String deptId);

}
