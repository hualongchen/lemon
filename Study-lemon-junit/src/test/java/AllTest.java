import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * 统一将所有的test的方法都跑一遍
 */
@RunWith(Suite.class)
@SuiteClasses({SuanFaUtilTest.class,SuanFaUtil2Test.class})
public class AllTest {
}




/**

 cobertura jar进行代码覆盖率的单元测试。
 */