package com.tcloudsoft.web.provider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tcloudsoft.web.provider.model.Isolation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tcloudsoft.web.provider.model.IsolationExam;
import com.tcloudsoft.web.provider.vo.IsolationVo;
import com.tcloudsoft.web.provider.vo.VaccinationVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @since 2021-12-06
 */
public interface IsolationService extends IService<Isolation> {

    IPage<IsolationVo> page (String name, String type, Integer status, String deptId, Integer current, Integer pageSize);







}
