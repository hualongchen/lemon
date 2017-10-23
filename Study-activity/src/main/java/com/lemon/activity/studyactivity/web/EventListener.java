//package com.lemon.activity.studyactivity.web;
//
//import org.activiti.engine.delegate.event.ActivitiEvent;
//import org.activiti.engine.delegate.event.ActivitiEventListener;
//
//public class EventListener implements ActivitiEventListener {
//
//
//    /**
//     * 监听事件
//     * @param event
//     */
//    @Override
//    public void onEvent(ActivitiEvent event) {
//
//        switch (event.getType()){
//
//
//            case JOB_EXECUTION_SUCCESS:
//                System.out.println("job well done"); break;
//            case JOB_EXECUTION_FAILURE:
//                System.out.println("job has failed"); break;
//            default:
//                System.out.println("EVENT receiverd:" + event.getType());
//        }
//    }
//
//
//
//    @Override
//    public boolean isFailOnException() {
//
//        // onEvent方法中的逻辑并不重要，日志失败异常可以被忽略……
//
//
//        //如果返回false,则是忽略异常
//
//        //如果是true, 异常会上抛送
//        return false;
//    }
//}
