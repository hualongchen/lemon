package com.lemon.chen;

import com.lemon.chen.AsyncTask.TaskAsync;
import com.lemon.chen.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Study115ApplicationTests {


    @Autowired
    private UserService userService;


    @Autowired
    private TaskAsync taskAsync;

    /**
     * 测试缓存
     */
    @Test
    public void contextLoads() {

        System.out.println(userService.randomInt(1).getUserId());
        System.out.println(userService.randomInt(1).getUserId());

    }


    /**
     * 测试异步调用
     */
    @Test
    public void test()throws Exception{

        long start = System.currentTimeMillis();

        Future<String> task1 = taskAsync.one();
        Future<String> task2 = taskAsync.two();
        Future<String> task3 = taskAsync.three();

        while(true) {
            if(task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }

        long end = System.currentTimeMillis();

        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }

}
