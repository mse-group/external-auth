# External Auth
这是一个简易的认证鉴权的服务，用于用户注册、用户登录以及Token校验。在微服务场景中，通常会部署一个中心化的认证鉴权服务，
外部用户在访问微服务系统之前，需要先登录并获取访问凭证，然后利用凭证信息去访问微服务系统。目标微服务在收到用户请求后，
会访问认证鉴权服务对用户进行认证鉴权，即判断凭证信息的合法性以及是否有权限访问该微服务。

# 使用场景
在微服务场景中，每个微服务都需要对接认证鉴权服务的工作是冗余，我们可以在集群入口的网关处对API进行统一认证鉴权，
由网关访问认证鉴权服务来决定该用户请求是否放行。

# 部署
## 本地部署
直接运行ExternalAuthApplication类即可

## Docker部署
```
// 构建镜像
docker build -f Dockerfile -t external-auth .

// 运行镜像
docker run -p 8080:8080 external-auth
```

## K8s部署
```
# Deployment
apiVersion: apps/v1
kind: Deployment
metadata:
  name: external-auth
  namespace: default
spec:
  replicas: 1
  selector:
    matchLabels:
      app: external-auth
  template:
    metadata:
      labels:
        app: external-auth
    spec:
      containers:
        - image: registry.cn-hangzhou.aliyuncs.com/mse-ingress/external-auth
          name: main
          ports:
            - containerPort: 8080
---
# Service
apiVersion: v1
kind: Service
metadata:
  name: external-auth
  namespace: default
  labels:
    app: external-auth
spec:
  selector:
    app: external-auth
  ports:
  - protocol: TCP
    name: http
    port: 8080
    targetPort: 8080
  type: LoadBalancer
```

# API
## 用户注册
/users/signup
```
# 请求
curl --location --request POST 'localhost:8080/users/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "yang",
    "password": "123"
}'
```

## 用户登录
/users/login
```
# 请求
curl --location --request POST 'localhost:8080/users/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "yang",
    "password": "123"
}'

# 响应
{
    "status": 200,
    "message": "login success",
    "data": {
        "token": "d727186a-494e-45df-9202-44cfbc1e857f"
    }
}
```

## 校验Token
/validate-token/**
```
# 请求
curl --location --request GET 'localhost:8080/validate-token/test' \
--header 'Authorization: d727186a-494e-45df-9202-44cfbc1e857f'
```

# 其他
本项目只是一个认证鉴权的Demo，目的是展示一个认证鉴权涉及的基本API，用户可以根据业务需求自行修改实现。
