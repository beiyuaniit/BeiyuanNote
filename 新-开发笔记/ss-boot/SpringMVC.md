# ==简介==

## SpringMVC

- mvc
  - MVC是一种软件架构的思想，将软件按照模型、视图、控制器来划分。用户通过视图层发送请求到服务器，在服务器中请求被Controller接收，Controller调用相应的Model层处理请求，处理完毕将结果返回到Controller，Controller再根据请求处理的结果找到相应的View视图，渲染数据后最终响应给浏览器
  - M：Model，模型层，指工程中的JavaBean，作用是处理数据
    - 实体类Bean：专门存储业务数据的，如 Student、User 等
    - 业务处理 Bean：指 Service 或 Dao 对象，专门用于处理业务逻辑和数据访问。
  - V：View，视图层，指工程中的html或jsp等页面，作用是与用户进行交互，展示数据
  - C：Controller，控制层，指工程中的servlet，作用是接收请求和响应浏览器

- springmvc
  - SpringMVC是Spring的一个后续产品，是Spring的一个子项目，为表述层开发提供的一整套完备的解决方案。在表述层框架历经 Strust、WebWork、Strust2 等诸多产品的历代更迭之后，目前业界普遍选择了 SpringMVC 作为 Java EE 项目表述层开发的首选方案
  - 三层架构分为表述层（或表示层）、业务逻辑层、数据访问层，表述层表示前台页面和后台servlet
  - 特点
    - Spring 家族原生产品，与 IOC 容器等基础设施无缝对接
    - 基于原生的Servlet，通过了功能强大的前端控制器DispatcherServlet，对请求和响应进行统一处理。感觉跟scrapy中的引擎发挥的作用差不多
    - 表述层各细分领域需要解决的问题全方位覆盖，提供全面解决方案
    - 代码清新简洁，大幅度提升开发效率
    - 内部组件化程度高，可插拔式组件即插即用，想要什么功能配置相应组件即可
    - 性能卓著，尤其适合现代大型、超大型互联网项目要求



## 常用组件

- 组件
  - DispatcherServlet
    - 前端控制器
    - 统一处理请求和响应，整个流程控制的中心，由它调用其它组件处理用户的请求
  - HandlerMapping
    - 处理器映射器、
    - 根据请求的url、method等信息查找Handler，即控制器方法
  - Handler
    - 处理器
    - 在DispatcherServlet的控制下Handler对具体的用户请求进行处理（请求和控制器方法映射
  - HandlerAdapter
    - 处理器适配器
    - 通过HandlerAdapter对处理器（控制器方法）进行执行
  - ViewResolver
    - 视图解析器
    - 进行视图解析，得到相应的视图，例如：ThymeleafView、InternalResourceView、RedirectView

  - View
    - 视图
    - 将模型数据通过页面展示给用户



## SpringMVC的执行流程

- 整个过程中，Spring MVC 还提供了拦截器（Interceptors）、异常处理（Exception Resolvers）等功能，可以用于日志记录、性能监控、权限检查、异常处理等，使得 Web 开发更加灵活和强大

  - 用户通过浏览器或其他客户端向服务器发送 HTTP 请求
  - 前端控制器 DispatcherServlet捕获请求，对请求URL进行解析，得到请求资源标识符（URI），判断请求URI对应的映射
    - URI对应的映射不存在
      - 是否配置了mvc:default-servlet-handler
        - 没配置，则控制台报映射查找不到，客户端展示404错误
        - 有配置，则访问目标资源（一般为静态资源，如：JS,CSS,HTML），找不到客户端也会展示404错误
    - URI对应的映射存在则继续执行下面的流程
  - 根据URI，调用HandlerMapping获得该Handler配置的所有相关的对象（包括Handler对象以及Handler对象对应的拦截器），最后以HandlerExecutionChain执行链对象的形式返回
  - DispatcherServlet 根据获得的Handler，选择一个合适的HandlerAdapter
  - 开始执行拦截器的preHandler(…)方法（正向）
  - 提取Request中的模型数据，填充Handler入参，开始执行Handler（Controller)方法，处理请求。在填充Handler的入参过程中，根据配置，Spring将帮你做一些额外的工作：
    - HttpMessageConveter： 将请求消息（如Json、xml等数据）转换成一个对象，将对象转换为指定的响应信息
    - 数据转换：对请求消息进行数据转换。如String转换成Integer、Double等
    - 数据格式化：对请求消息进行数据格式化。 如将字符串转换成格式化数字或格式化日期等
    - 数据验证： 验证数据的有效性（长度、格式等），验证结果存储到BindingResult或Error中
  - Handler执行完成后，向DispatcherServlet 返回一个ModelAndView对象
  - 开始执行拦截器的postHandle(...)方法【逆向】

  - 根据返回的ModelAndView（此时会判断是否存在异常：如果存在异常，则执行HandlerExceptionResolver进行异常处理）选择一个适合的ViewResolver进行视图解析，根据Model和View，来渲染视图
  - 渲染视图完毕执行拦截器的afterCompletion(…)方法【逆向】
  - 将渲染结果返回给客户端
  - 清理一些资源，例如关闭打开的连接，释放持有的锁等

## DispatcherServlet

-  `DispatcherServlet` 源码的主要流程解析

  - 初始化阶段

    - 创建实例：当应用启动时，Servlet 容器（如 Tomcat）会根据 web.xml 或者 Spring Boot 的自动配置创建 DispatcherServlet 的实例。
    - 初始化：DispatcherServlet 实现了 Servlet 接口和 FrameworkServlet 类，这意味着它的生命周期由容器控制。init() 方法会在容器启动时被调用。

    - 初始化 WebApplicationContext：initWebApplicationContext() 方法会在 init() 方法中被调用，这会创建或检索一个 WebApplicationContext。这个上下文包含了所有 Spring MVC 的 Bean 配置。

    - 初始化策略对象：initStrategies() 方法初始化多个策略对象，这些对象负责不同的职责：

      ```java
      HandlerMappings：将请求映射到处理器。
      HandlerAdapters：执行处理器。
      HandlerExceptionResolvers：处理处理器抛出的异常。
      ViewResolvers：解析视图。
      LocaleResolver：解决本地化问题。
      ThemeResolver：解决主题问题。
      MultipartResolver：解析多部分请求。
      FlashMapManager：管理 FlashMap（用于重定向时的数据传递）。
      ```

  - 请求处理阶段

    - 处理请求：当一个 HTTP 请求到达时，doService() 方法被调用。在这个方法内部，doDispatch() 方法开始处理请求。

    - 查找处理器：doDispatch() 方法会调用 getHandler() 方法，该方法使用 HandlerMappings 来查找处理请求的处理器（Controller）。如果找到匹配的处理器，它将返回一个 HandlerExecutionChain 对象，其中包含了处理器和任何相关的拦截器。
    - 处理请求前的拦截器：如果 HandlerExecutionChain 包含拦截器，它们的 preHandle() 方法会被依次调用。
    - 执行处理器：使用 execute() 方法，DispatcherServlet 会选择一个 HandlerAdapter 来执行处理器。HandlerAdapter 负责调用处理器的 handle() 方法，并返回一个 ModelAndView 对象。
    - 处理请求后的拦截器：如果存在拦截器，它们的 postHandle() 方法会在处理器执行后被调用。
    - 解析视图：processDispatchResult() 方法被调用来解析 ModelAndView 对象中的视图。这通常涉及到 ViewResolver 来找到实际的视图。
    - 渲染视图：一旦视图被解析，render() 方法被调用来渲染视图并将其写入到响应中。
    - 处理完成后的拦截器：在渲染完成后，如果存在拦截器，它们的 afterCompletion() 方法会被调用。

  - 销毁阶段

    - 当应用停止或者服务器关闭时，DispatcherServlet 的 destroy() 方法会被调用，允许进行必要的清理工作

