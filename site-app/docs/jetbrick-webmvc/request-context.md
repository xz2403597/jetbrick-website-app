RequestContext 对象
============================

`RequestContext` 是 jetbrick webmvc 核心类之一。RequestContext 封装 HTTP Request 相关常用操作。


getCurrent()
----------------------

对于每一个 HTTP Request 请求，jetbrick 都封装了一个 RequestContext 对象。并且将该对象放置在 ThreadLocal 中，方便让 RequestContext 对象能够在任意地方可以直接访问到。

```java
RequestContext ctx = RequestContext.getCurrent();
```


HttpServletRequest/HttpSession/ServletContext
-----------------------------------------------

通过 RequestContext 对象，可以获取 HTTP 相关的对象，如下：

* `ServletContext getServletContext()`
* `HttpSession getSession()`
* `HttpServletRequest getRequest()`
* `HttpServletResponse getResponse()`


getParameter() 系列方法
-----------------------------------------------

RequestContext 对象提供了 getParameter() 系列方法，用于从 Request 中获取参数。

* `String getParameter(String key)`
* `String getParameter(String key, String defaultValue)`
* `String[] getParameterValues(String key)`
* `Integer getParameterAsInt(String key)`
* `Integer getParameterAsInt(String key, Integer defaultValue)`
* `Long getParameterAsLong(String key)`
* `Long getParameterAsLong(String key, Long defaultValue)`


getFilePart() 系列方法
-----------------------------------------------

RequestContext 提供了 getFilePart() 系列方法来支持文件上传。支持 `multipart/form-data` 方式的表单上传，也支持 HTML5 方式的文件上传。

* `FilePart getFilePart()` - 获取第一个上传的文件
* `FilePart getFilePart(String)` - 按 form 名称获取对应的文件
* `List<FilePart> getFileParts()` - 获取所有上传的文件


Model 相关
-----------------------------------------------

jetbrick 将 Model 封装在了 RequestContext 中。

* `Model getModel()` - 获取关联的 Model 对象


URL 相关
-----------------------------------------------

* `File getWebroot()` - 获取 Web 根目录对应的文件路径
* `String getPathInfo()` - 获取相对于 webapp 的 URL 路径
* `String getContextPath()` - 获取 webapp 的 context path 路径


RouteInfo 相关
-----------------------------------------------

* `RouteInfo getRouteInfo()` - 获取路由信息
* `String getPathVariable(String)` - 获取 URL 中对应的指定参数


