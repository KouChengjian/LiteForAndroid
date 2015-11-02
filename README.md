# PIForAndroid 简介
* ORM  
* Http
* 异步处理
* 包含了很多实用的android工具。

#目前PIForAndroid主要有四大模块：
##AsyncLibs模块：

##HttpLibs模块：
* 单线程，所有方法都基于一个线程，绝不会跨线程，多线程的事情交给它自带的AsyncExecutor 或者更专业的框架库来解决。
* 灵活的架构，你可以轻松的替换Json自动化库、参数构建方式甚至默认的apache http client连接方式。
* 轻量级，微小的的开销，core jar包仅约86kb。
* 多种请求类型全面支持：get, post, head, put, delete, trace, options, patch.
* 多文件上传，不需要额外的类库支持。
* 内置的Dataparser支持文件和位图下载，你也可以自由的扩展DataParser来把原始的http inputstream转化为你想要的东西。
* 基于json的全自动对象转化： 框架帮你完成Java Object Model 和 Http Parameter之间的转化，完成Http Response与Java Object Model的转化。
* 自动重定向，基于一定的次数，不会造成死循环。
* 自动gizp压缩，帮你完成request编码和response解码以使http连接更加快速.
* 通过网络探测完成智能重试 ，对复杂的、信号不良的的移动网络做特殊的优化。
* 禁用一种或多种网络, 比如2G，3G。
* 内置的AsyncExecutor可以让你轻松实现异步和并发的http请求，如果你喜欢，随意使用你自己的AsyncTask或Thread来完成异步，推荐使用更强大、高效的专业并发库( https://github.com/litesuits/android-lite-async )。
* 简明且统一的异常处理体系：清晰、准确的抛出客户端、网络、服务器三种异常。

##OrmLibs模块：
其中包括shell命令，静默安装，bitmap处理，文件操作，加密存储器，计数器，均值器，吐司，日志，校验，提示，网络监测等基础功能，以及一些Base64、MD5、Hex、Byte、Number、Dialog、Filed、Class、Package、Telephone、Random等工具类。

##UtilLibs模块：