- 初始化过程部分源码

  - DispatcherServlet 本质上是一个 Servlet，所以天然的遵循 Servlet 的生命周期。所以宏观上是 Servlet 生命周期来进行调度。

  - 下图是DispatcherServlet的继承关系（DispatcherServlet继承FrameworkServlet），其实HttpServletBean前还有个HttpServlet，只不过没有重写init

    ![images](img/img005.png)

  - a>初始化WebApplicationContext

    - 所在类：org.springframework.web.servlet.FrameworkServlet

    ```java
    protected WebApplicationContext initWebApplicationContext() {
        WebApplicationContext rootContext =
            WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        WebApplicationContext wac = null;
    
        if (this.webApplicationContext != null) {
            // A context instance was injected at construction time -> use it
            wac = this.webApplicationContext;
            if (wac instanceof ConfigurableWebApplicationContext) {
                ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
                if (!cwac.isActive()) {
                    // The context has not yet been refreshed -> provide services such as
                    // setting the parent context, setting the application context id, etc
                    if (cwac.getParent() == null) {
                        // The context instance was injected without an explicit parent -> set
                        // the root application context (if any; may be null) as the parent
                        cwac.setParent(rootContext);
                    }
                    configureAndRefreshWebApplicationContext(cwac);
                }
            }
        }
        if (wac == null) {
            // No context instance was injected at construction time -> see if one
            // has been registered in the servlet context. If one exists, it is assumed
            // that the parent context (if any) has already been set and that the
            // user has performed any initialization such as setting the context id
            wac = findWebApplicationContext();
        }
        if (wac == null) {
            // No context instance is defined for this servlet -> create a local one
            // 创建WebApplicationContext
            wac = createWebApplicationContext(rootContext);
        }
    
        if (!this.refreshEventReceived) {
            // Either the context is not a ConfigurableApplicationContext with refresh
            // support or the context injected at construction time had already been
            // refreshed -> trigger initial onRefresh manually here.
            synchronized (this.onRefreshMonitor) {
                // 刷新WebApplicationContext
                onRefresh(wac);
            }
        }
    
        if (this.publishContext) {
            // Publish the context as a servlet context attribute.
            // 将IOC容器在应用域共享
            String attrName = getServletContextAttributeName();
            getServletContext().setAttribute(attrName, wac);
        }
    
        return wac;
    }
    ```

  - b>创建WebApplicationContext

    - 所在类：org.springframework.web.servlet.FrameworkServlet

    ```java
    protected WebApplicationContext createWebApplicationContext(@Nullable ApplicationContext parent) {
        Class<?> contextClass = getContextClass();
        if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass)) {
            throw new ApplicationContextException(
                "Fatal initialization error in servlet with name '" + getServletName() +
                "': custom WebApplicationContext class [" + contextClass.getName() +
                "] is not of type ConfigurableWebApplicationContext");
        }
        // 通过反射创建 IOC 容器对象
        ConfigurableWebApplicationContext wac =
            (ConfigurableWebApplicationContext) BeanUtils.instantiateClass(contextClass);
    
        wac.setEnvironment(getEnvironment());
        // 设置父容器，若是同时有spring的ioc容器和springmvc的ioc容器，则spring的容器是springmvc的父容器（如同时存在spring.xml和springmvc.xml
        wac.setParent(parent);
        String configLocation = getContextConfigLocation();
        if (configLocation != null) {
            wac.setConfigLocation(configLocation);
        }
        configureAndRefreshWebApplicationContext(wac);
    
        return wac;
    }
    ```

  - c>DispatcherServlet初始化策略

    - FrameworkServlet创建WebApplicationContext后，刷新容器，调用onRefresh(wac)，此方法在DispatcherServlet中进行了重写，调用了initStrategies(context)方法，初始化策略，即初始化DispatcherServlet的各个组件
    - 所在类：org.springframework.web.servlet.DispatcherServlet

    ```java
    protected void initStrategies(ApplicationContext context) {
       initMultipartResolver(context); // 解析多部分请求
       initLocaleResolver(context); // 解决本地化问题
       initThemeResolver(context); // 解决主题问题
       initHandlerMappings(context); // 将请求映射到处理器
       initHandlerAdapters(context); // 执行处理器
       initHandlerExceptionResolvers(context); // 处理处理器抛出的异常
       initRequestToViewNameTranslator(context);
       initViewResolvers(context); // 解析视图
       initFlashMapManager(context); // 管理 FlashMap（用于重定向时的数据传递）
    }
    ```

