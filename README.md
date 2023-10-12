# TwelveT Fast
基于 Spring Boot 3 实现的twelvet单体服务，抢先体验 Spring 6 版本。

### 要求
- jdk >= 17
- maven >= 3.8

前端源码：https://github.com/twelvet-s/twelvet-ui

## 演示图

<table>
    <tr>
        <td><img src="https://twelvet.cn/assets/images/twelvet/1.png"/></td>
        <td><img src="https://twelvet.cn/assets/images/twelvet/2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://twelvet.cn/assets/images/twelvet/3.png"/></td>
        <td><img src="https://twelvet.cn/assets/images/twelvet/4.png"/></td>
    </tr>
    <tr>
        <td><img src="https://twelvet.cn/assets/images/twelvet/5.png"/></td>
        <td><img src="https://twelvet.cn/assets/images/twelvet/6.png"/></td>
    </tr>
</table>

## 在线体验

- admin/123456

演示地址：[https://cloud.twelvet.cn](https://cloud.twelvet.cn)

## 支持Linux一件Docker启动
内存 > 16
需要自行安装maven、docker、docker-compose、node、yarn
```shell
# mvn
mvn clean && mvn install
# 进入脚本目录
cd ./docker
# 可执行权限
chmod 751 deploy.sh
# 执行启动（按需执行参数，[init|port|base|server|stop|rm]）
# 初始化
./deploy.sh init
# 基础服务
./deploy.sh base
# 启动twelvet
./deploy.sh server
# 启动UI
./deploy.sh nginx
```

## TwelveT微服务交流群

QQ群： [![加入QQ群](https://img.shields.io/badge/985830229-blue.svg)](https://jq.qq.com/?_wv=1027&k=cznM6Q00) 点击按钮入群。

## 🤝鸣谢
感谢jetbrains提供的许可证[![jetbrains](https://cloud.twelvet.cn/jetbrains.png)](https://www.jetbrains.com?from=https://github.com/twelvet-projects/twelvet)
