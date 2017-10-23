package com.lemon.activity.studyactivity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;

/**
 * @author :chenhualong
 * @Date : created in 17:40 2017/10/19
 * @Description : 流程定义
 */
public class StudyProcessDefinitionTest {


    /**
     *  00  创建文件bpmn,然后使用工具进行流程画图
     */


    /**
     *  01  启动activitiy-cfg.xml。并将表创建好
     */
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    /**
     *  02  部署流程定义
     */
    @Test
    public void deploymentProcessDefinition(){

        //与流程定义和部署对象相关的Service
        Deployment deployment = processEngine.getRepositoryService()
                //创建一个部署对象
                .createDeployment()
                //添加部署的名称
                .name("陈华龙入门")
                //从classpath的资源中加载，一次只能加载一个文件
                .addClasspathResource("processes/chenhualong.bpmn")
                //从classpath的资源中加载，一次只能加载一个文件
                .addClasspathResource("processes/chenhualong.png")
                //完成部署
                .deploy();
        System.out.println("部署ID："+deployment.getId());
        //陈华龙入门
        System.out.println("部署名称："+deployment.getName());
    }



    /**   03 启动流程实例*/
    @Test
    public void startProcessInstance(){
        //流程定义的key
        String processDefinitionKey = "myProcess_1";
        //与正在执行的流程实例和执行对象相关的Service
        ProcessInstance pi = processEngine.getRuntimeService()
                //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
                .startProcessInstanceByKey(processDefinitionKey);

        //流程实例ID:12501
        //流程定义ID:myProcess_1:1:10004
        System.out.println("流程实例ID:"+pi.getId());
        System.out.println("流程定义ID:"+pi.getProcessDefinitionId());
    }



}
