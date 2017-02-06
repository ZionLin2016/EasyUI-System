### EasyUI-System
======================================================================

### 项目描述
Spring4 + Spring mvc + Hibernate4 + Maven + EasyUI + 少量Bootstrap的后台权限管理系统<br/>
这是一个基础框架，内部已经实现了泛型的增删改查、预先抓取(可以抓取多级对象)、动态条件查询(QueryFilter)等功能<br/>
使用这个框架做后台，会大大降低你的代码量<br/>
项目包含了通用的Controller方法日志，使用AOP拦截，拦截了所有方法，能知道谁(IP)在什么时候，访问了什么类的什么方法，传递的参数是什么，返回了什么值<br/>
项目使用拦截器做权限控制，可以控制到任何url、任何按钮、菜单，保障你的项目安全<br/>
项目使用javamelody监控dao、service、controller和系统的状况<br/>
项目使用druid监控数据源信息<br/>

------------------------------------------------------
  
### 框架特点

框架使用主流的Spring MVC+Hibernate4整合，基于注解的配置，基本框架都已配置完毕，不需要更改任何XML文件；
框架抽象了BaseDao/BaseService/BaseAction的功能，程序员开发一套增删改查，不需要编写大量代码，建立几个空的接口和实现类，其他全由Base包下的功能实现；
可以实现动态条件查询、分页、排序、和多表级联查询；
上手容易，程序员在此基础上二次开发，不需要配置任何配置文件，只要遵循约定，书写相应注解就可以实现项目90%的功能，针对更复杂的操作，也提供了相应的接口，具体请看BaseDao的实现。

--------------------------------------------------------

### 功能开发顺序
你想要开发一个功能，首先你肯定有表，那么顺序就是
+ 先写Model，将表和对象进行映射(注意@Entity和@Table注解的使用)
+ 编写Dao接口和DaoImpl实现(注意继承BaseDao和实现BaseDaoImpl，并且要增加@Repository注解)
+ 编写Service接口和ServiceImpl实现(注意继承BaseService和实现BaseServiceImpl，并且要增加@Service的注解，里面需要注入你自己的dao)
+ 编写Controller(注意添加@Controller，便于程序自动扫描为控制器)

----------------------------------------------------------
### 界面截图
![image](https://github.com/ButBueatiful/dotvim/raw/master/screenshots/vim-screenshot.jpg)
