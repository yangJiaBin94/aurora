# 曙光系统

​	本项目是以学习实践为目的，与朋友们自主研发，编码，完善的项目。将自身所学实际运用到项目中的一个实践，从中总结经验提升自我专业水平。

项目会不定时更新，我们会在项目中实践各种中间件，并发，微服务，分布式，设计模式等相关使用，也许运用并不规范或应用方式有问题，但我们会在学习过程中进行改正。

### 项目技术方向

​	项目构成以springBoot springCloud微服务架构为核心，会集成springCloud中的多数中间件，包括但不限于（gateway，feign，eureka，sentinel，hystrix，ribbon，druid等等）我们会持续学习并集成新的技术到其中。

### 项目业务结构

##### 	选用风控为主体的业务进行学习与实践，项目结构为：

​		calculation（计算中心）-- 服务

​		common（通用模块）-- jar

​		data（数据中心）-- 服务

​		eureka（注册中心）-- 服务

​		gateway（全局网关）-- 服务

​		loan（风控服务的客户端，仅简单模拟客户端交互） -- 服务

​		risk（风控服务）-- 服务

​		mybatis-generator（mybatis逆向工程）-- 工具

### 风控系统介绍

​	此项目风控所属业务场景即为借贷类风控，即对用户申请贷款进行风控业务。

##### 1.风控业务含义

