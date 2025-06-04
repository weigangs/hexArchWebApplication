# 六边形web架构
***以领域驱动开发思想设计web应用架构***
## 使用六边形web架构的优点：
- 定位业务代码更快，修改代码引起其它业务代码出错风险低，代码更易维护；因为开发遵循单一职责原则，接口职责清晰，各功能间用包作为边界隔离
- 单元测试，集成测试与系统测试边界清晰；单元测试只需对domain类的各个方法测试，即可覆盖核心业务逻辑
- 无需等数据表设计完成再开发；因为其它层代码都依赖domain，domain不依赖其它层；
- domain核心业务层不受外部环境改变影响；例如外部框架（spring mvc， mybatis等）改变时，domain核心业务逻辑代码无需修改；因为spring mvc和mybatis框架的代码不会出现在domain层

## 缺点：
- 开发新功能前期工作量多，每层都要有自己的entity和响应的类；因为entity，DTO，VO原则上都不推荐功能间共用
- 数据在各层之间流动，需要映射；因为各层对应的entity推荐不复用
- 接口文件较多；因为工程中推荐接口单一职责原则
  
## 先来说传统三层架构：
### 代码层次：
```java 
    // web层
    LoginController.login(SignInDTO)
    // service 业务逻辑层
    LoginService.login(signInDTO)
    // 数据持久层
    UserMapper.queryUser(userCode)
```
### 主要特点
1. controller、service依赖都指向mapper层：
   * 因为controller依赖service或直接依赖mapper层，service层又依赖mapper层
2. 一个类承担多个功能职责：
   * 一个controller类通常会包含多个功能的请求方法，同样一个service类也会负责多个controller类分发的任务，一个mapper供多个service类调用
3. service层单侧代码mock时需判断使用了哪些方法：
   * 同一个service通常由多个不同职责的方法，所以对外部的依赖更多，单独写某个方法的单侧需判断当前方法中具体使用了依赖，然后再mock

### *针对以上三个特点，总结优缺点*
#### 优点：
* 开发结构简单清晰，开发速度快；因为可以直接将entity从mapper层传递到web层渲染页面；
* 文件相对较少；类文件在多个功能间共用；
