package top.xionghz.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * @author bj
 * @since 1.0.0
 */
//该注解只能应用在类上
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     */
    Class<? extends Annotation> value();
}
