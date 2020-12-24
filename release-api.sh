#!/bin/bash
 
 
#提示“请输入姓名”并等待30秒，把用户的输入保存入变量name中
read -t 30 -p "请输入发布的API版本号(如：v1.0.1):" tversion
echo -e "\n"
echo "版本号为:$tversion"

#api
docker pull 118.126.66.51/wenanguo/little_bird_api:$tversion

docker tag 118.126.66.51/wenanguo/little_bird_api:$tversion ccr.ccs.tencentyun.com/little_bird/api:$tversion

docker push ccr.ccs.tencentyun.com/little_bird/api:$tversion

echo "成功推送版本为:ccr.ccs.tencentyun.com/little_bird/api:$tversion"
