#  Maven 学习笔记


### 1. Maven下载地址

```
下载Maven地址：http://maven.apache.org/download.cgi
下载Jdk地址：http://www.oracle.com/technetwork/java/javaee/downloads/index.html
```

### 2. 配置全局变量

```
vim etc/profile

export M2_HOME=/Users/chenhualong/Documents/dev_tool/apache-maven-3.0.5
export PATH=$PATH:$M2_HOME/bin/
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home
source etc/profile
```

### 3.pom文件浏览

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

  <!--这期都是利用maven4.0协议进行开发-->

  <!--
modelVersion：POM 模型版本 4.0.0 固定
groupId：一般指某个公司或者某个组织的某个项目 比如 org.springframework
artifactId:一般指某个具体项目的某个具体模块 比如 spring-context
Version：项目的版本
Maven 常见命令
Mvn compile 编译
Mvn clean 清空
Mvn test 测试
Mvn package 打包
Mvn install 把项目安装到本地仓库
Mvn 远程仓库地址：http://mvnrepository.com/
  -->
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.zbj.mobile</groupId>
  <artifactId>mobile_test</artifactId>
  <packaging>war</packaging>
  <version>1.0-SNAPSHOT</version> 
</project>

```


###   4.配置公司的私网地址
```

#setting.xml文件
<mirror>
      <id>nexus</id>
      <mirrorOf>*</mirrorOf>
      <url>http://maven.zbjwork.com:8081/nexus/content/groups/public/</url>
    </mirror>
  </mirrors>
  
  
  在小贷公司，用的下面这个。
  
  <mirror>  
  <id>alimaven</id>  
  <name>aliyun maven</name>  
  <url>http://repo.tongrong365.net/repository/maven-public/</url>  
  <mirrorOf>central</mirrorOf>          
</mirror> 


配置用户和组的权限


 <server>  
     <!--releases 连接发布版本项目仓库-->  
      <id>releases</id>  
      <!--访问releases这个私服上的仓库所用的账户和密码-->  
      <username>admin</username>  
      <password>admin123</password>  
    </server> 


    <server>  
    <!--snapshots 连接测试版本项目仓库-->  
      <id>snapshots</id>  
      <!--访问releases这个私服上的仓库所用的账户和密码-->  
      <username>admin</username>  
      <password>admin123</password> 
      </server>

    <server> 
      <id>maven-central</id>  
      <!--访问releases这个私服上的仓库所用的账户和密码-->  
      <username>admin</username>  
      <password>admin123</password> 
      </server>

<server> 
      <id>maven-3rdParty</id>  
      <!--访问releases这个私服上的仓库所用的账户和密码-->  
      <username>admin</username>  
      <password>admin123</password> 
      </server>

 ```
 
 
### 5.增加用户权限推送jar包
```
mvn deploy:deploy-file -DgroupId=tk.mybatis -DartifactId=mapper -Dversion=3.3.9 -Dpackaging=jar -Dfile=C:\Users\lemon\Desktop\tk\mybatis\mapper\3.3.9\mapper-3.3.9.jar -Durl=http://repo.tongrong365.net/repository/maven-3rdParty/ -DrepositoryId=maven-3rdParty


打包到本地仓库

mvn install:install-file -Dfile=jsonplugin-0.34.jar -DgroupId=com.google.json -DartifactId=jsonutil -Dversion=4.0 -Dpackaging=jar



```
###   6. maven的依赖范围

```
classpath 分为三种：编译 classpath , 测试 classpath , 运行 classpath
Scope 选项如下：
Compile:编译依赖范围。默认就是 compile。在编译，测试，运行都有效；
Test：测试依赖范围。仅测试有效； 例如 JUnit；
Provided：已提供依赖范围。编译，测试有效，运行时候无效。例如 servlet-api。
System：系统依赖范围。（了解即可）使用 system 范围的依赖必须通过 sytemPath 指定依赖文件的路径。
Import：导入依赖范围。（了解即可）使用 dependencyManagement 时候，可以导入依赖配置

```

###   7.父项目的继承

```
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.3.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    
```

### 8.利用idea构建多模块maven项目

```
	* 新建maven,不要勾选任何的archetype.然后填写artifactId和groupId。
	* 删除SRC目录，只剩下pom文件即可。
	* 新建module,然后通知不勾选任何的archetype，自己构建目录即可。
	* 例子中，首先加入moudle,这个顺序需要注意，不然构建会报错。
	
	
	 <!--子模块-->
    <modules>
        <module>lemon-maven-module</module>
        <module>lemon-maven-dao</module>
        <module>lemon-maven-service</module>
        <module>lemon-maven-web</module>
    </modules>
    
    
    
     <!--依赖父jar spring boot的相关包-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.6.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    
    
    其中，dao依赖module,service依赖dao,web依赖service. 在web层，我们新建run启动方法，然后配置文件之类的写在web层。
	
	在启动的时候，最好web，把所有的module都依赖上，这样就完全没有问题。
	
	
	
