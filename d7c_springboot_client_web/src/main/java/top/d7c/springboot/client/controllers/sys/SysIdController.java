package top.d7c.springboot.client.controllers.sys;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.d7c.springboot.client.controllers.WebBaseController;
import top.d7c.springboot.common.dos.sys.SysId;
import top.d7c.springboot.common.services.sys.SysIdService;

/**
 * @Title: SysIdController
 * @Package: top.d7c.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年7月19日 下午2:02:14
 * @Description: d7c系统_主键操作 Controller
 */
@Controller
@RequestMapping(value = "/sys/id")
public class SysIdController extends WebBaseController {
    /**
     * d7c系统_主键操作 Service 实现
     */
    @Resource(name = "sysIdServiceImpl")
    private SysIdService sysIdService;

    /**
     * @Title: index
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午2:03:22
     * @Description: 主键管理首页
     * @param page
     * @return String
     */
    @RequestMapping(value = "/index")
    @RolesAllowed("sys_id:index")
    public String index(Model model) {
        List<SysId> dataList = sysIdService.listBy(null);
        model.addAttribute("dataList", dataList);
        return "sys/id/id_index";
    }

}
