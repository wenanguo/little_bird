#!/bin/bash
 
 
#提示“请输入姓名”并等待30秒，把用户的输入保存入变量name中

read -t 30 -p "请输入发布的vue版本号(如：v1.0.1):" vversion
echo -e "\n"
echo "版本号为:$vversion"

#vue
docker pull 118.126.66.51/wenanguo/little_bird_vue:$vversion

docker tag 118.126.66.51/wenanguo/little_bird_vue:$vversion ccr.ccs.tencentyun.com/little_bird/vue:$vversion

docker push ccr.ccs.tencentyun.com/little_bird/vue:$vversion

echo "成功推送版本为:ccr.ccs.tencentyun.com/little_bird/vue:$vversion"

