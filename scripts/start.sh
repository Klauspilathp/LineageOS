#!/bin/bash
# 
# chmod +x start.sh
# 该脚本用于先停止已经启动的应用，再启动新部署的应用程序
# 

PID=$(ps -ef | grep d7c_xxx.jar | grep -v grep | awk '{ print $2 }')

# 如果 PID 不为空则停止已经启动的应用
if [ ! -z "$PID" ]
then
    kill $PID
fi

# 进入 jar 包目录
cd /d7c/xxx || exit 2
nohup java -jar d7c_xxx.jar > log.log 2>&1 &


