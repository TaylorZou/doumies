package com.doumies.common.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验城市地名等是否合法，不接受null
 *
 * @author Taylor
 * @since 2023-2-25 16:02
 */
@Documented
@Constraint(validatedBy = CityValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface CheckCityValid {
    /* 校验字段类型 */
    CityType value() default CityType.CITY;

    String message() default "{city.valid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
