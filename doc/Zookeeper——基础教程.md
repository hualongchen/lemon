# Zookeeper——基础教程

## 1.安装zookeeper

#### 1.1 下载地址
```
http://zookeeper.apache.org/
```
#### 1.2 传到服务器，然后进行解压.
```
tar zxvf zookeeper-3.4.6.tar.gz 
```
#### 1.3 进行赋权。
```
chown -R hadoop:hadoop /home/study/zookeeper/
```
#### 1.4 增加所需文件夹 
```
mkdir data
mkdir logs
cp zoo_sample.cfg zoo.cfg
dataDir=/home/wusc/zookeeper-3.4.6/data  创建myid文件  修改配置文件
vi myid
```
#### 1.5 进行启动检查
```
./zkServer.sh start   进行启动
./zkServer.sh status  进行状态查询
```
#### 1.6 进行日志检查
```
tail -fn 222 zookeeper.out
```
#### 1.7 zookeeper的配置
```
vi /home/hadoop/.bash_profile

export ZOOKEEPER_HOME=/home/study/zookeeper/
export PATH=$ZOOKEEPER_HOME/bin:$PATH
刷新对应的配置
source  /home/hadoop/.bash_profile
```
#### 1.8 加入防火墙
```
chkconfig iptables on
vi /etc/sysconfig/iptables
增加以下配置
-A INPUT -m state --state NEW -m tcp -p tcp --dport 2181 -j ACCEPT
-A INPUT -m state --state NEW -m tcp -p tcp --dport 2888 -j ACCEPT
-A INPUT -m state --state NEW -m tcp -p tcp --dport 3888 -j ACCEPT

重新启动防火墙
service iptables restart
service iptables status
````

#### 1.9实战案例与Demo
https://github.com/hualongchen/lemon

#### 2.0 **有疑问和兴趣，请添加个人公众号**

![输入图片说明](http://7xordd.com1.z0.glb.clouddn.com/qrcode_for_gh_363af0fc9423_430.jpg "在这里输入图片标题")