```

###  8.1 打多模块maven项目的jar包

```
先配置web层的pom文件

<build>
        <!-- 为jar包取名 -->
        <finalName>lemon-start</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>1.3.0.RELEASE</version>
            </plugin>
        </plugins>
    </build>
    

配置最外层项目的pom文件



<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>1.3.0.RELEASE</version>
                <configuration><!-- 指定该Main Class为全局的唯一入口 -->
                    <mainClass>com.lemon.maven.Application</mainClass>
                    <layout>ZIP</layout>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal><!--可以把依赖的包都打包到生成的Jar包中-->
                        </goals>
                        <!--可以生成不含依赖包的不可执行Jar包-->
                        <!-- configuration>
                          <classifier>exec</classifier>
                        </configuration> -->
                    </execution>
                </executions>
            </plugin>
            <!--mybatis生成代码-->
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.2</version>
                <configuration>
                    <configurationFile>src/main/resources/MapperGeneratorConfig.xml</configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>5.1.21</version>
                    </dependency>
                    <dependency>
                        <groupId>tk.mybatis</groupId>
                        <artifactId>mapper</artifactId>
                        <version>3.4.0</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
    
    
    然后执行mvn  package  ，就可以打出可执行的包了。注意打包顺序，被依赖的先打。
    
    [INFO] Reactor Summary:
[INFO] 
[INFO] lemon-maven-parent ................................. SUCCESS [  0.876 s]
[INFO] lemon-maven-module ................................. SUCCESS [  0.755 s]
[INFO] lemon-maven-dao .................................... SUCCESS [  0.087 s]
[INFO] lemon-maven-service ................................ SUCCESS [  0.043 s]
[INFO] lemon-maven-web .................................... SUCCESS [  0.444 s]




```



###   9. 常用命令汇总

```
mvn -e 显示详细错误
mvn -U 强制更新snapshot类型的插件或依赖库（否则maven一天只会更新一次snapshot依赖）
mvn -o 运行offline模式，不联网更新依赖
mvn -N仅在当前项目模块执行命令，关闭reactor
mvn -pl module_name在指定模块上执行命令
mvn -ff 在递归执行命令过程中，一旦发生错误就直接退出
mvn -Dxxx=yyy指定java全局属性
mvn -Pxxx引用profile xxx
mvn package 打包，上面已经介绍过了
mvn package -Prelease打包，并生成部署用的包，比如deploy/xxx.tgz
mvn install 打包并安装到本地库
mvn eclipse:eclipse 生成eclipse项目文件
mvn eclipse:clean 清除eclipse项目文件
mvn site 生成项目相关信息的网站
mvn test-compile 编译测试代码
mvn test 运行程序中的单元测试
mvn compile 编译项目
mvn package 打包，此时target目录下会出现maven-quickstart-1.0-SNAPSHOT.jar文件，即为打包后文件
mvn install 打包并安装到本地仓库，此时本机仓库会新增maven-quickstart-1.0-SNAPSHOT.jar文件。
每个phase都可以作为goal，也可以联合，如之前介绍的mvn clean install
mvn install -Dmaven.test.skip=true
mvn clean package deploy -Dmaven.test
.skip=true       //打包到远程仓库
mvn install:install-file -Dfile=jsonplugin-0.34.jar -DgroupId=com.google.json -DartifactId=jsonutil -Dversion=4.0 -Dpackaging=jar  //打包本地仓库


mvn archetype:create ：创建 Maven 项目
mvn compile ：编译源代码
mvn test-compile ：编译测试代码
mvn test ： 运行应用程序中的单元测试
mvn site ： 生成项目相关信息的网站
mvn clean ：清除目标目录中的生成结果
mvn package ： 依据项目生成 jar 文件
mvn install ：在本地 Repository 中安装 jar
mvn eclipse:eclipse ：生成 Eclipse 项目文件
mvn -Dmaven.test.skip=true : 忽略测试文档编译

```



###  10. 搭建maven的私服仓库

- 下载私服程序：http://nexus.sonatype.org/downloads/
-  解压私服程序  tar xzvf nexus-oss-webapp-1.8.0-bundle.tar.gz
- 启动私服程序   ./nexus start 
- 访问私服 ： http://host:8081/nexus
- 登陆： admin/admin123
- 点击Repositories,选择public reposotories,下方选择configuration,选择需要留下的组移动过去
- remote storage location 里面填写; http://repo1.maven.org/maven2/


