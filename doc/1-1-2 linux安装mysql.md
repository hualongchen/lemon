#   1-1-3  Linux安装mysql文档

###    1.1  安装mysql源码包

```
sudo apt-get install mysql-server
sudo apt-get install mysql-client
sudo apt-get install libmysqlclient-dev
```
###    1.2安装过程请输入密码 

###  1.3  进行连接尝试
```
mysql -u root -p
然后输入密码
```

###  1.4 mac版本安装
mac是下载一个安装包，然后直接安装即可。
需要增加附属命令，方便操作。
执行后就可以在终端进行输入命令连接

```
alias mysql=/usr/local/mysql/bin/mysql
alias mysqladmin=/usr/local/mysql/bin/mysqladmin
```

###  1.5 开启远程连接

```
mysql 5.7版本找到这个路径
/etc/mysql/mysql.conf.d
注释掉下面这一行
bind-address   = 127.0.0.1

然后登陆mysql，进行修改赋权
update user set host = '%' where user = 'root'; 
Grant all on *.* to 'root'@'%' identified by 'root用户的密码' with grant option;
flush privileges;
大功告成， 本地可以连接远程服务器数据库了
```