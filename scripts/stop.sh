#!/bin/bash
#
# 该脚本用于停止已经启动的应用
# 

PID=$(ps -ef | grep d7c_xxx.jar | grep -v grep | awk '{ print $2 }')

if [ ! -z "$PID" ]
then
    kill $PID
fi


