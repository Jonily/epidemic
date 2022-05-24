package com.tcloudsoft.web.provider.service;

import com.tcloudsoft.web.provider.model.Isolation;
import com.tcloudsoft.web.provider.model.IsolationExam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IsolationExamService extends IService<IsolationExam> {

    List<IsolationExam> getList(String isolationId,Integer status);

    Isolation getByIdCard (String idCard);


}
