Result/ResultHandler
=====================================

Result 是 action 方法的返回值。 action 方法返回值可以是任意类型，每一种 Result 类型将会有一个对应的 ResultHandler 来负责处理相应的 Result 对象。


内置的 Result/ResultHandler
-------------------------------------

jetbrick 已经内置了多种 Result/ResultHandler 处理器：

* `String`: 负责处理模板 View
* `HttpStatus`: 响应一个 HTTP Status
* `RawText`: 自定义文本输出
* `RawData`: 自定义二进制输出
* `RawDownload`: 文件下载
* `JSONAware/JSONObject/JSONArray`: JSON 输出 (fastjson 库)
* `JsonElement`: JSON 输出 (Gson 库)
* `org.w3c.dom.Document`: XML 输出
* `JAXBElement<?>`: JAXB XML 输出
* `void`: 无 Response 输出。

我们将分别介绍不同的 Result 的使用方法。

### String

String Result 是专门用来处理模板 View 的，同时也能处理 forward 和 redirect 的情况。

```java
@Action
public String list() {
    // 将会显示 JSP 页面
    return "list.jsp";
}

@Action
public String login() {
    // 将会 redirect 到 /login
    return "redirect:/login";
}
```

当我们使用 String 作为 Result 的时候，我们实际是由 View/ViewHandler 这一对兄弟来处理对应的 View。具体可以参考 [View/ViewHandler](view.html)

### HttpStatus

生成一个 HTTP Response 响应。

```java
@Action
public HttpStatus notfound() {
    // 将会返回一个 404 (Page not found)
    return HttpStatus.SC_NOT_FOUND;
}
```

### RawText

提供自定义文本输出

```java
@Action
public RawText text() {
    return RawText.text("这是纯文本");
}
@Action
public RawText js() {
    return RawText.js("data=[1,2,3];");
}
```

### RawData

提供自定义二进制输出

```java
@Action
public RawData image() {
    return new RawData(new File("/tmp/123.gif"), "image/gif");
}
```

### RawDownload

提供文件下载功能

```java
@Action
public RawDownload download() {
    return new RawDownload(new File("/tmp/123.doc"), "中文文件名.doc");
}
```

### JSONAware/JSONObject/JSONArray

使用 fastjson 库来生成一个 json 输出。(自动支持 jsonp 调用)

需要加入依赖：

```xml
<dependency>
    <groupId>com.github.subchen</groupId>
    <artifactId>jetbrick-webmvc-fastjson</artifactId>
    <version>{{WEBMVC-VERSION}}</version>
</dependency>
```

范例代码如下：

```java
@Action
public JSONAware list() {
    JSONAware json = new JSONObject();
    json.put("list", ...);
    ...
    return json;
}
```

### JsonElement

使用 gson 库来生成一个 json 输出。(自动支持 jsonp 调用)


需要加入依赖：

```xml
<dependency>
    <groupId>com.github.subchen</groupId>
    <artifactId>jetbrick-webmvc-gson</artifactId>
    <version>{{WEBMVC-VERSION}}</version>
</dependency>
```

范例代码如下：

```java
@Action(value="data.json")
public GsonElement list() {
    JsonObject json = new JsonObject();
    json.add("list", ...);
    ...
    return json;
}
```

### org.w3c.dom.Document

输出一个 XML Document

```java
@Action(value="data.xml")
public Document getXml() {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();
    // 
    Element root = doc.createElement("root");
    doc.appendChild(root);
    ...
    //
    return doc;
}
```

### JAXBElement

输出一个 JAXB XML Document

```java
@Action(value="data.xml")
public JAXBElement<Users> getXml() {
    Users users = new Users();

    User user = new User();
    user.setId("1");
    user.setName("张三");
    users.setUsers(Arrays.asList(user));

    return new JAXBElement<Users>(new QName("users"), Users.class, users);
}

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="users")
public class Users {

    @XmlElement(name="user")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
    
    public void setUsers(List<User> users) {
        this.users = users;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="user")
public class User {
 
    @XmlElement
    private String id;

    @XmlElement
    private String name;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
```

### void

不进行任何输出操作（可能用户自己会进行一些自定义的输出）

```java
@Action
public void action() {
    ...
}
```


自定义 Result/ResultHandler
-------------------------------------

jetbrick 允许用户定义自己的 Result 和 ResultHandler。

### 自定义 Result

新建一个普通的 Java 对象: `MyResult`

```java
@ManagedWith(MyResultHandler.class)
public class MyResult {
    ...
}
```

> [info] **提示**: 需要使用 `@ManagedWith(...)` 标注，参数是对应的 ResultHandler 类。

### 自定义 ResultHandler

新建一个 `MyResultHandler` 类来处理 `MyResult`

```java
@Managed(MyResult.class)
public class MyResultHandler implements ResultHandler<MyResult> {

    @Override
    public void handle(RequestContext ctx, MyResult result) throws Throwable {
        ...
    }
}
```

> [info] **提示**：
> 1. 需要实现 `jetbrick.web.mvc.result.ResultHandler` 接口
> 2. 需要使用 `@Managed(...)` 标注，参数是 Result 类。如果省略参数，那么将自动从 ResultHandler 接口的泛型参数中获取。


使用系统/第三方自带的 Result 类
-------------------------------------

如果你的 `Result` 类是系统自带的，或者第三方提供的，无法增加 `@ManagedWith(...)` 注解，那么也没关系。

我们可以直接定义对应的 `ResultHandler`。然后将自定义的 `ResultHandler` 所在的 package 加入的 jetbrick 自动扫描路径下（由 `web.scan.packages` 配置），然后 jetbrick 就会在启动的时候，自动发现所有的 `ResultHandler`，并且将其和 `Result` 关联起来。

我们真心的希望大家扩展自己的 Result/ResultHandler，然后共享出来，让 jetbrick 可以为更多的用户服务。

