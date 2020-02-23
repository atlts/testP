# testP
基于Spring boot，MyBatis，Redis，MySQL使用SpringMVC框架，实现问答论坛网站。实现了敏感词过滤，登录，发帖，点赞，关注，邮件等功能。
敏感词过滤使用字典树来过滤敏感词
登录使用salt加强，并用MD5进行转码加密
使用Redis实现异步框架，对于点赞，关注，添加问题等事件进行异步得处理
利用拦截器对于HttpRequest进行拦截验证用户身份
对于每个用户使用threadLocal存储用户信息，防止多并发的安全性问题
对于互相关注的消息，直接存在Redis里面便于提取
