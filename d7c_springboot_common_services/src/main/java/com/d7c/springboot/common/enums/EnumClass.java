package com.d7c.springboot.common.enums;

import java.util.HashMap;
import java.util.Map;

import com.d7c.plugins.core.enums.HttpStatus;
import com.d7c.plugins.core.enums.RequestMethodEnum;
import com.d7c.springboot.common.enums.sys.LevelEnum;
import com.d7c.springboot.common.enums.sys.LogTypeEnum;
import com.d7c.springboot.common.enums.sys.LoginStatusEnum;
import com.d7c.springboot.common.enums.sys.MenuFuncEnum;
import com.d7c.springboot.common.enums.sys.MenuTypeEnum;
import com.d7c.springboot.common.enums.sys.OrgTypeEnum;
import com.d7c.springboot.common.enums.sys.SexEnum;
import com.d7c.springboot.common.enums.sys.SourceEnum;
import com.d7c.springboot.common.enums.sys.StatusEnum;
import com.d7c.springboot.common.enums.sys.SuccessFailEnum;
import com.d7c.springboot.common.enums.sys.UserTypeEnum;
import com.d7c.springboot.common.enums.sys.VisibleEnum;
import com.d7c.springboot.common.enums.sys.YesNoEnum;

/**
 * @Title: EnumClass
 * @Package: com.d7c.springboot.common.enums
 * @author: 吴佳隆
 * @date: 2020年4月25日 下午2:11:58
 * @Description: 枚举类信息
 */
public class EnumClass {

    public static final Map<String, String[]> ENUM_MAP = new HashMap<String, String[]>() {
        private static final long serialVersionUID = -7109232505711464149L;
        {
            put(LevelEnum.class.getSimpleName(), new String[]{"组织机构级别", LevelEnum.class.getName()});
            put(LoginStatusEnum.class.getSimpleName(), new String[]{"登录状态", LoginStatusEnum.class.getName()});
            put(LogTypeEnum.class.getSimpleName(), new String[]{"日志操作类型", LogTypeEnum.class.getName()});
            put(MenuFuncEnum.class.getSimpleName(), new String[]{"菜单功能", MenuFuncEnum.class.getName()});
            put(MenuTypeEnum.class.getSimpleName(), new String[]{"菜单类型", MenuTypeEnum.class.getName()});
            put(OrgTypeEnum.class.getSimpleName(), new String[]{"机构类型", OrgTypeEnum.class.getName()});
            put(SexEnum.class.getSimpleName(), new String[]{"性别", SexEnum.class.getName()});
            put(SourceEnum.class.getSimpleName(), new String[]{"请求来源", SourceEnum.class.getName()});
            put(StatusEnum.class.getSimpleName(), new String[]{"数据状态", StatusEnum.class.getName()});
            put(SuccessFailEnum.class.getSimpleName(), new String[]{"成功/失败", SuccessFailEnum.class.getName()});
            put(UserTypeEnum.class.getSimpleName(), new String[]{"用户类型", UserTypeEnum.class.getName()});
            put(VisibleEnum.class.getSimpleName(), new String[]{"显示状态", VisibleEnum.class.getName()});
            put(YesNoEnum.class.getSimpleName(), new String[]{"是否", YesNoEnum.class.getName()});
            put(HttpStatus.class.getSimpleName(), new String[]{"HTTP 请求状态", HttpStatus.class.getName()});
            put(RequestMethodEnum.class.getSimpleName(), new String[]{"请求方式", RequestMethodEnum.class.getName()});
        }
    };

}