package io.github.javpower.vectorexserver.annotation;

import java.lang.annotation.*;

/**
 * @author gc.x
 * @date 2022/6/26
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreLogin {

}
