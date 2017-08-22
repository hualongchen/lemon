package com.tr.file.util.Lombok;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 自动生成类中所有非静态非瞬时字段的equals方法和hashCode方法
 */
@EqualsAndHashCode(exclude = {"id", "shape"}, callSuper = false)
public class LombokEqualsAndHashCodeTest {

    @Getter
    @Setter
    private  int id ;

    @Getter
    @Setter
    private String shape ;


    public static void main(String[] args) {

        LombokEqualsAndHashCodeTest  test  = new LombokEqualsAndHashCodeTest();

        System.out.println(test.hashCode());
    }

}
