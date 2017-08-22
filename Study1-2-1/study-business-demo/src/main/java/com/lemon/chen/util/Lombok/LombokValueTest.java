package com.lemon.chen.util.Lombok;

import com.lemon.chen.controller.user.form.LoginForm;
import lombok.Value;

/**
 * Value 类注解，和@Data类似，但是用于不可变类型。
 * 生成的类和所有字段都设置为final，所有字段都为private，
 * 自动生成Getter但是没有Setter，
 * 会生成初始化所有字段的构造函数。
 * 相当于同时应用了final
 * @ToString、
 * @EqualsAndHashCode、
 * @AllArgsConstructor 、
 * @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE),
 * @Getter。
 */
@Value
public class LombokValueTest {


    private LoginForm loginForm;

    private String username;

    private int age =3;


    /**
     * 相当于所有的字段前面都加了个  final
     *
     */
}
