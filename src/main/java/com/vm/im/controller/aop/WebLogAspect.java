package com.vm.im.controller.aop;

import com.vm.im.common.util.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * web请求日志切面类---专门针对控制层，如谁被请求了，花了多少时间，请求发送的参数，返回得值等
 *
 * @author zhangqi
 */
@Aspect     // 表示一个切面bean
@Component  // bean容器的组件注解。虽然放在contrller包里，但它不是控制器。如果注入service,但我们又没有放在service包里
@Order(9)   // 有多个日志时，ORDER可以定义切面的执行顺序（数字越大，前置越后执行，后置越前执行）
public class WebLogAspect {

    //定义日志记录器--获取sl4j包下提供的logger
    Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
    ThreadLocal<Long> startTime = new ThreadLocal<>();  //线程副本类去记录各个线程的开始时间


    //定义切入点
	/*1、execution 表达式主体
	  2、第1个* 表示返回值类型  *表示所有类型
	  3、包名  com.*.*.controller下
	  4、第4个* 类名，com.*.*.controller包下所有类
	  5、第5个* 方法名，com.*.*.controller包下所有类所有方法
	  6、(..) 表示方法参数，..表示任何参数
	  */
    @Pointcut("execution(public * com.vm.im.controller.*.*.*(..))")
    public void weblog() {

    }

    @Before("weblog()")
    public void dobefore(JoinPoint joinPoint) {        //方法里面注入连接点
        logger.info("================================前置通知=================================");                     //info ,debug ,warn ,erro四种级别，这里我们注入info级别
        startTime.set(System.currentTimeMillis());
        //获取servlet请求对象---因为这不是控制器，这里不能注入HttpServletRequest，但springMVC本身提供ServletRequestAttributes可以拿到
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("FROM_IP:" + IPUtils.getIpAddr(request));   //请求IP
        logger.info("URL:" + request.getRequestURL().toString());         // 向那个url发的请求
        logger.info("METHOD:" + request.getMethod());
        logger.info("CLASS_METHOD:" + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());                     // 请求的是哪个类，哪种方法
        logger.info("ARGS:" + Arrays.toString(joinPoint.getArgs()));     // 本方法传了哪些参数
    }


    //方法的返回值注入给ret
    @AfterReturning(returning = "ret", pointcut = "weblog()")
    public void doafter(Object ret) {
        logger.info("================================后置通知================================");
        logger.info("RESPONSE:" + ret);       // 响应的内容---方法的返回值responseEntity
        logger.info("SPEND:" + (System.currentTimeMillis() - startTime.get()));
    }
}