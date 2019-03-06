package com.vm.im.controller.aop;

import com.alibaba.fastjson.JSON;
import com.vm.im.common.dto.ResultBean;
import com.vm.im.common.enums.BusinessExceptionEnum;
import com.vm.im.common.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 统一异常处理
 *
 * @author zhangqi
 */
@Aspect     // 表示一个切面bean
@Component  // bean容器的组件注解。虽然放在contrller包里，但它不是控制器。如果注入service,但我们又没有放在service包里
@Order(18)   // 有多个日志时，ORDER可以定义切面的执行顺序（数字越大，前置越后执行，后置越前执行）
public class ExceptionHandleAspect {

    //定义日志记录器--获取sl4j包下提供的logger
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandleAspect.class);

    //定义切入点
	/*1、execution 表达式主体
	  2、第1个* 表示返回值类型  *表示所有类型
	  3、包名  com.*.*.controller下
	  4、第4个* 类名，com.*.*.controller包下所有类
	  5、第5个* 方法名，com.*.*.controller包下所有类所有方法
	  6、(..) 表示方法参数，..表示任何参数
	  */
    @Pointcut("execution(public * com.vm.im.controller.*.*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public String handlerException(ProceedingJoinPoint joinPoint) throws Throwable {
        String result = null;
        try {
            result = (String) joinPoint.proceed();
        } catch (BusinessException e) {
            LOG.info("捕获到自定义异常");
            result = JSON.toJSONString(new ResultBean(Integer.parseInt(e.getFailCode()), null, e.getFailReason()));
        } catch (Throwable throwable) {
            LOG.error("捕获到未知异常, Exception:{}", throwable);
            result = JSON.toJSONString(new ResultBean(Integer.parseInt(BusinessExceptionEnum.UNKNOWN_SYSTEM_EXCEPTION.getFailCode()),
                    null, BusinessExceptionEnum.UNKNOWN_SYSTEM_EXCEPTION.getFailReason()));
        }

        return result;
    }

}