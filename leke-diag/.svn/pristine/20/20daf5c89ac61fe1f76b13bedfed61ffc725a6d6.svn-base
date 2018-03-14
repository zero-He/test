package cn.strong.leke.diag.service.studentMonitor;

import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.diag.dao.studentMonitor.OtherAnalyseDao;
import cn.strong.leke.diag.model.studentMonitor.*;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.service.teachingMonitor.CommService;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author LIU.SHITING
 * @version 1.5
 * @date 2017-11-30 20:07:26
 */
@Service
public class OtherAnalyseService {

	@Resource
	private CommService commService;
	@Resource
	private OtherAnalyseDao otherAnalyseDao;
	@Resource
	private IKlassRemoteService klassRemoteService;

	protected static final Logger logger = LoggerFactory.getLogger(OtherAnalyseService.class);

	private static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	/**
	 * 查询other数据
	 * @param vo
	 * @return
	 */
	public OtherReturnResultBean queryOtherAnalysePageData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		//根据schoolId、gradeId或者classId查所有学生
		List<Long> studentIdList = commService.queryStudentIdByParam(vo);
		//学生总人数
		int studentTotalNum = studentIdList.size();
		vo.setStudentTotalNum(studentTotalNum);
		vo.setStudentIds(studentIdList);

		OtherReturnResultBean resultBean = new OtherReturnResultBean();
		resultBean.setStudentTotalNum(studentTotalNum);

		//统计错题本数据
		//查询有错题本的学生
		WrongQuestionByOtherBean wrongQuestionByOtherBean = studentTotalNum > 0 ? otherAnalyseDao.selectWrongQuestionData(vo) : null;
		if (wrongQuestionByOtherBean != null) {
			resultBean.setAvgAddWrongSum(commService.handlerPoint(wrongQuestionByOtherBean.getAvgAddWrongSum()));
			resultBean.setAvgXmWrongSum(commService.handlerPoint(wrongQuestionByOtherBean.getAvgXmWrongSum()));
			resultBean.setGraspPro(commService.handlerPoint(wrongQuestionByOtherBean.getGraspPro()));
		} else {
			resultBean.setAvgAddWrongSum(0);
			resultBean.setAvgXmWrongSum(0);
			resultBean.setGraspPro(0);
		}

		//统计乐答数据
		DoubtByOtherBean doubtByOtherBean = studentTotalNum > 0 ? otherAnalyseDao.selectDoubtData(vo) : null;
		if (doubtByOtherBean != null) {
			resultBean.setAskNum(doubtByOtherBean.getAskPeoNum());
			resultBean.setAvgAskNum(commService.handlerPoint(doubtByOtherBean.getAvgAskNum()));
			resultBean.setAskSolvePro(commService.handlerPoint(doubtByOtherBean.getAskSolvePro()));
		} else {
			resultBean.setAskNum(0);
			resultBean.setAvgAskNum(0);
			resultBean.setAskSolvePro(0);
		}

		//统计课堂录像数据
		//每个学生应该观看课堂数和实际观看课堂数
		List<VideoSeeBean> beanList = new ArrayList<>();
		//查询有考勤并且有录像的课堂
		List<OtherLessonBean> otherLessonBeans = studentTotalNum > 0 ? otherAnalyseDao.selectRideoLesson(vo) : new ArrayList<>();
		Map<Long, List<OtherLessonBean>> lessonBeanByUserId = otherLessonBeans.stream().collect(Collectors.groupingBy(OtherLessonBean::getUserId));
		lessonBeanByUserId.forEach((userId, lessonBeanList) -> {
			//该生应该观看的课堂数
			int isSeeNum = lessonBeanList.size();
			//该生应该观看的课堂ids
			List<Long> sinIdList = lessonBeanList.stream().map(OtherLessonBean::getCourseSingleId).collect(Collectors.toList());
			OtherLessonBean bean = new OtherLessonBean();
			bean.setUserId(userId);
			bean.setCourseSingleIds(sinIdList);

			//该生实际观看的课堂数
			int realSeeNum = sinIdList.size() > 0 ? otherAnalyseDao.selectIsOrNoSeeBySinIds(bean) : 0;

			VideoSeeBean seeBean = new VideoSeeBean();
			seeBean.setStudentId(userId);
			seeBean.setIsSeeNum(isSeeNum);
			seeBean.setRealSeeNum(realSeeNum);
			seeBean.setStuViewSeePro((double) realSeeNum / isSeeNum);
			beanList.add(seeBean);
		});

