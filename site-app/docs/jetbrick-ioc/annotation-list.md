IoC Annotation 一览表
===========================

这里提供一个完整的 IoC 使用的 Annotation 列表，以供参考。

注解                    |  说明
------------------------|------------------------------
@IocBean                | AnnotationIocLoader 将根据这个注解自动将该对象加入到 IoC 容器中管理
@Inject                 | 注入 IoC 中管理的对象（字段和参数，构造函数）
@Config                 | 注入配置文件中配置的参数（字段和参数）
@SpringBean             | 注入 Spring IoC 中的对象（字段和参数）
@IocInit                | 注入完成后的初始化方法
@IocFree                | 被 IoC 容器删除时调用的方法
@InjectFieldWith        | 实现自定义的字段注入的注解
@InjectParameterWith    | 实现自定义的参数注入的注解