- 处理请求部分源码
  - a>processRequest()

    - FrameworkServlet重写HttpServlet中的service()和doXxx()，这些方法中调用了processRequest(request, response)
    - 所在类：org.springframework.web.servlet.FrameworkServlet

    ```java
    protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        long startTime = System.currentTimeMillis();
        Throwable failureCause = null;
    
        LocaleContext previousLocaleContext = LocaleContextHolder.getLocaleContext();
        LocaleContext localeContext = buildLocaleContext(request);
    
        RequestAttributes previousAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = buildRequestAttributes(request, response, previousAttributes);
    
        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
        asyncManager.registerCallableInterceptor(FrameworkServlet.class.getName(), new RequestBindingInterceptor());
    
        initContextHolders(request, localeContext, requestAttributes);
    
        try {
    		// 执行服务，doService()是一个抽象方法，在DispatcherServlet中进行了重写
            doService(request, response);
        }
        catch (ServletException | IOException ex) {
            failureCause = ex;
            throw ex;
        }
        catch (Throwable ex) {
            failureCause = ex;
            throw new NestedServletException("Request processing failed", ex);
        }
    
        finally {
            resetContextHolders(request, previousLocaleContext, previousAttributes);
            if (requestAttributes != null) {
                requestAttributes.requestCompleted();
            }
            logResult(request, response, failureCause, asyncManager);
            publishRequestHandledEvent(request, response, startTime, failureCause);
        }
    }
    ```

  - b>doService()

    - 所在类：org.springframework.web.servlet.DispatcherServlet

    ```java
    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logRequest(request);
    
        // Keep a snapshot of the request attributes in case of an include,
        // to be able to restore the original attributes after the include.
        Map<String, Object> attributesSnapshot = null;
        if (WebUtils.isIncludeRequest(request)) {
            attributesSnapshot = new HashMap<>();
            Enumeration<?> attrNames = request.getAttributeNames();
            while (attrNames.hasMoreElements()) {
                String attrName = (String) attrNames.nextElement();
                if (this.cleanupAfterInclude || attrName.startsWith(DEFAULT_STRATEGIES_PREFIX)) {
                    attributesSnapshot.put(attrName, request.getAttribute(attrName));
                }
            }
        }
    
        // Make framework objects available to handlers and view objects.
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext());
        request.setAttribute(LOCALE_RESOLVER_ATTRIBUTE, this.localeResolver);
        request.setAttribute(THEME_RESOLVER_ATTRIBUTE, this.themeResolver);
        request.setAttribute(THEME_SOURCE_ATTRIBUTE, getThemeSource());
    
        if (this.flashMapManager != null) {
            FlashMap inputFlashMap = this.flashMapManager.retrieveAndUpdate(request, response);
            if (inputFlashMap != null) {
                request.setAttribute(INPUT_FLASH_MAP_ATTRIBUTE, Collections.unmodifiableMap(inputFlashMap));
            }
            request.setAttribute(OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap());
            request.setAttribute(FLASH_MAP_MANAGER_ATTRIBUTE, this.flashMapManager);
        }
    
        RequestPath requestPath = null;
        if (this.parseRequestPath && !ServletRequestPathUtils.hasParsedRequestPath(request)) {
            requestPath = ServletRequestPathUtils.parseAndCache(request);
        }
    
        try {
            // 处理请求和响应
            doDispatch(request, response);
        }
        finally {
            if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
                // Restore the original attribute snapshot, in case of an include.
                if (attributesSnapshot != null) {
                    restoreAttributesAfterInclude(request, attributesSnapshot);
                }
            }
            if (requestPath != null) {
                ServletRequestPathUtils.clearParsedRequestPath(request);
            }
        }
    }
    ```

  - c>doDispatch()

    - 所在类：org.springframework.web.servlet.DispatcherServlet

    ```java
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerExecutionChain mappedHandler = null;
        boolean multipartRequestParsed = false;
    
        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
    
        try {
            ModelAndView mv = null;
            Exception dispatchException = null;
    
            try {
                processedRequest = checkMultipart(request);
                multipartRequestParsed = (processedRequest != request);
    
                // Determine handler for the current request.
                /*
                	mappedHandler：调用链
                    包含handler、interceptorList、interceptorIndex
                	handler：浏览器发送的请求所匹配的控制器方法
                	interceptorList：处理控制器方法的所有拦截器集合
                	interceptorIndex：拦截器索引，控制拦截器afterCompletion()的执行
                */
                mappedHandler = getHandler(processedRequest);
                if (mappedHandler == null) {
                    noHandlerFound(processedRequest, response);
                    return;
                }
    
                // Determine handler adapter for the current request.
               	// 通过控制器方法创建相应的处理器适配器，调用所对应的控制器方法
                HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
    
                // Process last-modified header, if supported by the handler.
                String method = request.getMethod();
                boolean isGet = "GET".equals(method);
                if (isGet || "HEAD".equals(method)) {
                    long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
                    if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
                        return;
                    }
                }
    			
                // 调用拦截器的preHandle()
                if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                    return;
                }
    
                // Actually invoke the handler.
                // 由处理器适配器调用具体的控制器方法，最终获得ModelAndView对象
                mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
    
                if (asyncManager.isConcurrentHandlingStarted()) {
                    return;
                }
    
                applyDefaultViewName(processedRequest, mv);
                // 调用拦截器的postHandle()
                mappedHandler.applyPostHandle(processedRequest, response, mv);
            }
            catch (Exception ex) {
                dispatchException = ex;
            }
            catch (Throwable err) {
                // As of 4.3, we're processing Errors thrown from handler methods as well,
                // making them available for @ExceptionHandler methods and other scenarios.
                dispatchException = new NestedServletException("Handler dispatch failed", err);
            }
            // 后续处理：处理模型数据和渲染视图
            processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
        }
        catch (Exception ex) {
            triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
        }
        catch (Throwable err) {
            triggerAfterCompletion(processedRequest, response, mappedHandler,
                                   new NestedServletException("Handler processing failed", err));
        }
        finally {
            if (asyncManager.isConcurrentHandlingStarted()) {
                // Instead of postHandle and afterCompletion
                if (mappedHandler != null) {
                    mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
                }
            }
            else {
                // Clean up any resources used by a multipart request.
                if (multipartRequestParsed) {
                    cleanupMultipart(processedRequest);
                }
            }
        }
    }
    ```

  - d>processDispatchResult()

    - 所在类：org.springframework.web.servlet.DispatcherServlet

    ```java
    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response,
                                       @Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv,
                                       @Nullable Exception exception) throws Exception {
    
        boolean errorView = false;
    
        if (exception != null) {
            if (exception instanceof ModelAndViewDefiningException) {
                logger.debug("ModelAndViewDefiningException encountered", exception);
                mv = ((ModelAndViewDefiningException) exception).getModelAndView();
            }
            else {
                Object handler = (mappedHandler != null ? mappedHandler.getHandler() : null);
                mv = processHandlerException(request, response, handler, exception);
                errorView = (mv != null);
            }
        }
    
        // Did the handler return a view to render?
        if (mv != null && !mv.wasCleared()) {
            // 处理模型数据和渲染视图
            render(mv, request, response);
            if (errorView) {
                WebUtils.clearErrorRequestAttributes(request);
            }
        }
        else {
            if (logger.isTraceEnabled()) {
                logger.trace("No view rendering, null ModelAndView returned.");
            }
        }
    
        if (WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
            // Concurrent handling started during a forward
            return;
        }
    
        if (mappedHandler != null) {
            // Exception (if any) is already handled..
            // 调用拦截器的afterCompletion()
            mappedHandler.triggerAfterCompletion(request, response, null);
        }
    }
    ```

# ==注解==

## @RequestMapping

- 将请求和处理请求的控制器方法关联起来，建立映射关系。SpringMVC 接收到指定的请求，就会来找到在映射关系中对应的控制器方法来处理这个请求

- example

  ```java
  package org.springframework.web.bind.annotation;
  
  @Target({ElementType.TYPE, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Mapping
  @Reflective({ControllerMappingReflectiveProcessor.class})
  public @interface RequestMapping {
      String name() default "";
      @AliasFor("path")
      String[] value() default {};
      @AliasFor("value")
      String[] path() default {};
      RequestMethod[] method() default {};
      String[] params() default {};
      String[] headers() default {};
      String[] consumes() default {};
      String[] produces() default {};
  }
  
  
  // 匹配的地址要唯一，不能重复
  /*
  标注位置
  类：设置映射请求的请求路径的初始信息
  方法：设置映射请求请求路径的具体信息
  */
  @Controller
  @RequestMapping("/test")
  public class RequestMappingController {
  
  	//此时请求映射所映射的请求的请求路径为：/test/testRequestMapping。拼接
      @RequestMapping("/testRequestMapping")
      public String testRequestMapping(){
          return "success";
      }
  
  }
  
  
  /*
  value是一个数组
  params
  	"param":含该参数
  	"!param":不包含
  	"param=value":匹配相等
  	"param!=value":不相等
  headers，和params同理
  */
  <a th:href="@{/test(username='admin',password=123456)"> test params -->/test</a><br>
  @RequestMapping(
          value = {"/testRequestMapping", "/test"}
          ,method = {RequestMethod.GET, RequestMethod.POST,RequestMethod.DELETE}
          ,params = {"username","password=123456"}
  )
  
  ```

## 路径匹配

- Spring支持Apache ant风格路径

  - ？：表示任意的单个字符
  - *：表示任意的0个或多个字符
  - **：表示任意多层目录
    - 在使用\**时，只能使用/**/xxx的方式。
    - 若是a**b  则解析为2个\*，不能匹配多级目录

  ```java
  @GetMapping("/a?c/ant")
  public String testAnt(){
      return "success";
  }
  ```

- 路径中占位符

  - 传递参数
    - 原始方式：/deleteUser?id=1
    - rest方式：/deleteUser/1 

  ```java
  <a th:href="@{/testRest/1/admin}"> admin -->/testRest</a><br>
  
  // 通过@PathVariable拿到。同名时好像可以省略这个注解
  @RequestMapping("/testRest/{id}/{username}")
  public String testRest(@PathVariable("id") String id, @PathVariable("username") String username){
      System.out.println("id:"+id+",username:"+username);
      return "success";
  }
  ```



## @xxMapping

- 对于处理指定请求方式的控制器方法，SpringMVC中提供了@RequestMapping的派生注解

  ```java
  /*
  处理get请求的映射-->@GetMapping
  处理post请求的映射-->@PostMapping
  处理put请求的映射-->@PutMapping
  处理delete请求的映射-->@DeleteMapping
  */
  @GetMapping("/testGetMapping")
  public String testGetMapping(){
   	return "success";
  }
  ```

