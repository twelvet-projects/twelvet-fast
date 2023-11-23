#!/bin/sh

# 使用说明，用来提示输入参数
usage() {
  echo "Usage: sh 执行脚本.sh [init|port|base|server|nginx|stop|rm]"
  exit 1
}

# 初始化
init() {
  # copy sql
  echo "begin copy sql "
  cp ../data/twelvet.sql ./mysql/db
  cp ../data/twelvet_gen.sql ./mysql/db
  cp ../data/twelvet_job.sql ./mysql/db

  # copy jar
  echo "begin copy twelvet-admin "
  cp ../twelvet-admin/target/twelvet-admin.jar ./twelvet/twelvet-admin/jar
}

# 开启所需端口
port() {
  firewall-cmd --add-port=8080/tcp --permanent
  firewall-cmd --add-port=6379/tcp --permanent
  firewall-cmd --add-port=3306/tcp --permanent
  service firewalld restart
}

# 启动基础环境（必须）
base() {
  docker-compose up -d twelvet-mysql twelvet-redis
}

# 启动程序模块（必须）
server() {
  docker-compose up -d twelvet-admin
}

# 启动nginx（必须）
nginx() {
  if [ ! -d "./twelvet-ui" ];then
    rm -rf ./twelvet-ui

    # 获取前端UI
    git clone https://gitee.com/twelvet/twelvet-ui twelvet-ui

    # 执行打包
    cd ./twelvet-ui && yarn install && yarn run build

    # 移动打包文件
    \cp -rf ./dist/* ../nginx/html/
  else
    echo "前端已初始化"
  fi

  docker-compose up -d twelvet-nginx
}

# 关闭所有环境/模块
stop() {
  docker-compose stop
}

# 删除所有环境/模块
rm() {
  docker-compose rm
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
"init")
  init
  ;;
"port")
  port
  ;;
"base")
  base
  ;;
"server")
  server
  ;;
"nginx")
  nginx
  ;;
"stop")
  stop
  ;;
"rm")
  rm
  ;;
*)
  usage
  ;;
esac

