# little_bird
小鸟

1.[接口地址](http://www.teamyy.cn:18087/swagger-ui/index.html)

2.[数据库字典](http://www.teamyy.cn:18087/api/db_doc)

3.[数据库监控](http://www.teamyy.cn:18087/druid/spring.html)



正式服仓库

```
docker tag 118.126.66.51/wenanguo/little_bird_api:v1.0.151 ccr.ccs.tencentyun.com/little_bird/api:v1.0.151

docker push ccr.ccs.tencentyun.com/little_bird/api:v1.0.151
```

本地运行

```
docker run -it -p 18080:8080 -e spring.datasource.password='wwwwww' ccr.ccs.tencentyun.com/little_bird/api:v1.0.151


spring.datasource.username
spring.datasource.url
spring.datasource.password

```