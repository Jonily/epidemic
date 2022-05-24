package com.tcloudsoft.auth.provider.service.impl;

import com.tcloudsoft.auth.provider.model.Log;
import com.tcloudsoft.auth.provider.mapper.LogMapper;
import com.tcloudsoft.auth.provider.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuolin.Huang
 * @since 2021-09-08
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
