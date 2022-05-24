package com.tcloudsoft.utils.common;

import com.tcloudsoft.utils.JsonSerializer;
import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.utils.ex.TcmsAuthException;
import com.tcloudsoft.utils.response.ResponseData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

@Slf4j
@Data
public class UserVo extends UserInfo{
    private static final long serialVersionUID = -8128458079891262205L;

    public UserVo() {}

    public UserVo(ResponseData<?> userVo) throws TcmsAuthException {
        if (userVo == null) {
            throw new TcmsAuthException("500","用户未登陆或登陆失效");
        }
        Map userMap = JsonSerializer.deserialize(Map.class, JsonSerializer.serialize(userVo));
        if (userMap == null) {
            throw new TcmsAuthException("500","系统解析异常");
        }
        boolean isLoginSuccess = false;
        if (userMap.containsKey("success") && TcloudUtils.isNotEmpty(userMap.get("success")) && (Boolean) userMap.get("success")) {
            // 登陆成功
            if (userMap.containsKey("data")) {
                // 用户对象
                Map usrSourceDataMap = (Map) userMap.get("data");
                this.setId(TcloudUtils.isNotEmpty(usrSourceDataMap.get("id"))?usrSourceDataMap.get("id").toString():"");
                this.setName(TcloudUtils.isNotEmpty(usrSourceDataMap.get("name"))?usrSourceDataMap.get("name").toString():"");
                this.setDeptId(TcloudUtils.isNotEmpty(usrSourceDataMap.get("deptId"))?usrSourceDataMap.get("deptId").toString():"");
                this.setCode(TcloudUtils.isNotEmpty(usrSourceDataMap.get("code"))?usrSourceDataMap.get("code").toString():"");
                this.setStatus(TcloudUtils.isNotEmpty(usrSourceDataMap.get("status"))?Integer.parseInt(usrSourceDataMap.get("status").toString()):null);
                this.setPassword(TcloudUtils.isNotEmpty(usrSourceDataMap.get("password"))?usrSourceDataMap.get("password").toString():"");
                isLoginSuccess = true;
            }
        }
        if (!isLoginSuccess) {
            log.error(JsonSerializer.serialize(userVo));
            throw new TcmsAuthException("500","用户未登陆或登陆失效[01]");
        }
    }

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