- 注

  - 但是目前浏览器只支持get和post：1.若在form表单提交时，可以是post和get，2.若为method设置了其他请求方式的字符串（put或delete），则按照默认的请求方式get处理
  - 若要发送put和delete请求，则需要通过spring提供的过滤器HiddenHttpMethodFilter，在RESTful部分会讲到，在这里对数据库的增删改查可以使用相同的请求路径，不同的请求方式，如get查询，post新增，put修改，delete方式对应删除

## 获取请求参数

- 方式

  - ServletAPI获取

    - 将HttpServletRequest作为控制器方法的形参，此时HttpServletRequest类型的参数表示封装了当前请求的请求报文的对象

    ```java
    @RequestMapping("/testParam")
    public String testParam(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username:"+username+",password:"+password);
        return "success";
    }
    ```

  - 同名形参获取

    - 在控制器方法的形参位置，设置和请求参数同名的形参，当浏览器发送请求，匹配到请求映射时，在DispatcherServlet中就会将请求参数赋值给相应的形参

    ```java
    <a th:href="@{/testParam(username='admin',password=123456)}">测试获取请求参数-->/testParam</a><br>
    
    @RequestMapping("/testParam")
    public String testParam(String username, String password){
        System.out.println("username:"+username+",password:"+password);
        return "success";
    }
    
    // 多值对象
    一复选框名  hobby  值 rain  sun  day     
    // 接受参数形式
    1.String hobby--->hobby:rain,sun,day        
    2.String[]hobby--->hobby:[rain,sun,day]
    ```

  - @PathVariable

    - 路径占位符获取，详间路径匹配章节

    ```java
    @RequestMapping("/testRest/{id}/{username}")
    public String testRest(@PathVariable("id") String id, @PathVariable("username") String username){
        return "success";
    }
    ```

  - @RequestParam

    - 建立映射关系，即使形参名和实参名不一致

    ```java
    package org.springframework.web.bind.annotation;
    
    @Target({ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface RequestParam {
        @AliasFor("name")
        String value() default "";
        @AliasFor("value")
        String name() default "";
        boolean required() default true;
        String defaultValue() default "\n\t\t\n\t\t\n\ue000\ue001\ue002\n\t\t\t\t\n";
    }
    
    // username是前端的参数名
    public String testParam(String id,@RequestParam(value="username",defaultValue="libai") String name){}
    ```

  - @RequestHeader

    - 同理@RequestParam

  - @CookieValue

    - 同理@RequestParam

  - POJO传递

    - 若浏览器传输的请求参数的参数名和实体类中的属性名一致，那么请求参数就会为此属性赋值

    ```java
    <form th:action="@{/testpojo}" method="post">
        用户名：<input type="text" name="username"><br>
        密码：<input type="password" name="password"><br>
        性别：<input type="radio" name="sex" value="男">男<input type="radio" name="sex" value="女">女<br>
        年龄：<input type="text" name="age"><br>
        邮箱：<input type="text" name="email"><br>
        <input type="submit">
    </form>
        
    @RequestMapping("/testpojo")
    public String testPOJO(User user){
        System.out.println(user);
        return "success";
    }
    // 最终结果-->User{id=null, username='张三', password='123', age=23, sex='男', email='123@qq.com'}
    ```

- 获取请求参数的乱码问题

  - 使用SpringMVC提供的编码过滤器CharacterEncodingFilter，但是必须在web.xml中进行注册。tomcat9：post请求提交时会有乱码，get不会。tomcat10好像都解决了
  - SpringMVC中处理编码的过滤器一定要配置到其他过滤器之前，否则无效

  ```xml
  <!--配置springMVC的编码过滤器-->
  <filter>
      <filter-name>CharacterEncodingFilter</filter-name>
      <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
      <init-param>
          <param-name>encoding</param-name>
          <param-value>UTF-8</param-value>
      </init-param>
      <init-param>
          <!--
  		默认值为false，要设置为true才能过滤，因为     
  		if (encoding != null) {
              if (this.isForceRequestEncoding() || request.getCharacterEncoding() == null) {
                  request.setCharacterEncoding(encoding);
              }
  
              if (this.isForceResponseEncoding()) {
                  response.setCharacterEncoding(encoding);
              }
          }
  		-->
          <param-name>forceResponseEncoding</param-name>
          <param-value>true</param-value>
      </init-param>
  </filter>
  <filter-mapping>
      <filter-name>CharacterEncodingFilter</filter-name>
      <url-pattern>/*</url-pattern>
  </filter-mapping>
  ```

## 域对象共享数据

- 选择能实现功能、范围最小的域对象存数据

- jsp的pageContext来获取

- request域

  - ServletAPI

    ```java
    @RequestMapping("/testServletAPI")
    public String testServletAPI(HttpServletRequest request){
        request.setAttribute("testScope", "hello,servletAPI");
        return "success";
    }
    
    //前端通过thymeleaf获取
    <p th:text="${testScope}"></p>
    ```

  - ModelAndView

    ```java
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        /**
         * ModelAndView有Model和View的功能
         * Model主要用于向请求域共享数据
         * View主要用于设置视图，实现页面跳转
         */
        ModelAndView mav = new ModelAndView();
        //向请求域共享数据
        mav.addObject("testScope", "hello,ModelAndView");
        //设置视图，实现页面跳转
        mav.setViewName("success");
        return mav;
    }
    ```

  - Model

    ```java
    @RequestMapping("/testModel")
    public String testModel(Model model){
        model.addAttribute("testScope", "hello,Model");
        return "success";
    }
    ```

  - Map

    ```java
    @RequestMapping("/testMap")
    public String testMap(Map<String, Object> map){
        map.put("testScope", "hello,Map");
        return "success";
    }
    ```

  - ModelMap

    ```java
    @RequestMapping("/testModelMap")
    public String testModelMap(ModelMap modelMap){
        modelMap.addAttribute("testScope", "hello,ModelMap");
        return "success";
    }
    ```

- Model、ModelMap、Map的关系

  - Model、ModelMap、Map类型的参数其实本质上都是 BindingAwareModelMap 类型的（接口的实现类

  ```java
  public interface Model{}
  public class ModelMap extends LinkedHashMap<String, Object> {}
  public class ExtendedModelMap extends ModelMap implements Model {}
  public class BindingAwareModelMap extends ExtendedModelMap {}
  ```

- session域

  - springmvc有 一个注解可以在请求域共享数据时也在session域中存 一份，但是不太好用，建议 用原生servlet

  ```java
  @RequestMapping("/testSession")
  public String testSession(HttpSession session){
      session.setAttribute("testSessionScope", "hello,session");
      return "success";
  }
  // 前端获取
  <p th:text="${session.testSessonScope}"></p>
  ```

- application域

  ```java
  @RequestMapping("/testApplication")
  public String testApplication(HttpSession session){
  	ServletContext application = session.getServletContext();
      application.setAttribute("testApplicationScope", "hello,application");
      return "success";
  }
  }
  // 前端获取
  <p th:text="${application.testSessonScope}"></p>
  ```



## SpringMVC视图

- SpringMVC中的视图是View接口，视图的作用渲染数据，将模型Model中的数据展示给用户

  ```java
  // Source code recreated from a .class file by IntelliJ IDEA
  // (powered by FernFlower decompiler)
  package org.springframework.web.servlet;
  
  public interface View {
      String RESPONSE_STATUS_ATTRIBUTE = View.class.getName() + ".responseStatus";
      String PATH_VARIABLES = View.class.getName() + ".pathVariables";
      String SELECTED_CONTENT_TYPE = View.class.getName() + ".selectedContentType";
  
      @Nullable
      default String getContentType() {
          return null;
      }
      
      void render(@Nullable Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
  }
  ```

