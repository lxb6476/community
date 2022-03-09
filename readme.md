# 一个交流社区
基于Spring Boot应用

## 资料
[SpringBoot guides](https://spring.io/guides)  可以查看自己需要集成的功能

## 工具
[Github OAuth](https://docs.github.com/cn/developers/apps/building-oauth-apps/creating-an-oauth-app)
创建Github授权应用


## 学习建议
学习什么内容，要去看官网（其实英文没关系，官网看不懂可以参考一些中文博客，结合起来看）
up主建议直接从官方文档中复制过来用，不要死磕，用熟练了就懂了，hhhhh这种方式可以尝试一下哎。


## 编程建议
1. 将代码模块化，不同的部分放到不同的包中，例如想要提供第三方包的支持，可以创建一个provider包，
存放第三方库的功能支持。
2. 函数参数超过两个，要把其封装成对象来管理，可以放到dto包中（data transfer object） 命名规范可以参考：https://developer.aliyun.com/article/715619
3. 遇到不懂问题，或者有简单思路可以去搜索关键字（不要用包含上下文的语义）