		//所有学生总共实际观看的课堂数
		//所有学生查看率之和
		double totalStuViewSeePro = beanList.stream().mapToDouble(VideoSeeBean::getStuViewSeePro).sum();
		//人均查看录像课堂数
		double avgLessonNum = studentTotalNum > 0 ? (double) otherAnalyseDao.selectTotalSeeLessonNum(vo) / studentTotalNum : 0;

		//人均课堂查看率
		double viewSeePro = studentTotalNum > 0 ? totalStuViewSeePro / studentTotalNum : 0;
		resultBean.setAvgLessonNum(commService.handlerPoint(avgLessonNum));
		resultBean.setAvgTimeLong(0);
		resultBean.setViewSeePro(commService.handlerPoint(viewSeePro * 100));

		//统计笔记部分的数据
		//查询总创建份数
		int createNum = studentTotalNum > 0 ? otherAnalyseDao.selectCreateNum(vo) : 0;
		//人均创建份数
		double avgCreateNum = studentTotalNum > 0 ? (double) createNum / studentTotalNum : 0;
		resultBean.setAvgCreateNum(commService.handlerPoint(avgCreateNum));

		//查看总份数
		int seeNum = studentTotalNum > 0 ? otherAnalyseDao.selectSeeNum(vo) : 0;
		//人均查看份数
		double avgSeeNum = studentTotalNum > 0 ? (double) seeNum / studentTotalNum : 0;
		resultBean.setAvgSeeNum(commService.handlerPoint(avgSeeNum));

		//平均每份查看次数
		String avgReadNum = studentTotalNum > 0 ? otherAnalyseDao.selectAvgReadNum(vo) : null;
		resultBean.setAvgReadNum(avgReadNum == null ? 0 : commService.handlerPoint(Double.valueOf(avgReadNum)));