- SpringMVC视图的种类很多

  - 默认有转发视图和重定向视图
  - 当工程引入jstl的依赖，转发视图会自动转换为JstlView
  - 若使用的视图技术为Thymeleaf，在SpringMVC的配置文件中配置了Thymeleaf的视图解析器，由此视图解析器解析之后所得到的是ThymeleafView

- 分类

  - 转发视图

    - SpringMVC中默认的转发视图是InternalResourceView
      - 当控制器方法中所设置的视图名称以"forward:"为前缀时，创建InternalResourceView视图，此时的视图名称不会被	SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"forward:"去掉，剩余部分作为最终路径通过转发的方式实现跳转
      - 例如"forward:/"，"forward:/employee"

    ```java
    <!--  视图解析器  -->
        <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
            <!--前后缀。在webapp/jsp目录下的wel.jsp写路径时只需要写wel即可-->
            <property name="prefix" value="/jsp/"></property>
            <property name="suffix" value=".jsp"></property>
        </bean>
    
    @RequestMapping("/testForward")
    public String testForward(){
        //秒啊，此处转发到的是一个下一个RequestMapping("/testHello")匹配的请求处理方法
        //转发到下一个请求处理
        return "forward:/testHello";
        //配置的视图解析器将失效
        //也可以是具体的页面。（若想具体页面，那就不加前缀
        //return "forward:/html/index.html";//转发到webapp/html/index.html;
    }
    ```

    ![image-20210706201316593](img/img003.png)

  - 重定向视图

    - SpringMVC中默认的重定向视图是RedirectView
      - 当控制器方法中所设置的视图名称以"redirect:"为前缀时，创建RedirectView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"redirect:"去掉，剩余部分作为最终路径通过重定向的方式实现跳转
      - 例如"redirect:/"，"redirect:/employee"
      - 重定向视图在解析时，会先将redirect:前缀去掉，然后会判断剩余部分是否以/开头，若是则会自动拼接上下文路径

    ```java
    @RequestMapping("/testRedirect")
    public String testRedirect(){
        return "redirect:/testHello";
        //使用和转发同理
    }
    ```

    ![image-20210706201602267](img/img004.png)

  - ThymeleafView

    - 当控制器方法中所设置的视图名称没有任何前缀时，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，视图名称拼接视图前缀和视图后缀所得到的最终路径，会通过转发的方式实现跳转
    - 但是要怎么使用这个视图的转发呢？return "param02";直接这样跳转到RequestMapping("param02")是不行的

    ```java
    @RequestMapping("/testHello")
    public String testHello(){
        return "hello";
    }
    ```

    ![](img/img002.png)

  - 视图控制器

    - 当控制器方法中，仅仅用来实现页面跳转，不需要处理，如只需要请求即只需要设置视图名称时，可以将处理器方法使用view-controller标签进行表示

    ```xml
    <!--
    	path：设置处理的请求地址
    	view-name：设置请求地址所对应的视图名称
    	可用于通过请求直接从服务器拿页面
    -->
    <mvc:view-controller path="/" view-name="index"></mvc:view-controller>
    
    
    <!--
    当SpringMVC中设置任何一个view-controller时，其他控制器中的请求映射将全部失效，要想生效，需要在SpringMVC的核心配置文件中设置开启mvc注解驱动的标签：
    -->
    <mvc:annotation-driven />
    ```

## RESTful

- REST：Representational State Tansfer，表现层资源状态转移

  - 资源
    - 资源是一种看待服务器的方式，即，将服务器看作是由很多离散的资源组成
    - 每个资源是服务器上一个可命名的抽象概念。因为资源是一个抽象的概念，所以它不仅仅能代表服务器文件系统中的一个文件、数据库中的一张表等等具体的东西，可以将资源设计的要多抽象有多抽象，只要想象力允许而且客户端应用开发者能够理解
    - 与面向对象设计类似，资源是以名词为核心来组织的，首先关注的是名词
    - 一个资源可以由一个或多个URI来标识。URI既是资源的名称，也是资源在Web上的地址，对某个资源感兴趣的客户端应用，可以通过资源的URI与其进行交互。
  - 资源的表述
    - 资源的表述是一段对于资源在某个特定时刻的状态的描述，可以在客户端-服务器端之间转移（交换）
    - 资源的表述可以有多种格式，例如HTML/XML/JSON/纯文本/图片/视频/音频等等
    - 资源的表述格式可以通过协商机制来确定，请求-响应方向的表述通常使用不同的格式

- 实现

  - HTTP协议的GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE 用来删除资源
  - REST 风格提倡 URL 地址使用统一的风格设计，从前到后各个单词使用斜杠分开，不使用问号键值对方式携带请求参数，而是将要发送给服务器的数据作为 URL 地址的一部分，以保证整体风格的一致性

  ```java
  | 操作     | 传统方式         | REST风格                |
  | -------- | ---------------- | ----------------------- |
  | 查询操作 | getUserById?id=1 | user/1-->get请求方式    |
  | 保存操作 | saveUser         | user-->post请求方式     |
  | 删除操作 | deleteUser?id=1  | user/1-->delete请求方式 |
  | 更新操作 | updateUser       | user-->put请求方式      |
  ```

- HiddenHttpMethodFilter

  -  如果浏览器请求方式写的是put、delete、patch（实际上默认都是按照get处理），HiddenHttpMethodFilterl过滤器会改变它们的请求方式，变成真正的put、delete、patch
  - 需要的时候再补充

  ```java
  // 原理，创建一个wrapper类重新包装
  public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
       super(request);
       this.method = method;
  }
  ```



## HttpMessageConverter

- 简介
  - 报文信息转换器，将请求报文转换为Java对象，或将Java对象转换为响应报文。（操作响应比较常见
  - 提供了两个注解和两个类型：@RequestBody，@ResponseBody，RequestEntity，ResponseEntity


- 7

  - @RequestBody

    - 将 HTTP 请求体中的数据绑定到方法的参数上
    - 请求头中的 `Content-Type` 与 `@RequestBody` 参数类型兼容。例如，对于 JSON 数据，`Content-Type` 应该是 `application/json`。

    ```java
    {
        "username": "john_doe",
        "email": "john.doe@example.com",
        "password": "secure_password"
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok().body("User registered successfully");
    }
    
    
    <form th:action="@{/testRequestBody}" method="post">
        用户名：<input type="text" name="username"><br>
        密码：<input type="password" name="password"><br>
        <input type="submit">
    </form>
    @RequestMapping("/testRequestBody")
    public String testRequestBody(@RequestBody String requestBody){
        System.out.println("requestBody:"+requestBody); // requestBody:username=admin&password=123456
        return "success";
    }
    ```

  - @ResponseBody

    - 该方法的返回值直接作为响应报文的响应体响应到浏览器
    - 可用于返回給ajax请求

    ```java
    // 原生用HttpResponse response响应数据
    // response.getWriter().print("success is not far away");
    @RequestMapping("/testResponseBody")
    @ResponseBody
    public String testResponseBody(){
        return "success"; // 已经作为响应体，不再被视图解析器解析
    }
    
    
    // 返回json数据
    1.导入依赖
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.12.1</version>
    </dependency>
        
    2.配置文件中开启注解
    <mvc:annotation-driven />
      
    3.使用
    @RequestMapping("/testResponseUser")
    @ResponseBody
    public User testResponseUser(){
        return new User(1001,"admin","123456",23,"男");
    }    
    // 浏览器的页面中展示的结果
    {"id":1001,"username":"admin","password":"123456","age":23,"sex":"男"}
       
    ```

  - @RestController

    - @Controller + @ResponseBody

  - RequestEntity

    - 一个泛型类，用于封装 HTTP 响应的全部内容，包括状态码、响应头和响应体

    ```java
    @RequestMapping("/testRequestEntity")
    public String testRequestEntity(RequestEntity<String> requestEntity){
        System.out.println("requestHeader:"+requestEntity.getHeaders());
        System.out.println("requestBody:"+requestEntity.getBody());
        return "success";
    }
    
    
    @PostMapping("/example")
    public ResponseEntity<String> handlePostRequest(@RequestBody String requestBody) {
        if (requestBody.equals("valid")) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }
    }
    ```

  - ResponseEntity

    - 同理，可用于构建响应报文



