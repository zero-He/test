package cn.strong.leke.monitor.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

public class HttpRequestUtilsTest {

	@Test
	public void test() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getHeader("X-Forwarded-For")).thenReturn("1.1.1.1, 2.2.2.2, 3.3.3.3");
		assertEquals("1.1.1.1", HttpRequestUtils.getClientIp(request));
	}

}
