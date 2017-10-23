//package com.lemon.activity.studyactivity.web;
//
//
//import org.activiti.engine.*;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class StudyOneController {
//
//
//    public static void main(String[] args) {
//
//        //初始化了引擎
//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//
//        System.out.println("processEngine:" + processEngine.toString());
//
//        //引擎提供的各种服务
//
//        //====1.0 这个服务提供了管理与控制deployments（部署）与process definitions（流程定义）的操作
//        //查询引擎已知的部署与流程定义。
//        //暂停或激活部署中的某些流程，或整个部署。暂停意味着不能再对它进行操作，激活是其反操作。
//        //读取各种资源，比如部署中保存的文件，或者引擎自动生成的流程图
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//
//
//        //=====2.0RuntimeService也用于读取与存储process variables（流程变量）
//        //启动流程定义的新流程实例.
//        //在流程实例等待外部触发时使用，以便流程可以继续运行。流程有许多wait states（暂停状态），
//        // RuntimeService服务提供了许多操作用于“通知”流程实例，告知已经接收到外部触发，使流程实例可以继续运行
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//
//
//
//        //=====3.0 查询分派给用户或组的任务
//        //创建standalone（独立运行）任务。这是一种没有关联到流程实例的任务。
//        //决定任务的执行用户（assignee），或者将用户通过某种方式与任务关联。
//        //认领（claim）与完成（complete）任务。认领是指某人决定成为任务的执行用户，也即他将会完成这个任务。完成任务是指“做这个任务要求的工作”，通常是填写某种表单
//
//        TaskService taskService = processEngine.getTaskService();
//
//
//
//        //它用于管理（创建，更新，删除，查询……）组与用户
//        IdentityService identityService = processEngine.getIdentityService();
//
//
//
//        //可以用于读取数据库 表与表原始数据的信息，也提供了对作业（job）的查询与管理操作
//        ManagementService managementService = processEngine.getManagementService();
//
//
//
//        //当执行流程时，引擎会保存许多数据（可以配置），例如流程实例启动时间，谁在执行哪个任务，完成任务花费的事件，每个流程实例的执行路径，等等。
//        // 这个服务主要提供查询这些数据的能力
//        HistoryService historyService = processEngine.getHistoryService();
//
//        //可选服务，经常用不到， 主要是表单操作
//        FormService formService = processEngine.getFormService();
//    }
//}