## HandlerInterceptor

- 简介

  - 用于拦截控制器方法，在控制器方法执行前后进行一些操作
  - SpringMVC中的拦截器需要实现HandlerInterceptor，且在配置文件中进行配置

  - 源码

    ```java
    package org.springframework.web.servlet;
    
    public interface HandlerInterceptor {
        // 控制器方法执行之前执行preHandle()，其boolean类型的返回值表示是否拦截或放行，返回true为放行，即调用控制器方法；返回false表示拦截，即不调用控制器方法
        default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            return true;
        }
    
        // 控制器方法执行之后执行postHandle()
        default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        }
    
        // 处理完视图和模型数据，渲染视图完毕之后执行afterComplation()
        default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        }
    }
    
    
    
    
    // DispatcherServlet.java中
    try{	
        //preHandle
    	if (!mappedHandler.applyPreHandle(processedRequest, response)) {
             return;
         }
        mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
        if (asyncManager.isConcurrentHandlingStarted()) {
             return;
        }
        this.applyDefaultViewName(processedRequest, mv);
        	//postHandle
             mappedHandler.applyPostHandle(processedRequest, response, mv);
        }
    
    catch (Exception var20) {
             dispatchException = var20;
        } 
    catch (Throwable var21) {
             dispatchException = new NestedServletException("Handler dispatch failed", var21);
        }
    
    this.processDispatchResult(processedRequest, response, mappedHandler, mv, (Exception)dispatchException);
    
    //afterComplation在this.processDispatchResult()方法中
      if (mappedHandler != null) {
          mappedHandler.triggerAfterCompletion(request, response, (Exception)null);
      }
      
    ```

  - example

    ```java
    @Component//交给springmvc创建对象
    public class FirstInterceptor  implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            System.out.println("preHandle...");
            return true;//返回true表示放行
        }
    
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            System.out.println("postHandle...");
        }
    
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            System.out.println("afterCompletion...");
        }
    }
    ```

    ```xml
    <!--  包扫描  -->
        <context:component-scan base-package="demo01.interceptor"></context:component-scan>
    <!-- 配置拦截器   -->
        <mvc:interceptors>
            <mvc:interceptor>
                <!--拦截的,/*只拦截一层目录，/**所有层-->
                <mvc:mapping path="/**"/>
                <!--不拦截的-->
                <mvc:exclude-mapping path="/"/>
                <!--拦截器对象-->
                <ref bean="firstInterceptor"></ref>
            </mvc:interceptor>
        </mvc:interceptors>
    <!-- 
    	以上配置方式可以通过ref或bean标签设置拦截器，通过mvc:mapping设置需要拦截的请求，通过mvc:exclude-mapping设置需要排除的请求，即不需要拦截的请求
    -->
    ```

- 多个拦截器的执行顺序

  - 若每个拦截器的preHandle()都返回true，和在配置文件的顺序有关，从上到下
    - preHandle()会按照配置的顺序执行
    - postHandle()和afterComplation()会按照配置的反序执行
    - 类似于栈的调用

  - 若某个拦截器的preHandle()返回了false
    - preHandle()返回false和它之前的拦截器的preHandle()都会执行
    - postHandle()都不执行
    - 返回false的拦截器之前的afterComplation()会执行（返回false的那个不会）

  ```java
  preHandle1();
  preHandle2();
  ha.handle(); // 目标方法
  postHandle2();
  postHandle1();
  afterComplation2();
  afterComplation1();
  ```

  

## 异常处理

- 全局异常处理

  - HandlerExceptionResolver

    - 是SpringMVC提供了一个处理控制器方法执行过程中所出现的异常的接口
    - 实现类有：DefaultHandlerExceptionResolver和SimpleMappingExceptionResolver
    - SpringMVC提供了自定义的异常处理器SimpleMappingExceptionResolver

    ```java
    package org.springframework.web.servlet;
    
    public interface HandlerExceptionResolver {
        @Nullable
        ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex);
    }
    
    ```

    - example

      - 统一返回到错误页面

      ```xml
      <!-- web.xml -->
      <error-page>
          <error-code>404</error-code>
          <location>/error/404</location>
      </error-page>
      
      <error-page>
          <exception-type>java.lang.Throwable</exception-type>
          <location>/error/default</location>
      </error-page>
      ```

      ```java
      @Configuration
      public class WebConfig implements WebMvcConfigurer {
      
          @Bean
          public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
              SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
              Properties mappings = new Properties();
              mappings.setProperty("java.lang.Throwable", "error/default");
              resolver.setExceptionMappings(mappings);
              return resolver;
          }
      }
      ```

      

  - @ControllerAdvice注解

    - 控制器异常

    ```xml
    <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        <property name="exceptionMappings">
            <props>
            	<!--
            		properties的键表示处理器方法执行过程中出现的异常
            		properties的值表示若出现指定异常时，设置一个新的视图名称，跳转到指定页面
            	-->
                <prop key="java.lang.ArithmeticException">error</prop>
            </props>
        </property>
        <!--
        	exceptionAttribute属性设置一个属性名，将出现的异常信息存在请求域
      		可通过<p th:text="${ex}"></p>在页面展示
    			java.lang.ArithmeticException: / by zero
        -->
        <property name="exceptionAttribute" value="ex"></property>
    </bean>
    ```

    ```java
    @ControllerAdvice // 将当前类标识为异常处理的组件
    public class GlobalExceptionHandler {
    
        @ExceptionHandler(value = { NullPointerException.class })
        public ModelAndView handleNullPointerException(NullPointerException exception) {
            log.error("Caught NPE globally", exception);
            return new ModelAndView("error");
        }
    
        @ResponseStatus(value = HttpStatus.NOT_FOUND)
        @ExceptionHandler(value = { UserNotFoundException.class })
        @ResponseBody
        public Map<String, String> handleUserNotFoundException(UserNotFoundException exception) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "User not found");
            return errorMap;
        }
        
        
        // @ExceptionHandler用于设置所标识方法处理的异常
        @ExceptionHandler({ArithmeticException.class,NullPointerException.class})
        // ex表示当前请求处理中出现的异常对象
        public String handleArithmeticException(Exception ex, Model model){
            model.addAttribute("ex", ex);//放入请求域
            return "error";
        }
    }
    ```



## 文件上传和下载

