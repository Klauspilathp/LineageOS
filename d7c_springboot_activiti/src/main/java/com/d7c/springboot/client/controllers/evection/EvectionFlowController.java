package com.d7c.springboot.client.controllers.evection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.springboot.client.controllers.WebBaseController;

/**
 * @Title: EvectionFlowController
 * @Package: com.d7c.springboot.client.controllers.evection
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: 出差流程控制类
 */
@RestController
@RequestMapping(value = "/evection/flow")
public class EvectionFlowController extends WebBaseController {
    /**
     * 概要：
     * 1、Activiti 7 很多服务方法需要用户具有 ROLE_ACTIVITI_ADMIN 或 ROLE_ACTIVITI_USER（GROUP_users 组） 角色，
     *   角色组以 GROUP_ 开头；
     * 2、对 Activiti 的增、删、改操作要放在 Service 层加上事务操作；
     * 3、Activiti 的查询采用懒加载，要将查询结果组装到 map 等对象中返回，否则会报 Could not write JSON: lazy loading outside command context; 错误。
     * 
     * 测试流程：
     * 1、创建流程图；
     * 2、启动 d7c_springboot_client_oauth2 项目请求登录接口获取授权 token；
     * 3、/resources/processes/* 路径下的流程图会自动进行流程部署；
     * 4、/resources/bpmn/* 路径下的流程图通过请求 /activiti/processDefinition/deploymentLocalProcess 接口进行流程部署；
     *   {
     *      "deploymentName" : "test1",
     *      "deploymentFileName": "evection.bpmn"
     *   }
     * 5、/resources/processes/* 路径已经被 Activiti 使用了，不能再使用 addClasspathResource 方式进行流程部署；
     * 6、流程部署完成后会在 act_re_procdef 流程定义表、act_re_deployment 流程部署信息表、
     *   act_ge_bytearray 流程文件二进制数据表各插入一条数据；
     * 7、调用 /activiti/processInstance/runProcessInstance 接口启动流程，
     *   /activiti/processInstance/startProcessInstance 接口启用流程需要当前登录用户具有 ROLE_ACTIVITI_ADMIN 角色；
     *   采用 POST form-data 方式传入：
     *   {
     *      "processDefinitionKey" : "evectionProcess",
     *      "businessKey": "11111"
     *   }
     * 8、启动流程后会在 act_ru_variable 运行时变量信息表、act_ru_task 运行时任务信息表、act_ru_execution 运行时流程执行实例表、
     *   act_hi_varinst 历史变量表、act_hi_taskinst 历史任务实例表、act_hi_procinst 历史流程实例表、act_hi_detail 历史详情表、
     *   act_hi_actinst 历史节点表中插入数据；
     * 9、调用 /activiti/processInstance/suspendedProcessInstanceByProcessDefinitionIdAndDate
     *   ?processDefinitionId=evectionProcess:5:2992228e-8bb0-11eb-82b3-00e04c221416&suspensionDate=2021-03-25 10:00:00 接口
     *   挂起流程任务，会在 act_ru_timer_job 运行时定时器作业表中插入一条数据，每隔 10 秒运行一次，检查任务是否要被挂起。再次调用接口挂起会再插入一条数据；
     *   如果到达任务挂起时间，会将 act_ru_task 表中的 SUSPENSION_STATE_ 字段设置为 2，act_ru_execution 表中的 SUSPENSION_STATE_ 字段设置为 2，
     *   同时删除 act_ru_timer_job 表中的对应定时任务。执行定时任务中的任务之前会在 act_ru_job 表中插入一条数据，执行完成之后删除；
     * 10、调用 /activiti/processInstance/activateProcessInstanceByProcessDefinitionIdAndDate
     *   ?processDefinitionId=evectionProcess:5:2992228e-8bb0-11eb-82b3-00e04c221416&activationDate=2021-03-25 09:56:00 接口
     *   激活流程任务，会在 act_ru_timer_job 运行时定时器作业表中插入一条数据，每隔 10 秒运行一次，检查任务是否要被激活。到达激活时间会更新 act_ru_task 表
     *   和 act_ru_execution 表；
     * 11、拾取任务；
     * 12、完成任务。
     */

}