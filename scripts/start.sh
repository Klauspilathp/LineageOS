#!/bin/bash
#
# 该脚本用于先停止已经启动的应用，再启动新部署的应用程序
# 

PID=$(ps -ef | grep d7c_xxx.jar | grep -v grep | awk '{ print $2 }')

if [ ! -z "$PID" ]
then
    kill $PID
fi

cd /d7c/xxx || exit 2
nohup java -jar d7c_xxx.jar > log.log 2>&1 &


