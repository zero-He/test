/**
 * 
 */
package cn.strong.leke.question;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 集成测试基类
 * 
 * @author liulb
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/leke.xml" })
@Transactional
public class AbstractIT {

	@Test
	@Ignore
	public void test() {
		System.out.println("test stub");
	}
}