- 上传

  - 要求form表单的请求方式必须为post，并且添加属性enctype="multipart/form-data"把数据以二进制传输到服务器
  - SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息

  ```java
  // 前端页面
  <form th:action="@{/testUp}" method="post" enctype="multipart/form-data">
      头像：<input type="file" name="photo"><br>
      <input type="submit" value="上传">
  </form>
  
  
  // 添加依赖
  <!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
  <dependency>
      <groupId>commons-fileupload</groupId>
      <artifactId>commons-fileupload</artifactId>
      <version>1.3.1</version>
  </dependency>
      
      
  // 在SpringMVC的配置文件中添加配置
  <!--必须通过文件解析器的解析才能将文件转换为MultipartFile对象,id要写，且名字只能是下面这个-->
  <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"></bean>
      
      
  // Controller
  @RequestMapping("/testUp")
  public String testUp(MultipartFile photo, HttpSession session) throws IOException {
      //获取上传的文件的文件名
      String fileName = photo.getOriginalFilename();
      //处理文件重名问题
      String hzName = fileName.substring(fileName.lastIndexOf("."));//后缀名还是得要.png
      fileName = UUID.randomUUID().toString().replaceAll("-","") + hzName;
      //获取服务器中photo目录的路径
      ServletContext servletContext = session.getServletContext();
      String photoPath = servletContext.getRealPath("photo");
      File file = new File(photoPath);
      if(!file.exists()){
          file.mkdir();
      }
      String finalPath = photoPath + File.separator + fileName;
      //实现上传功能。意为将浏览器的文件转移到服务器，先读再写
      photo.transferTo(new File(finalPath));
      return "success";
  }    
  ```

- 下载

  - 使用ResponseEntity实现下载文件的功能（把文件作为响应体

  ```java
  @RequestMapping("/testDown")
  public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
      //获取ServletContext对象
      ServletContext servletContext = session.getServletContext();
      //获取服务器中文件的真实路径
      String realPath = servletContext.getRealPath("/static/img/1.jpg");//
      //创建输入流
      InputStream is = new FileInputStream(realPath);
      //创建字节数组
      byte[] bytes = new byte[is.available()];//is.available()文件的字节数
      //将流读到字节数组中
      is.read(bytes);
      //创建HttpHeaders对象设置响应头信息
      MultiValueMap<String, String> headers = new HttpHeaders();
      //设置要下载方式以及下载文件的名字
      headers.add("Content-Disposition", "attachment;filename=1.jpg");
      //设置响应状态码
      HttpStatus statusCode = HttpStatus.OK;
      //创建ResponseEntity对象
      ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
      //关闭输入流
      is.close();
      return responseEntity;
  }
  ```

  

## 注解配置SpringMVC

- 使用配置类和注解代替web.xml和SpringMVC配置文件的功能

- 7

  - 创建初始化类，代替web.xml

    - 在Servlet3.0环境中，容器会在类路径中查找实现javax.servlet.ServletContainerInitializer接口的类，如果找到的话就用它来配置Servlet容器
    - Spring提供了这个接口的实现，名为SpringServletContainerInitializer，这个类反过来又会查找实WebApplicationInitializer的类并将配置的任务交给它们来完成
    - Spring3.2引入了一个便利的WebApplicationInitializer基础实现，名为AbstractAnnotationConfigDispatcherServletInitializer，当我们的类扩展了AbstractAnnotationConfigDispatcherServletInitializer并将其部署到Servlet3.0容器的时候，容器会自动发现它，并用它来配置Servlet上下文

    ```java
    public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    
        /**
         * 指定spring的配置类
         * @return
         */
        @Override
        protected Class<?>[] getRootConfigClasses() {
            return new Class[]{SpringConfig.class};
        }
    
        /**
         * 指定SpringMVC的配置类
         * @return
         */
        @Override
        protected Class<?>[] getServletConfigClasses() {
            return new Class[]{WebConfig.class};
        }
    
        /**
         * 指定DispatcherServlet的映射规则，即url-pattern
         * @return
         */
        @Override
        protected String[] getServletMappings() {
            return new String[]{"/"};
        }
    
        /**
         * 添加过滤器
         * @return
         */
        @Override
        protected Filter[] getServletFilters() {
            CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
            encodingFilter.setEncoding("UTF-8");
            encodingFilter.setForceRequestEncoding(true);
            HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
            return new Filter[]{encodingFilter, hiddenHttpMethodFilter};
        }
    }
    ```

  - 创建SpringConfig配置类，代替spring的配置文件

    ```java
    @Configuration
    public class SpringConfig {
    	//ssm整合之后，spring的配置信息写在此类中
    }
    ```

  - 创建WebConfig配置类，代替SpringMVC的配置文件

    ```java
    /*
    如：1、扫描组件	2、视图解析器		3、view-controller	4、default-servlet-handler
    	5、mvc注解驱动	6、文件上传解析器	7、异常处理		8、拦截器
    */
    @Configuration
    // 扫描组件
    @ComponentScan("com.atguigu.mvc.controller")
    // 开启MVC注解驱动
    @EnableWebMvc
    // 有些配置是在springmvc.xml中不以bean的形式存在，则要用到重写WebMvcConfigurer接口的方法
    public class WebConfig implements WebMvcConfigurer {
    
        //使用默认的servlet处理静态资源
        @Override
        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
            configurer.enable();
        }
    
        //配置文件上传解析器
        @Bean
        public CommonsMultipartResolver multipartResolver(){
            return new CommonsMultipartResolver();
        }
    
        //配置拦截器
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            FirstInterceptor firstInterceptor = new FirstInterceptor();
            registry.addInterceptor(firstInterceptor).addPathPatterns("/**");
            //excludePathPatterns()则是不拦截的
        }
        
        //配置视图控制
        
        /*@Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("index");
        }*/
        
        //配置异常映射
        /*@Override
        public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
            SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
            Properties prop = new Properties();
            prop.setProperty("java.lang.ArithmeticException", "error");
            //设置异常映射
            exceptionResolver.setExceptionMappings(prop);
            //设置共享异常信息的键
            exceptionResolver.setExceptionAttribute("ex");
            resolvers.add(exceptionResolver);
        }*/
    
        //配置生成模板解析器
        @Bean
        public ITemplateResolver templateResolver() {
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            // ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过WebApplicationContext 的方法获得
            ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(
                    webApplicationContext.getServletContext());
            templateResolver.setPrefix("/WEB-INF/templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setCharacterEncoding("UTF-8");
            templateResolver.setTemplateMode(TemplateMode.HTML);
            return templateResolver;
        }
    
        //生成模板引擎并为模板引擎注入模板解析器
        @Bean//@Bean返回值作为ioc容器的一个bean
        //方法能使用的参数是容器中存在的bean：ITemplateResolver templateResolver是上面方法的返回值
        public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);
            return templateEngine;
        }
    
        //生成视图解析器并未解析器注入模板引擎
        @Bean
        public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
            ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
            viewResolver.setCharacterEncoding("UTF-8");
            viewResolver.setTemplateEngine(templateEngine);
            return viewResolver;
        }
    
    
    }
    ```

    

# ==HelloWorld==

- 浏览器发送请求，若请求地址符合前端控制器的url-pattern，该请求就会被前端控制器DispatcherServlet处理。前端控制器会读取SpringMVC的核心配置文件，通过扫描组件找到控制器，将请求地址和控制器中@RequestMapping注解的value属性值进行匹配，若匹配成功，该注解所标识的控制器方法就是处理请求的方法。处理请求的方法需要返回一个字符串类型的视图名称，该视图名称会被视图解析器解析，加上前缀和后缀组成视图的路径，通过Thymeleaf对视图进行渲染，最终”转发“到视图所对应页面

## 开发环境

IDE：idea 2019.2

构建工具：maven3.5.4

服务器：tomcat7

Spring版本：5.3.1

## 创建maven工程

- 添加web模块，打包方式为war

