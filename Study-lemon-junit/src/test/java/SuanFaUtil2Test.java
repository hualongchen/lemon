import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 *
 * 通过快捷键生成， command+shift+T.
 *
 */
public class SuanFaUtil2Test {


    SuanFaUtil  suanFaUtil ;


    @Before
    public void before()throws Exception{

        suanFaUtil = new SuanFaUtil();
    }
    @Test
    public void add2() throws Exception {

        int rel= suanFaUtil.add(2,4);
        assertEquals(rel,6);
    }

    @Test
    public void delete2() throws Exception {

        //测试hamcrest框架

        int rel = suanFaUtil.delete(5,1);

        assertThat(4,is(4));
        assertThat(4,greaterThan(3));
        assertThat(4,allOf(greaterThan(3),lessThan(6)));
        assertThat("test.txt",startsWith("t"));
        assertThat("test.txt",endsWith("txt"));


        /**
         *
         * org.hamcrest.Matchers.*;  熟悉这个包下面的方法即可。
         *
         * 源代码中有各种的例子
         */

    }



    @Test
    public void chengfa() throws Exception {
    }

}