version: '3.6'

services:
  api:
    image: 118.126.66.51/wenanguo/little_bird_api:v1.0.12
    command: java -jar /app/app.jar
    environment:
      - PASSENGER_APP_ENV=development
      - TZ=Asia/Shanghai
    volumes:
      - /home/ubuntu/ninepoint/:/app/static/img/
    deploy:
      replicas: 1
      restart_policy:
        condition: on-failure
      placement:
        constraints:                      # 添加条件约束
          - node.hostname==VM-4-118-ubuntu  
      resources:
        limits:
          cpus: "1"
          memory: 1024M
      update_config:
        parallelism: 1 # 每次更新1个副本
        delay: 5s # 每次更新间隔 
        monitor: 10s # 单次更新多长时间后没有结束则判定更新失败
        max_failure_ratio: 0.1 # 更新时能容忍的最大失败率
        order: start-first # 更新顺序为新任务启动优先
    ports:
      - 18087:8080
  