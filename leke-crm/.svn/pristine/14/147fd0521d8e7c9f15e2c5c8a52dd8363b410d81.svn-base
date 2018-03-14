package cn.strong.leke.scs.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.strong.leke.framework.spring.SpringContextHolder;
import cn.strong.leke.scs.service.SellerService;

/**
 * 销售月佣金累计清零任务。
 * 计划执行时间：每月1日凌晨0点。
 * @author  andy
 * @created 2015年6月15日 下午6:47:09
 * @since   v1.0.0
 */
public class ResetMonthAmountTask extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			SellerService service = SpringContextHolder.getBean(SellerService.class);
			service.executeResetMonthAmountWithTx();
		} catch (Exception e) {
			throw new JobExecutionException(e.getMessage(), e);
		}
	}
}
