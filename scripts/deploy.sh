#!/bin/bash
# 
# chmod +x deploy.sh
# 项目部署脚本，该脚本用于：
# 1、从远端拉取项目到本地；
# 2、本地项目打包；
# 3、将本地打包好的 Jar 上传到指定服务器的指定目录下；
# 4、运行指定服务器上指定脚本，如 start.sh。
# 

# 1、从远端拉取项目到本地
echo "======= 进入到本地项目的 git 目录 ======="
cd /d7c/xxx
echo "======= 切换到项目的 git 发布版本 ======="
git checkout d7c_xxx
echo "======= git fetch ======="
git fetch
echo "======= git pull ======="
git pull

# 2、本地项目打包
echo "======= 编译并跳过单元测试 ======="
mvn clean package -Dmaven.test.skip=true

# 3、将本地打包好的 Jar 上传到指定服务器的指定目录下


# 4、运行指定服务器上指定脚本
echo "======= sleep 10s ======="
for i in {1..10}
do 
	echo $i"s" 
	sleep 1s 
done
echo "======= 启动服务 ======="
./start.sh


