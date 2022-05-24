package com.tcloudsoft.web.provider.util;

import com.tcloudsoft.utils.TcloudUtils;
import com.tcloudsoft.web.provider.mapper.VisitMapper;
import com.tcloudsoft.web.provider.vo.VisitVo;

public class QueryUtils {

    public static String appendFrom(VisitVo person, VisitMapper visitMapper){
        StringBuffer sb = new StringBuffer();
        if (TcloudUtils.isNotEmpty(person.getFromProvince())){
            sb.append(visitMapper.selectRegionName(person.getFromProvince()));
        }
        if (TcloudUtils.isNotEmpty(person.getFromCity())){
            sb.append(visitMapper.selectRegionName(person.getFromCity()));
        }
        if (TcloudUtils.isNotEmpty(person.getFromDistrict())){
            sb.append(visitMapper.selectRegionName(person.getFromDistrict()));
        }
        if (TcloudUtils.isNotEmpty(person.getFromStreet())){
            sb.append(visitMapper.selectRegionName(person.getFromStreet()));
        }
        if (TcloudUtils.isNotEmpty(person.getFromVillage())){
            sb.append(person.getFromVillage());
        }
        return sb.toString();
    }

    public static String appendTarget(VisitVo person, VisitMapper visitMapper){
        StringBuffer sb = new StringBuffer();
        if (TcloudUtils.isNotEmpty(person.getTargetProvince())){
            sb.append(visitMapper.selectRegionName(person.getTargetProvince()));
        }
        if (TcloudUtils.isNotEmpty(person.getTargetCity())){
            sb.append(visitMapper.selectRegionName(person.getTargetCity()));
        }
        if (TcloudUtils.isNotEmpty(person.getTargetDistrict())){
            sb.append(visitMapper.selectRegionName(person.getTargetDistrict()));
        }
        if (TcloudUtils.isNotEmpty(person.getTargetStreet())){
            sb.append(visitMapper.selectRegionName(person.getTargetStreet()));
        }
        if (TcloudUtils.isNotEmpty(person.getTargetVillage())){
            sb.append(person.getTargetVillage());
        }
        return sb.toString();
    }
}
