package com.tcloudsoft.web.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tcloudsoft.web.provider.model.Vaccination;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloudsoft.web.provider.vo.VaccinationVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2021-12-06
 */
public interface VaccinationService extends IService<Vaccination> {

    IPage<VaccinationVo> page (String idCard, String brand, Integer type,String deptId, Integer current, Integer pageSize);

    List<VaccinationVo> getList(String idCard, String brand, Integer type,String deptId);

    List<Vaccination> getByQuery(String idCard, String brand);

    Integer count(String deptId,Integer type);

    Integer count(String deptId,Integer startAge,Integer endAge);

    Integer countCode(String deptId,Integer codeStatus);

}
