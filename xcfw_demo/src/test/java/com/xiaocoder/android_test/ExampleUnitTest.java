package com.xiaocoder.android_test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 * <p/>
 * 1. @Test : 测试方法，测试程序会运行的方法，后边可以跟参数代表不同的测试，如(expected=XXException.class) 异常测试，(timeout=xxx)超时测试
 * 2. @Ignore : 被忽略的测试方法
 * 3. @Before: 每一个测试方法之前运行
 * 4. @After : 每一个测试方法之后运行
 * 5. @BeforeClass: 所有测试开始之前运行
 * 6. @AfterClass: 所有测试结束之后运行
 * <p/>
 * 在老Junit4提供了setUp()和tearDown()，在每个测试函数调用之前/后都会调用。
 *
 * @Before: Method annotated with @Before executes before every test.
 * @After: Method annotated with @After executes after every test.
 * <p/>
 * 如果在测试之前有些工作我们只想做一次，用不着每个函数之前都做一次。比如读一个很大的文件。那就用下面两个来标注：
 * @BeforeClass
 * @AfterClass 注意：
 * @Before/@After 可以有多个; @BeforeClass/@AfterClass 只有一个
 * <p/>
 * 如果我们预计有Exception，那就给@Test加参数：
 * @Test(expected = XXXException.class)
 * <p/>
 * 如果出现死循环怎么办？这时timeout参数就有用了：
 * @Test(timeout = 1000)
 * <p/>
 * 如果我们暂时不用测试一个用例，我们不需要删除或都注释掉。只要改成：
 * @Ignore 你也可以说明一下原因@Ignore("something happens")
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    //---------------------------------------------------------------------------
    @Test
    public void testMain() {
        fail("Not yet implemented");
    }

    @Test
    public void testTest() {
        System.out.println("@Test");//调用自己要测试的方法
    }

    @Test(expected = ArithmeticException.class)
    public void testException() {
        System.out.println(1 / 0);//调用自己要测试的方法
    }

    @Test
    public void testAssert() {
        assertEquals("chenleixing", "chenlei");
    }

    @Test(timeout = 1)
    public void testTimeout() {
        System.out.println("超时测试");
    }

    @Before
    public void testBefore() {
        System.out.println("@Before");
    }

    @BeforeClass
    public static void testBeforeClass() {//必须为静态方法
        System.out.println("@BeforeClass");
    }

    @After
    public void testAfter() {
        System.out.println("@After");
    }

    @AfterClass
    public static void testAfterClass() {//必须为静态方法
        System.out.println("@AfterClass");
    }

    @Ignore
    public void testIgnore() {
        System.out.println("@Ignore");
    }
}