- 引入依赖

  ```xml
  <!--一个依赖可对应多个jar包，会把所依赖的jar包都导入进来-->
  <dependencies>
      <!-- SpringMVC -->
      <dependency>
          <groupId>org.springframework</groupId>
          <artifactId>spring-webmvc</artifactId>
          <version>5.3.1</version>
      </dependency>
  
      <!-- 日志 -->
      <dependency>
          <groupId>ch.qos.logback</groupId>
          <artifactId>logback-classic</artifactId>
          <version>1.2.3</version>
      </dependency>
  
      <!-- ServletAPI -->
      <dependency>
          <groupId>javax.servlet</groupId>
          <artifactId>javax.servlet-api</artifactId>
          <version>3.1.0</version>
          <scope>provided</scope>//由服务器提供
      </dependency>
  
      <!-- Spring5和Thymeleaf整合包 -->
      <dependency>
          <groupId>org.thymeleaf</groupId>
          <artifactId>thymeleaf-spring5</artifactId>
          <version>3.0.12.RELEASE</version>
      </dependency>
  </dependencies>
  ```

  注：由于 Maven 的传递性，我们不必将所有需要的包全部配置依赖，而是配置最顶端的依赖，其他靠传递性导入

  ![images](img\img001.png)

## 配置web.xml

- 注册SpringMVC的前端控制器DispatcherServlet，浏览器不能直接访问类，通过servlet来匹配路径处理请求

- a>默认配置方式

  - 此配置作用下，SpringMVC的配置文件默认位于WEB-INF下，默认名称为\<servlet-name>-servlet.xml，例如，以下配置所对应SpringMVC的配置文件位于WEB-INF下，文件名为springMVC-servlet.xml。也就是加载的springmvc.xml只能是默认路径下的默认文件名

  ```xml
  <!-- 配置SpringMVC的前端控制器，对浏览器发送的请求统一进行处理 -->
  <servlet>
      <servlet-name>springMVC</servlet-name>
      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  </servlet>
  <servlet-mapping>
      <servlet-name>springMVC</servlet-name>
      <!--
          设置springMVC的核心控制器所能处理的请求的请求路径
          /所匹配的请求可以是/login或.html或.js或.css方式的请求路径
          但是/不能匹配.jsp请求路径的请求
  		jsp本质是一个servlet，由服务器处理，不需要经过DispatcherServlet	
  		/*的话则包含.jsp
      -->
      <url-pattern>/</url-pattern>
  </servlet-mapping>
  ```

- b>扩展配置方式

  - 可通过init-param标签设置SpringMVC配置文件的位置和名称，通过load-on-startup标签设置SpringMVC前端控制器DispatcherServlet的初始化时间，springmvc.xml放在了资源目录统一管理
  - \<url-pattern>标签中使用/和/*的区别
    - /所匹配的请求可以是/login或.html或.js或.css方式的请求路径，但是/不能匹配.jsp请求路径的请求
    - 因此就可以避免在访问jsp页面时，该请求被DispatcherServlet处理，从而找不到相应的页面
    - /*则能够匹配所有请求，例如在使用过滤器时，若需要对所有请求进行过滤，就需要使用/\*的写法

  ```xml
  <!-- 配置SpringMVC的前端控制器，对浏览器发送的请求统一进行处理 -->
  <servlet>
      <servlet-name>springMVC</servlet-name>
      <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
      <!-- 通过初始化参数指定SpringMVC配置文件的位置和名称 -->
      <init-param>
          <!-- contextConfigLocation为固定值 -->
          <param-name>contextConfigLocation</param-name>
          <!-- 使用classpath:表示从类路径查找配置文件，例如maven工程中的src/main/resources -->
          <param-value>classpath:springMVC.xml</param-value>//编译后的calasses目录
      </init-param>
      <!-- 
   		作为框架的核心组件，在启动过程中有大量的初始化操作要做
  		而这些操作放在第一次请求时才执行会严重影响访问速度
  		因此需要通过此标签将启动控制DispatcherServlet的初始化时间提前到服务器启动时
  	-->
      <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
      <servlet-name>springMVC</servlet-name>
      <!--
          设置springMVC的核心控制器所能处理的请求的请求路径
          /所匹配的请求可以是/login或.html或.js或.css方式的请求路径
          但是/不能匹配.jsp请求路径的请求
      -->
      <url-pattern>/</url-pattern>
  </servlet-mapping>
  ```

## 创建请求控制器

- 3

  - 由于前端控制器对浏览器发送的请求进行了统一的处理，但是具体的请求有不同的处理过程，因此需要创建处理具体请求的类，即请求控制器，请求控制器中每一个处理请求的方法成为控制器方法
  - 因为SpringMVC的控制器由一个POJO（普通的Java类）担任，因此需要通过@Controller注解将其标识为一个控制层组件，交给Spring的IoC容器管理，此时SpringMVC才能够识别控制器的存在

  ```java
  @Controller
  public class HelloController {
      
  }
  ```

## 创建springMVC的配置文件

- 3

  ```xml
  <!-- 自动扫描包，根据注解管理对象，idea添加包扫面后，类或方法的右侧会有个小图标 -->
      <!--包名居然不能是demo-01，否则就变成目录了。。-->
      <context:component-scan base-package="demo01.controller"></context:component-scan>
  
  <!-- 配置Thymeleaf视图解析器 -->
  <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
      <property name="order" value="1"/>
      <property name="characterEncoding" value="UTF-8"/>
      <property name="templateEngine">
          <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
              <property name="templateResolver">
                  <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
      
                      <!-- 视图前缀 补充：这些界面不能直接访问，只能通过转发来访问-->
                      <property name="prefix" value="/WEB-INF/templates/"/>
      
                      <!-- 视图后缀 -->
                      <property name="suffix" value=".html"/>
                      <property name="templateMode" value="HTML5"/>
                      <property name="characterEncoding" value="UTF-8" />
                  </bean>
              </property>
          </bean>
      </property>
  </bean>
  
  <!-- 
     处理静态资源，例如html、js、css、jpg
    若只设置该标签，则只能访问静态资源，其他请求则无法访问
    此时必须设置<mvc:annotation-driven/>解决问题
   -->
  <mvc:default-servlet-handler/>
  
  <!-- 开启mvc注解驱动 -->
  <mvc:annotation-driven>
      <mvc:message-converters>
          <!-- 处理响应中文内容乱码 -->
          <bean class="org.springframework.http.converter.StringHttpMessageConverter">
              <property name="defaultCharset" value="UTF-8" />
              <property name="supportedMediaTypes">
                  <list>
                      <value>text/html</value>
                      <value>application/json</value>
                  </list>
              </property>
          </bean>
      </mvc:message-converters>
  </mvc:annotation-driven>
  ```

  

## 测试

- a>实现对首页的访问

  - 在请求控制器中创建处理请求的方法

  ```java
  // @RequestMapping注解：处理请求和控制器方法之间的映射关系
  // @RequestMapping注解的value属性可以通过请求地址匹配请求，/表示的当前工程的上下文路径
  // localhost:8080/springMVC/
  @RequestMapping("/")//请求映射(value="/")
  public String index() {
      //返回的是视图名称
      return "index";//不能return null;
  }
  ```

- b>通过超链接跳转到指定页面

  - 在主页index.html中设置超链接
  - /WEB-INF/templates/index.html

  ```xml
  <!DOCTYPE html>
  <html lang="en" xmlns:th="http://www.thymeleaf.org">
  <head>
      <meta charset="UTF-8">
      <title>首页</title>
  </head>
  <body>
      <h1>首页</h1>
      <a th:href="@{/target}">HelloWorld</a><br/>//thymeleaf的语法。自动加上上下文路径
  </body>
  </html>
  ```

  

- c>在请求控制器中创建处理请求的方法

  - /WEB-INF/templates/target.html

  ```java
  // 将访问target.html页面
  @RequestMapping("/target")//写成("target")也行
  public String HelloWorld() {
      return "target";
  }
  ```

















