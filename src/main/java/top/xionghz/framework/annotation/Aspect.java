package top.xionghz.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * @author bj
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();
}