		return resultBean;
	}

	/**
	 * 明细数据查询
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<OtherDetailBean> queryOtherDetailDataPage(RequestVo vo, Page page) {
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("graspPro");
			vo.setOrderType("desc");
		}
		List<OtherDetailBean> detailBeanList = handlerOtherDetailData(vo);
		page.setTotalSize(detailBeanList.size());
		return commService.handleOrderData(vo, detailBeanList, OtherDetailBean.class).stream().skip((page.getCurPage() - 1) * page.getPageSize())
				.limit(page.getPageSize()).collect(Collectors.toList());
	}

	/**
	 * 查询需要导出的明细数据
	 * @param vo
	 * @return
	 */
	public List<OtherDetailBean> queryOtherDetailData(RequestVo vo) {
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("graspPro");
			vo.setOrderType("desc");
		}
		List<OtherDetailBean> readyStatusBeanList = handlerOtherDetailData(vo);
		return commService.handleOrderData(vo, readyStatusBeanList, OtherDetailBean.class);
	}

	/**
	 * 处理明细数据
	 * @param vo
	 * @return
	 */
	private List<OtherDetailBean> handlerOtherDetailData(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		List<Clazz> clazzList = new ArrayList<>();
		if (vo.getClassId() != null) {
			Clazz clazz = new Clazz();
			clazz.setClassId(vo.getClassId());
			clazz.setClassName(klassRemoteService.findClazzByClassIds(Arrays.asList(vo.getClassId())).get(0).getClassName());
			clazzList.add(clazz);
		} else {
			clazzList = commService.queryClassByGradeId(vo.getGradeId());
		}

		List<OtherDetailBean> detailBeanList = new ArrayList<>();
		clazzList.forEach((Clazz c) -> {
			Long classId = c.getClassId();
			String className = c.getClassName();
			vo.setClassId(classId);
			//根据schoolId、gradeId或者classId查所有学生
			List<Long> studentIds = commService.queryStudentIdByParam(vo);

			studentIds.forEach(studentId -> {
				final CountDownLatch countDownLatch = new CountDownLatch(5);
				OtherDetailBean detailBean = new OtherDetailBean();
				detailBean.setClassId(classId);
				detailBean.setClassName(className);

				vo.setStudentId(studentId);
				detailBean.setStudentId(studentId);
				detailBean.setStudentName(UserBaseContext.getUserBaseByUserId(studentId).getUserName());

				executor.execute(new Runnable() {
					@Override
					public void run() {
						//查消灭/新增错题数，消灭错题率
						IsOrNoGraspBean isOrNoGraspBean = otherAnalyseDao.selectIsOrNoGraspNum(vo);
						if (isOrNoGraspBean != null) {
							detailBean.setGraspNum(isOrNoGraspBean.getGraspNum());
							detailBean.setGraspPro(commService.handlerPoint(isOrNoGraspBean.getGraspPro()));
						} else {
							detailBean.setGraspNum("0/0");
							detailBean.setGraspPro((double) 0);
						}
						countDownLatch.countDown();
					}
				});

				executor.execute(new Runnable() {
					@Override
					public void run() {
						//提问数/解答数，问题解答率
						AskOrSolveDoubtBean askOrSolveDoubtBean = otherAnalyseDao.selectAskOrSolveDoubt(vo);
						if (askOrSolveDoubtBean != null) {
							detailBean.setAskSolveSum(askOrSolveDoubtBean.getAskSolveSum());
							detailBean.setAskSolvePro(commService.handlerPoint(askOrSolveDoubtBean.getAskSolvePro()));
						} else {
							detailBean.setAskSolveSum("0/0");
							detailBean.setAskSolvePro((double) 0);
						}
						countDownLatch.countDown();
					}
				});

				executor.execute(new Runnable() {
					@Override
					public void run() {
						//查看课堂录像数/每次查看录像时长，录像查看率
						//查询该生有考勤并且有录像的课堂
						List<OtherLessonBean> otherLessonBeans = otherAnalyseDao.selectRideoLessonByStuId(vo);
						detailBean.setLessonNum(otherAnalyseDao.selectTotalSeeLessonNumById(vo));
						if (otherLessonBeans.size() > 0) {
							//该生应该观看的课堂数
							int isSeeNum = otherLessonBeans.size();

							//该生应该观看的课堂ids
							List<Long> sinIdList = otherLessonBeans.stream().map(OtherLessonBean::getCourseSingleId).collect(Collectors.toList());
							OtherLessonBean bean = new OtherLessonBean();
							bean.setUserId(studentId);
							bean.setCourseSingleIds(sinIdList);
							//该生实际观看的课堂数
							int realSeeNum = sinIdList.size() > 0 ? otherAnalyseDao.selectIsOrNoSeeBySinIds(bean) : 0;

							detailBean.setAvgTimeLong((double) 0);
							detailBean.setViewSeePro(commService.handlerPoint(((double) realSeeNum / isSeeNum) * 100));
						} else {
							detailBean.setAvgTimeLong((double) 0);
							detailBean.setViewSeePro((double) 0);
						}

						countDownLatch.countDown();
					}
				});

				executor.execute(new Runnable() {
					@Override
					public void run() {
						//该生创建笔记数
						int createBookNum = otherAnalyseDao.selectCreateBookNum(vo);
						detailBean.setCreateBookNum(createBookNum);
						countDownLatch.countDown();
					}
				});

				executor.execute(new Runnable() {
					@Override
					public void run() {
						//平均每份查看次数
						String avgReadNum = otherAnalyseDao.selectAvgReadNumByStudentId(vo);
						detailBean.setAvgReadNum(avgReadNum == null ? 0 : commService.handlerPoint(Double.valueOf(avgReadNum)));
						countDownLatch.countDown();
					}
				});
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					logger.error("other analyse collect error #################");
				}

				detailBeanList.add(detailBean);
			});

		});

		return detailBeanList;
	}
}
