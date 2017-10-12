# Dubbo—— dubboAdmin基础教程

## 1.安装容器tomcat

#### 1.1 下载地址
```
http://tomcat.apache.org/
```

#### 1.2 解压war到ROOT目录
```
unzip dubbo-admin-2.5.3.war -d ROOT
```

#### 1.3 修改配置文件

```
修改密码和用户
vi ROOT/WEB-INF/dubbo.properties
```

#### 1.4 启动与使用

```
./startup.sh start
加入开机启动
vim /etc/rc.local

su - hadoop -c '/home/study/tomcat/dubboadmin-tomcat/bin/startup.sh start'

强杀进程
 ps -aux | grep java
 kill -s 9 2644

```

#### 1.5 实战案例与Demo
https://github.com/hualongchen/lemon

#### 1.6 **有疑问和兴趣，请添加个人公众号**

![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/qrcode_for_gh_363af0fc9423_430.jpg "在这里输入图片标题")


