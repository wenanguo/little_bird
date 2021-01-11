# little_bird
小鸟


## 测试服
1.[接口地址](http://www.teamyy.cn:18087/swagger-ui/index.html)

2.[数据库字典](http://www.teamyy.cn:18087/api/db_doc)

3.[数据库监控](http://www.teamyy.cn:18087/druid/spring.html)





## 正式服

[公网地址：](http://175.24.154.126:18087/swagger-ui/index.html#/)


仓库

```
docker pull 118.126.66.51/wenanguo/little_bird_api:v1.0.222

docker tag 118.126.66.51/wenanguo/little_bird_api:v1.0.222 ccr.ccs.tencentyun.com/little_bird/api:v1.0.222

docker push ccr.ccs.tencentyun.com/little_bird/api:v1.0.222
```

本地运行

```
docker run -it -p 18080:8080 -e spring.datasource.password='wwwwww' ccr.ccs.tencentyun.com/little_bird/api:v1.0.151



```



```sql

select DATE_FORMAT(gmt_payment,'%Y-%m-%d') as datetime,
(CASE goods_id
WHEN '1' THEN '支付宝-3篇试读'
WHEN '2' THEN '支付宝-1年VIP'
WHEN '3' THEN '苹果内购-3篇试读'
WHEN '4' THEN '苹果内购-1年VIP'
ELSE '其他' END) as goodis_title,count(*)
from lb_orders where trade_status ='TRADE_SUCCESS' and goods_id=4
group by   DATE_FORMAT(gmt_payment,'%Y-%m-%d'),goods_id  order by DATE_FORMAT(gmt_payment,'%Y-%m-%d'),goods_id


select DATE_FORMAT(create_time,'%Y-%m-%d') as '日期',count(*) from sys_user GROUP BY DATE_FORMAT(create_time,'%Y-%m-%d')  order by  DATE_FORMAT(create_time,'%Y-%m-%d') 
```

