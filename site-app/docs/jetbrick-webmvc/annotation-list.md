MVC 注解一览表
========================

这里提供一个完整的 MVC 使用的 Annotation 列表，以供参考。


Action 注解
-----------------

注解                     |  说明                                 
-------------------------|------------------------------
@Controller              | 表示一个 Controller 类
@Action                  | 表示一个 Action 方法


参数注解
-----------------

注解                     |  说明                                 
-------------------------|------------------------------
@PathVariable            | 获取 URL PATH 中的参数 
@RequestParam            | request.getParameter()
@RequestHeader           | request.getHeader()
@RequestCookie           | ctx.getRequest().getCookies()
@RequestForm             | 将 request parameters 中的参数，注入到 form 中   
@RequestAttribute        | request.getAttribute()
@SessionAttribute        | session.getAttribute()
@ServletContextAttribute | servletContext.getAttribute()
@InitParameter           | servletContext.getInitParameter(name)

扩展点注解
-----------------

注解                     |  说明                                 
-------------------------|------------------------------
@Managed                 | 可以表示一个 ResultHandler/ViewHandler/ArgumentGetter/FileUpload 类
@ManagedWith             | 表示一个 Result 类，或者是一个 Argument Annotation


