# 2-2-1 Spring boot war方式部署

### 1. 增加maven配置

```
     <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>


		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>jstl</artifactId>
		</dependency>
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
			<scope>provided</scope>
		</dependency>

``` 

###    2.0 修改入口代码

```
@SpringBootApplication
public class TongrongRiskScoreApplication extends SpringBootServletInitializer {


	/**
	 * 因为使用war进行部署，所以需要重新cofigure方法
	 * @param application
	 * @return
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TongrongRiskScoreApplication.class);
	}

```

### 3.0 部署到tomcat

将war包改为ROOT.war，然后放大webapps下面。然后启动tomcat，就可以愉快的访问了。
不用增加项目名字哦！！！

