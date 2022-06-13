# 模块介绍
- com.uqierp.access 项目请求拦截器模块
- com.uqierp.bean 项目bean模块
- com.uqierp.config 项目配置模块
- com.uqierp.controller 项目对外接口模块
- com.uqierp.bean.Customer 项目dao模块
- com.uqierp.rabbitmq mq消息队列模块
- com.uqierp.redis 项目缓存模块
- com.uqierp.result 接口统一返回结果模块
- com.uqierp.service 项目业务模块
- com.uqierp.shiro 角色权限模块
- com.uqierp.util 项目工具类模块

# 采用docker Tomact打包方式
docker_project_name='docker hub登录名称/first-project';
project_name='check-tools-1.0.0'; #项目名称
project_version='1.0.0'; #版本号

echo "--------------------删除旧版镜像-----------------"
docker stop ${project_name};
docker rm ${project_name};
docker rmi ${project_name};
echo "--------------------创建镜像---------------------"
docker build -t ${project_name} .;
echo "--------------------查看镜像---------------------"
docker images;

docker run -d -p 8080:8080 {project_name} # 启动项目

docker ps #查看新的镜像

echo "--------------------登陆 docker------------------"
docker login || ! echo '登录失败' || exit;
echo "--------------------创建${docker_project_name}:${project_version}分支---------------"
docker tag check-tools-1.0.0 ${docker_project_name}:${project_version};
echo "--------------------创建完成--------"
echo "--------------------上传至docker hub 仓库--------"
docker push ${docker_project_name}:${project_version} || ! echo '上传失败' || exit;
echo "--------------------上传完成-------"
