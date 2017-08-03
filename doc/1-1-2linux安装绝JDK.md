# 1-1-2  Linux安装jdk文档

###  1.0  下载JDK安装包

> http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

###  1.2上传到服务器
> rz ,然后选择对应的jdk文件上传即可

###  1.3 解压文件夹
> sudo tar -xvf jdk-8u131-linux-x64.tar

###  1.4 安装命令
> sudo apt install gedit

###  1.5 设置环境变量
```
vim  ~/.bashrc
最后增加几行内容
export JAVA_HOME=/usr/local/jdk1.8.0_131  
export JRE_HOME=${JAVA_HOME}/jre  
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib  
export PATH=${JAVA_HOME}/bin:$PATH

立即刷新
source ~/.bashrc
```

###  1.6 测试是否安装成功
> java -version


###  1.7 MAC版本的安装

> sudo vim etc/profile

```
最后增加几行内容
export JAVA_HOME=/usr/local/jdk1.8.0_131  
export JRE_HOME=${JAVA_HOME}/jre  
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib  
export PATH=${JAVA_HOME}/bin:$PATH
```

> 刷新
source etc/profile