​	风险控制是指风险管理者采取各种措施和方法，消灭或减少风险事件发生的各种[可能性](https://baike.baidu.com/item/可能性/3411242)，或风险控制者减少[风险](https://baike.baidu.com/item/风险/2833020)事件发生时造成的损失。（百度百科）

##### 2.风控技术维度总结

​	风控从技术的维度进行描述，本人总结为：对某**主体**（多数情况为用户，或商品等），根据其画像（基本信息，标签，资料，特征）分析其相关业务风险程度，输出一个结果（好/坏，或者评分，或者拒绝/通过）给到客户端以提供建议。或直接采用其结果形成自动化风控流程。

##### 3.风控构成

​	构成此风控项目的基本元素有：

##### 	3.1 场景

​		场景即风控这个流程所在的业务场景，即在哪个阶段使用的风控。比如借款场景（用户申请借款，通过风控来判断用户的质量，是否符合放款资格），提额场景（用户是否符合提升其借款额度的标准），认证场景（此场景为校验场景，多数项目中放到了用户app端进行校验，但其实际职责属于风控，比如用户身份证号码校验，用户手机号码校验等）。在技术层面场景则作为一个标识字段存在。

##### 	3.4 ABtest

​		ABtest是一种策略分流模式，业务中应用需求场景为拿出一部分用户执行其他的策略集，以尝试新策略集的效果。ABtest是配置在场景下，一个场景对应多个ABtest。这些ABtest都会配置一个流量百分比，总和为100%。即这些ABtest各自分配了多少流量。ABtest由策略组成，策略在下文3.3中会做解释。

##### 	3.3策略

​		策略指的是对主体（用户）的风控方式，比如借款场景中的用户我希望风控的严格一些，所以风控人员会针对这个场景下的用户再多种维度下进行审核、校验其资格比如先看其年龄是否是18岁以上，再看其月收入是否达到3000，是否有房贷车贷等等。我把这种校验、审核的步骤过程定义为策略。即场景一中的用户，风控所用的策略为策略1（年龄，月收入，房贷车贷），场景二中的用户所用策略为策略2（职业，婚否，性别...）。当然同一场景下的用户是可以用多个策略进行风控的（即会输出多个结果，一个策略输出一个结果）。

​		策略是有类型的，其类型的区分主要在其输出的结果上体现，本项目中策略的结果只有两种类型：1.通过/拒绝/复审（会给出一个明确的决策结果）2.分数（输出一个分数，客户端根据分数进行决策）。

##### 	3.4规则

​		规则是整个风控最基本的元素，策略中提到的（年龄，月收入，房贷车贷）等等是用户的基本资料，单单靠基本信息是无法输出一个策略结果的，必须有一个基于基本信息的表达式的计算才会形成一个结果。比如评分类型的策略（年龄，月收入，房贷车贷），实际描述为：年龄>18记20分，月收入>3000记30分，有放点车贷记30分，用户最终的策略结果则为这些表达式输出的分数之和。**那么这些表达式即为规则**。

​		规则同样也有两种结果类型，与策略相同，区别为分数或者决策结果。

​		规则还有两种基本结构类型：1.简单规则 2.复杂规则。简单规则就是上边描述的最基本的规则，复杂规则为多个简单规则的集合体。比如复杂规则A为：年龄>18 &&（且） 月收入>3000 通过。或复杂规则B为：年龄<=18 ||（或） 月收入<=3000 拒绝 。复杂规则A与复杂规则B其实描述的用户群体相同，只是描述的角度不同。

##### 	3.5指标

​		指标可以理解为提供给规则进行描述的用户特征，即：年龄>18记20分，年龄就称作一个指标。当然指标大多数情况并不是单指用户的基本信息这么简单，多数情况下是通过用户的基本信息进行计算出的某个结果，比如：用户手机中一个月内收到的短信数量。

##### 	3.6风控中的元素关系

​		介绍完风控中的基本元素，这里总结一下这些基本元素的关系。

​		指标为规则的依据。多个规则组成策略，而策略应用在场景下。

​		若有配置ABtest则在策略与场景的关联中多一层ABtest的关系，多个策略组成ABtest，多个ABtest组成场景。

##### 	3.7 策略的结果输出定义

​		1.上述中策略的结果有两种类型，一种为决策结果（之后称作结果模式：通过/拒绝/复审），一种为分数（之后称作评分模式）。策略的结果即组成其规则的结果的集成。我们先从规则的结果进行梳理。

​		2.规则接结果同策略结果一样，有两种模式。但规则还有一种类型维度：简单规则和复杂规则。

​		3.复杂规则由简单规则组成，构成复杂规则的简单规则必须都为同一种类型（要么全部为结果模式，要么全部为评分模式）。

​		4.结果模式的复杂规则由结果模式的简单规则集组成，其结果不关心简单规则的结果（通过/拒绝），只关心简单规则是否命中（具体形式可配置：例如全命中则命中，任意命中则命中。**注意！命中并不代表通过**），输出结果的规则为：

​				4.1 其子规则结果只要有一个复审则此复杂规则结果为复审（忽略配置）。

​				4.2 根据复杂规则配置来决定其结果计算规律（先根据如下两个配置：全命中则命中，任意命中则命中。获取其是否命中状态，再根据其表达式获取命中结果是通过/拒绝/复审）。

​		结果模式的复杂规则由评分模式的简单规则组成，输出结果的规则则由此复杂规则的表达式再进行一次计算，例如：简单规则1输出分数20，简单规则2输出分数30，则总分数为20+30=50。复杂规则表达式为value>60 通过。value为总分数，不满足表达式，则未命中规则，输出拒绝。

​		**规则的结果依据表达式结果，表达式结果为真，则命中，反之则未命中，再根据其配置（命中通过/命中拒绝/命中复审）来得到其真正的执行结果。**

​		5.评分模式的复杂规则只能由评分模式的简单规则组成。其输出结果为组成其简单规则的分数结果之和。

​		6.策略的结果根据其组成的规则结果决定，结果模式的策略固定按照：规则中只要有复审结果，则策略结果为复审。所有规则结果为通过，则策略结果为通过。若规则中有结果为拒绝，则策略结果为拒绝。评分模式的策略结果为其所有规则结果的分数之和。

​		**7.组成评分模式策略的只能是评分模式规则，组成结果模式策略的只能是结果模式的规则。**

​		PS：规则命中则输出该规则所配置的命中结果，未命中则输出对应未命中结果，未命中结果配置关系为：

```java
 //命中通过 未命中拒绝
    PASS("pass", "reject"),
    //命中拒绝 未命中通过
    REJECT("reject", "pass"),
    //命中复审 未命中通过
    REVIEW("review", "pass"),
    ;
```

其配置在枚举类com.aurora.risk.enums.RuleHitResultEnum中。



##### 	3.8举例

​		场景：场景A

​		场景A应用策略：策略A（结果模式）

​		策略A应用规则：复杂规则A（结果模式，命中逻辑为任意命中则命中，命中则通过），复杂规则B（结果模式，表达式为value>60 通过），简单规则C（married==false 拒绝 结果模式）

​		复杂规则A组成：简单规则a1（age>18 通过 结果模式），简单规则a2（sex==“男” 拒绝 结果模式）

​		复杂规则B组成：简单规则b1（city==“杭州” 30分 评分模式），简单规则b2（job = “程序员” 50分 评分模式）		

​		用户资料：age=20 ，sex =“女” ，city=“北京”，job=“程序员”，married=true。

​		此用户进行风控则策略输出结果为拒绝。



### 风控流程以及功能分布

##### 	1.功能分布

##### 		**核心功能分布为三个服务：**

##### 			1.1风控主业务（risk）

​		主要控制了风控的主流程，属于风控业务的主体，入口出口都在此服务中。支持并发调用，异步回调处理，异步策略并行，规则按序执行不做非必要的调用，节约成本（即若先执行的规则已经给出拒绝结果，则后续规则不再执行）。规则，场景，策略灵活配置。调用异常熔断等等。

##### 			1.2 数据中心（data）

​		从客户端或其他地方同步用户数据，方式有接收数据的推送，或去其他服务主动进行拉取。其核心功能为存储数据，提供数据。

##### 			1.3 计算引擎（calculation）

​		指标的计算以及三方服务的调用都在此服务中处理。根据业务不同以及指标本身的性质，提供离线计算和实时计算功能，支持异步计算，缓存复用，并发处理，指标分组计算，批量计算等。

##### 		其他功能：

##### 			1.4 通用包（common）

​		此模块是以jar包形式提供给其他服务作为依赖，包内有各个服务中接口使用的request以及response实体，通用util工具类，通用expection，枚举enum等等。

##### 			1.5 注册中心（eureka）

​		简单的提供注册中心的作用，eureka-server01，eureka-server02模拟了eureka集群。

##### 			1.6 全局网关（gateway）

​		网关服务与注册中心以及feign的结合使用将各个服务间的依赖做了解耦，即所有服务的feign请求全部请求到网关服务，再由网关服务进行分发，限流，日志收集记录等工作。

##### 			1.7 借款客户端（loan）

​		此服务仅模拟了客户端作用，提供的作用有采集用户基本信息数据推送到数据中心以及调用风控对用户进行风控，接收风控结果。

##### 	2.风控流程

##### 			2.1 数据采集

​		首先客户端（loan）要推送用户数据到数据中心（data），这个是前提必要条件，数据中心有用户的数据，才能对用户进行风控流程。

##### 			2.2 入口 

​		风控业务（risk）接收风控请求，根据构建风控流程上下文：

```java
risk/verify/v1  //风控主流程入口
```

```java
//根据参数构建风控流程上下文
RiskContextInfo riskContextInfo = CommonUtil.transferDate(request, RiskContextInfo.class);
```

##### 		2.3 初始化前校验

```java
//初始化前校验（校验场景配置，商户配置等等）
riskApplicationContext.verifyBefore(riskContextInfo);
```

##### 		2.4 初始化

```java
//初始化（初始化风控订单，构建场景下策略链，处理ABtest分流配置，策略下规则链构建等一系列准备工作）
riskApplicationContext.init(riskContextInfo);
```

##### 		2.5 初始化后校验（深度校验）

```java
//初始化后校验（深度校验，校验异步模式是否合理等）
riskApplicationContext.verifyAfter(riskContextInfo);
```

##### 		2.6 执行

​		前期工作准备完成校验通过则开始执行，这里看调用模式，异步模式则直接返回约定code，等待处理完成进行回调客户端。同步则继续执行。

```java
//执行
riskApplicationContext.execute(riskContextInfo);

//异步模式返回null 等待执行完成后回调客户端
if (riskContextInfo.isAsync()) {
            return null;
        } else {
            //清除风控上下文缓存
            riskApplicationContext.clearContextCache(riskContextInfo);
            //构建返回参数返回到客户端
            return riskApplicationContext.buildRiskResult(riskContextInfo);
        }
```

##### 		2.7 异步说明

​		流程中异步同步的概念分为两个地方：

##### 			2.7.1 风控执行模式异步

​				风控执行模式同步：意思是客户端的风控请求，是等待风控结果计算完成同步返回给客户端。

​				风控执行模式异步：意思是客户端的风控请求先接收一个默认结果，等风控服务计算出结果之后，再通过约定好的接口进行回调通知给客户端。这种情况节省资源消耗，客户端可以在风控执行的时候去做其他的事情，不必阻塞在风控这一步骤。正常情况会根据客户端的请求参数中的 isAsync 字段进行判断是否执行异步模式，但是有一种情况必须为异步模式执行，就是客户端当前请求的场景下配置的策略中的规则有异步规则（异步规则指此规则所需指标的计算需要调用三方服务进行获取并计算，并且这个三方服务的数据获取规则是异步形式）时。若客户端要求同步模式，但其场景下配置有异步规则，则会在深度校验（2.5步骤）时抛出异常。

##### 			2.7.2 策略执行模式异步

​				策略执行模式同步：意思是当前策略链按照顺序执行，执行完一个策略，再执行下一个策略。

​				策略执行模式异步：意思是当前策略链并发异步执行，则启用多个线程并发执行各个策略。

​				此配置在产品场景关联表中 isAsync 字段进行配置。

### 计算引擎指标计算流程以及功能分布

#### 功能分布

##### 1.异步执行指标集合计算

​	支持异步指标的计算，即异步回调通知风控指标结果。是否异步执行取决于要计算的指标中是否包含异步指标（异步指标含义为需要调用异步的三方业务来获取三方提供的数据，用其提供的数据进行计算的指标为异步指标）。

​	指标执行器：没一个指标都有一个专门的指标执行器，指标执行器提供指标值。

​	分组执行器：将指标进行分组计算是必要的（比如会有多个指标使用同一数据源进行雷同的运算，例如：用户一天内收到的短信数量，用户一个月内收到的短信数量，用户三个月内收到的短信数量...）如果分别进行计算的话则浪费系统资源，底层需要循环多次进行统计，所以将此类指标归为同一组，使用分组执行器一次将组内所有指标全部计算出来。

​	支持缓存复用，可以对指标进行配置，使其在某一段时间内不必重复计算，多次收到风控业务请求时直接提供上一次计算的结果。此功能分为两层复用深度：第一首先会从redis缓存中获取，若缓存过期失效，则从执行记录中获取（mysql）。

#### 主要流程代码及概述

##### 1. 构建上下文信息

```java
CalculationContextInfo contextInfo = calculationContext.buildContextInfo(request);
```

##### 2. 执行

##### 	2.1 获取要计算指标的集合

```java
List<IndexInfo> indexInfoList = contextInfo.getIndexInfoList();
```

##### 	2.2 过滤掉已经执行完成的指标 并按其分组执行器className进行分组

```java
Map<String, List<IndexInfo>> indexGroupMap = indexInfoList.stream()
        .filter(e -> !IndexExecuteStatusEnum.DONE.equals(e.getStatusEnum()))
        .collect(Collectors.groupingBy(IndexInfo::getGroupClassName));
```

##### 	2.3 根据执行模式异步或同步执行

```java
//异步执行
if (contextInfo.getIsAsync()) {
    calculationThreadPool.execute(() -> {
        execute(contextInfo, indexGroupMap);
    });
} else {
    //同步执行
    execute(contextInfo, indexGroupMap);
}
```

##### 	2.4 分组异步执行指标执行器

```java
CountDownLatch groupLatch = new CountDownLatch(indexGroupMap.size());
//按组执行指标执行器
indexGroupMap.forEach((groupActuatorName, indexList) -> calculationThreadPool.execute(() -> {
    //执行分组执行器
    if (!groupActuatorName.equals(DefaultIndexGroupActuator.class.getName())) {
        IndexActuator groupActuator = IndexActuatorFactory.getActuator(groupActuatorName);
        if (groupActuator != null) {
            groupActuator.execute(contextInfo);
        }
    }
    //执行当前组内指标执行器
    CountDownLatch indexLatch = new CountDownLatch(indexList.size());
    indexList.forEach(e -> calculationThreadPool.execute(() -> {
        IndexActuator actuator = IndexActuatorFactory.getActuator(e.getClassName());
        if (actuator != null) {
            actuator.execute(contextInfo);
        }
        indexLatch.countDown();
    }));
    try {
        indexLatch.await();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    groupLatch.countDown();
}));
try {
    groupLatch.await();
} catch (InterruptedException e) {
    e.printStackTrace();
}
```

​	2.5 保存执行上下文记录到mysql，清除上下文缓存，回调风控业务通知结果

```java
if (contextInfo.getIsAsync()) {
    //若所有指标执行完成
    if (checkIndexIsAllDone(contextInfo.getIndexInfoList())) {
        //保存执行上下文记录
        saveRecord(contextInfo);
        //清除上下文缓存
        String key = RedisKeyUtil.getContextCacheRedisKey(contextInfo.getRiskOrderNo());
        RBucket<CalculationContextInfo> bucket = redissonClient.getBucket(key);
        bucket.deleteAsync();
        //回调客户端结果
        GetIndexResponse response = buildResponse(contextInfo);
        indexCallBackService.receiveIndex(CommonUtil.transferDate(response, ReceiveIndexRequest.class));
    }
} else {
    //保存执行上下文记录
    saveRecord(contextInfo);
}
```

### 相关流程图

##### [风控主流程接口流程图链接](draw/verify.png)

##### [异步指标回调接收接口流程图链接](draw/receiveIndex.png)



