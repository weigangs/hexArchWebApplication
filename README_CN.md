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
* 工程结构简单清晰，开发速度快；因为controller,service,mapper层可共用一个entity；
* 对于设计技巧要求不高；因为各层的依赖方向都指向数据持久层；
* 可将不同功能相同代码逻辑放到一个类中，方便复用；
* 适用于功能简单的小型项目；
#### 缺点：
* 不利于人员并行开发；因为controller和service层都依赖mapper层，功能开发前，数据库必须先设计好；
* 任意与业务不相关的框架层改动都要修改业务层代码；因为service层既有ORM框架代码，又有web框架代码；
* 代码维护和测试难度大；因为修改一个被多个controller引用的service时，需要考虑是否影响其它功能；
* 单侧编写和维护难度大；因为依赖的其它类中有较多方法，mock时需查看业务代码具体使用了哪些方法；
  
   ![](threeLayer.png)
## 再说六边形架构：
### 代码层次：
![](hexArch.png)
### 主要特点
1. 各层依赖都指向domain
   * web依赖domain较容易理解；数据持久层的依赖是通过由domain层定义与数据持久层的交互接口，数据持久层主动适配，从而将domain依赖数据持久层的方向倒置；
2. 外部与domain层的交互都定义在对应的port接口中
   * port负责与外部系统交互
3. 代码组织结构清晰；各功能对应的代码都在一个包内
   * 一个功能对应的port，adapter，service，domain都在一个包模块内实现，可借助ArchUnit工具强制校验，各功能间代码不能跨包依赖
4. 各层边界清晰
   * 每一层代码在特定包路径下，如数据持久层代码所在包名：adapter.persist，可拆分子模块将adapter放到一个模块，port放到一个模块，使各层边界更清晰
5. 接口设计遵循单一职责原则
   * 如修改登录代码只需找名称包含Login相关的类，而不会出现login逻辑在名为UserInfoService的接口中
6. 编写单侧mock时不用查看业务代码决定mock哪些方法
   * domain和service层依赖的接口中不会有没有使用的方法，mock更纯粹
7. domain方法更贴近业务场景
   * domain是对业务场景的抽象，方法更能反应现实中业务的操作步骤
8. port层，adapter层，domain层数据传输需要映射
   * 每层都推荐自己的数据模型，这样各层间传递数据就需要映射