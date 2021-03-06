View/ViewHandler
=========================================

当我们使用 String 作为 Result 的时候，我们实际是由 View/ViewHandler 这一对兄弟来处理对应的 View。

Result 返回值可以由下面两部分组成：

```
String Result = <view_type>:<view_path_name>
```

* view_type - 可选的 View 类型，代表用那个 ViewHandler 来处理
* view_path_name - 逻辑视图名称（包含路径，默认是相对于 Action URL 的路径）

比如下面的几种 Result 返回值例子：

* redirect:/passport/login
* login.jsp
* jetx:login.jetx

> [info] **提示**：
> * 如果 view_type 省略，那么将从 view_path_name 中的使用的扩展名中获取。
> * 如果 view_path_name 扩展名也没有，那么将使用系统默认的 ViewHandler (由 `web.view.default` 配置)。


内置的 View/ViewHandler
---------------------------------------

jetbrick 已经内置了多种 View/ViewHandler 处理器：

* `forward`: Forward 到一个指定的 page
* `redirect`: Redirect 到一个指定的 page/URL
* `jsp`: 使用 JSP 作为 View
* `jetx`: 使用 jetbrick-template 作为 View
* `ftl`: 使用 freemarker 作为 View
* `json`: 使用 JSON 文本输出作为 View
* `xml`: 使用 XML 文本输出作为 View
* `text`: 使用纯文本输出作为 View
* `html`: 使用 HTML 文本输出作为 View
* `js`: 使用 JS 文本输出作为 View
* `css`: 使用 CSS 文本输出作为 View

我们将分别介绍不同的 View 的使用方法。

### forward

等价于 `request.getRequestDispatcher(viewPathName).forward(request, response)`

```java
@Action
public String list() {
    return "forward:list.jsp";
}
```

### redirect

等价于 `response.sendRedirect(viewPathName);`

```java
@Action
public String list() {
    return "redirect:/passport/login";
}
```

> [info] **提示**: 
>
> * 返回的路径可以是 `http://`, `https://` 等开头的绝对路径。
> * 返回的路径也可以是相对于 request URL 的相对路径。
> * 返回的路径如果是 `/` 开头的绝对路径，那么 jetbrick 自动在前面会加上 `request.getContextPath()`。


### jsp

默认使用 JSP 页面作为 View

```java
@Action
public String list() {
    return "jsp:list";
}

@Action
public String form() {
    return "form.jsp";
}
```


### jetx

使用 jetbrick-template 页面作为 View

需要加入依赖：

```xml
<dependency>
    <groupId>com.github.subchen</groupId>
    <artifactId>jetbrick-template-jetbrickmvc</artifactId>
    <version>{{TEMPLATE-VERSION}}</version>
</dependency>
```

范例代码如下：

```java
@Action
public String list() {
    return "jetx:list";
}

@Action
public String form() {
    return "form.jetx";
}
```


### ftl

使用 freemarker 页面作为 View

需要加入依赖：

```xml
<dependency>
    <groupId>com.github.subchen</groupId>
    <artifactId>jetbrick-webmvc-freemarker</artifactId>
    <version>{{WEBMVC-VERSION}}</version>
</dependency>
```

范例代码如下：

```java
@Action
public String list() {
    return "ftl:list";
}

@Action
public String form() {
    return "form.ftl";
}
```

### json/xml/text/html/js/css

使用用户提供的文本进行输出

```java
@Action
public String json() {
    return "json: [1,2,3]";
}
@Action
public String xml() {
    return "xml: <root id='1'></root>";
}
@Action
public String text() {
    return "text: 纯文本";
}
@Action
public String html() {
    return "html: <font color='red'>Failed</font>";
}
@Action
public String js() {
    return "data = {};";
}
@Action
public String css() {
    return "css: div.block { width: 100px; }";
}
```


自定义 ViewHandler
--------------------------

jetbrick 允许用户定义自己的 ViewHandler。

比如说，我们要使用 Freemarker 来作为我们的模板引擎，那么就要如下定义：

```java
@Managed
public class FreemarkerViewHandler extends AbstractTemplateViewHandler {

    @Override
    public String getViewType() {
        return "fm";
    }

    @Override
    public String getViewSuffix() {
        return "flt";
    }

    @Override
    protected boolean doRender(RequestContext ctx, String viewPathName) {
        // sample code
        engine = getEngine();
        template = engine.getTemplate(viewPathName);
        if (template == null) {
            return false; // resource not found
        }
        
        template.render(ctx.getRequest(), ctx.getResponse());
        
        return true;
    }
}
```

> [info] **提示**：
>
> 1. 需要实现 `jetbrick.web.mvc.results.view.ViewHandler` 接口，我们推荐直接继承 `jetbrick.web.mvc.results.view.AbstractTemplateViewHandler` 抽象类
> 2. 需要使用 `@Managed` 标注
> 3. 需要将自定义的 ViewHandler 所在的 package 加入的 jetbrick 自动扫描路径下（由 `web.scan.packages` 配置）

如果大家实现了第三方的 ViewHandler，我们希望能共享出来，让 jetbrick 可以为更多的用户服务。



