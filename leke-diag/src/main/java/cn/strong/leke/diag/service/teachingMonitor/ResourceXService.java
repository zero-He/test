package cn.strong.leke.diag.service.teachingMonitor;

import cn.strong.leke.common.utils.DateUtils;
import cn.strong.leke.common.utils.StringUtils;
import cn.strong.leke.context.base.SubjectContext;
import cn.strong.leke.context.base.UserBaseContext;
import cn.strong.leke.diag.dao.teachingMonitor.ResourceXDao;
import cn.strong.leke.diag.model.teachingMonitor.CommProp;
import cn.strong.leke.diag.model.teachingMonitor.RankResultBean;
import cn.strong.leke.diag.model.teachingMonitor.RequestVo;
import cn.strong.leke.diag.model.teachingMonitor.ReturnResultBean;
import cn.strong.leke.diag.model.teachingMonitor.resource.*;
import cn.strong.leke.diag.mongo.teachingMonitor.ResourceMongoDao;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.lesson.model.ClazzQuery;
import cn.strong.leke.model.base.Clazz;
import cn.strong.leke.remote.model.tukor.GradeRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.model.tukor.UserRemote;
import cn.strong.leke.remote.service.lesson.IKlassRemoteService;
import cn.strong.leke.remote.service.tutor.user.IUserRemoteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-21 15:48:44
 */
@Service
public class ResourceXService {

	@Resource
	private ResourceXDao resourceXDao;
	@Resource
	private ResourceMongoDao resourceMongoDao;
	@Resource
	private CommService commService;
	@Resource
	private IKlassRemoteService klassRemoteService;
	@Resource
	private IUserRemoteService userRemoteService;

	/**
	 * 资源数据定时收集
	 */
	public void excuteResourceCollectTask() {
		List<ResourceBean> createCourseWareList = resourceXDao.selectYesterdayCreateCourseWareData();
		if (createCourseWareList.size() > 0) {
			resourceMongoDao.insertResourceCollectData(createCourseWareList);
		}
		List<ResourceBean> createMicroCourseList = resourceXDao.selectYesterdayCreateMicroCourseData();
		if (createMicroCourseList.size() > 0) {
			resourceMongoDao.insertResourceCollectData(createMicroCourseList);
		}
		List<ResourceBean> createBeikePkgList = resourceXDao.selectYesterdayCreateBeikePkgData();
		if (createBeikePkgList.size() > 0) {
			resourceMongoDao.insertResourceCollectData(createBeikePkgList);
		}
		List<ResourceBean> createPaperList = resourceXDao.selectYesterdayCreatePaperData();
		if (createPaperList.size() > 0) {
			resourceMongoDao.insertResourceCollectData(createPaperList);
		}
		List<ResourceBean> createQuestionList = resourceXDao.selectYesterdayCreateQuestionData();
		if (createQuestionList.size() > 0) {
			resourceMongoDao.insertResourceCollectData(createQuestionList);
		}
		List<ResourceBean> shareCourseWareList = resourceXDao.selectYesterdayShareCourseWareData();
		if (shareCourseWareList.size() > 0) {
			resourceMongoDao.insertResourceCollectData(shareCourseWareList);
		}
		List<ResourceBean> shareMicroCourseList = resourceXDao.selectYesterdayShareMicroCourseData();
		if (shareMicroCourseList.size() > 0) {
			resourceMongoDao.insertResourceCollectData(shareMicroCourseList);
		}
		List<ResourceBean> sharePaperList = resourceXDao.selectYesterdaySharePaperData();
		if (sharePaperList.size() > 0) {
			resourceMongoDao.insertResourceCollectData(sharePaperList);
		}
		List<ResourceBean> shareQuestionList = resourceXDao.selectYesterdayShareQuestionData();
		if (shareQuestionList.size() > 0) {
			resourceMongoDao.insertResourceCollectData(shareQuestionList);
		}
		List<ResourceBean> shareBeikePkgList = resourceXDao.selectYesterdayShareBeiKePkgData();
		if (shareBeikePkgList.size() > 0) {
			resourceMongoDao.insertResourceCollectData(shareBeikePkgList);
		}
	}

	/**
	 * 冗余字段
	 * @param list
	 * @param isSubject
	 * @return
	 */
	/*private List<ResourceBean> setRedundanceField(List<ResourceBean> list, boolean isSubject) {
		if (list.size() < 1) {
			return new ArrayList<>();
		}
		list.forEach(q -> {
			Long schoolStageId = q.getSchoolStageId();
			if (schoolStageId != null) {
				SchoolStageRemote schoolStage = SchoolStageContext.getSchoolStage(schoolStageId);
				if (schoolStage != null) {
					q.setSchoolStageName(schoolStage.getSchoolStageName());
				}
			}
			Long subjectId = q.getSubjectId();
			if (isSubject && subjectId != null) {
				SubjectRemote subject = SubjectContext.getSubject(subjectId);
				if (subject != null) {
					q.setSubjectName(subject.getSubjectName());
				}
			}
			Long createdBy = q.getCreatedBy();
			if (createdBy != null) {
				UserBase userBase = UserBaseContext.getUserBaseByUserId(createdBy);
				if (userBase != null) {
					q.setCreatedName(userBase.getUserName());
				}
			}
		});
		return list;
	}*/

	/**
	 * 根据筛选条件查询资源的统计、走势、横向对比、排行数据
	 * @param vo
	 * @return
	 */
	public ReturnResultBean queryResourcePageData(RequestVo vo) throws ParseException {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		if (vo.getCompType().equalsIgnoreCase(RequestVo.CLAZZ)) {
			vo.setCompType(RequestVo.GRADE_SUBJECT);
		}
		ReturnResultBean resultBean = new ReturnResultBean();
		//根据查询条件筛选后的mongoList数据
		List<ResourceBean> resourceBeanList = getTeacherIdsByRequestVo(vo);

		//统计
		List<ResourceCountBean> countBeans = getCountBeanList(resourceBeanList);
		int createMicrocourseCount = 0, createCoursewareCount = 0, createQuestionCount = 0, createPaperCount = 0, createBeikePkgCount = 0;
		int shareMicrocourseCount = 0, shareCoursewareCount = 0, shareQuestionCount = 0, sharePaperCount = 0, shareBeikePkgCount = 0;
		for (ResourceCountBean bean : countBeans) {
			int type = bean.getType();
			if (type == 1) {
				createMicrocourseCount = bean.getMicrocourseCount();
				createCoursewareCount = bean.getCoursewareCount();
				createQuestionCount = bean.getQuestionCount();
				createPaperCount = bean.getPaperCount();
				createBeikePkgCount = bean.getBeikePkgCount();
			}
			if (type == 2) {
				shareMicrocourseCount = bean.getMicrocourseCount();
				shareCoursewareCount = bean.getCoursewareCount();
				shareQuestionCount = bean.getQuestionCount();
				sharePaperCount = bean.getPaperCount();
				shareBeikePkgCount = bean.getBeikePkgCount();
			}
		}
		List<ResourceCountBean> countBeanList = new ArrayList<>();
		for (int i = 1; i < 6; i++) {
			ResourceCountBean bean = new ResourceCountBean();
			//1.习题、2.试卷、3.课件、4.微课、5.习题册、6.备课包'
			if (i == 1) {
				bean.setResType(i);
				bean.setResTypeName("习题");
				bean.setCreateCount(createQuestionCount);
				bean.setShareCount(shareQuestionCount);
			}
			if (i == 2) {
				bean.setResType(i);
				bean.setResTypeName("试卷");
				bean.setCreateCount(createPaperCount);
				bean.setShareCount(sharePaperCount);
			}
			if (i == 3) {
				bean.setResType(i);
				bean.setResTypeName("课件");
				bean.setCreateCount(createCoursewareCount);
				bean.setShareCount(shareCoursewareCount);
			}
			if (i == 4) {
				bean.setResType(i);
				bean.setResTypeName("微课");
				bean.setCreateCount(createMicrocourseCount);
				bean.setShareCount(shareMicrocourseCount);
			}
			if (i == 5) {
				bean.setResType(6);
				bean.setResTypeName("备课包");
				bean.setCreateCount(createBeikePkgCount);
				bean.setShareCount(shareBeikePkgCount);
			}
			countBeanList.add(bean);
		}
		resultBean.setCountBean(countBeanList);

		//走势
		List<ResourceTrendBean> trendList;
		if (RequestVo.DAY.equalsIgnoreCase(vo.getTrendType())) {
			//查出所有的日
			List<CommProp> dayList = commService.findBeikeRateByDay(vo);
			//根据日查出对应的走势数据
			trendList = getTrendData(resourceBeanList, dayList);
		} else if (RequestVo.WEEK.equalsIgnoreCase(vo.getTrendType())) {
			//查出所有的周
			List<CommProp> weebList = commService.findBeikeRateByWeek(vo);
			//根据周查出对应的走势数据
			trendList = getTrendData(resourceBeanList, weebList);
		} else {
			//查出所有的月
			List<CommProp> monthList = commService.findBeikeRateByMonth(vo);
			//根据月查出对应的走势数据
			trendList = getTrendData(resourceBeanList, monthList);
		}
		resultBean.setTrendList(trendList);

		//对比
		//年级学科班级对比
		List<ResourceCompareBean> compareBeanList = getCompareData(vo, resourceBeanList);
		resultBean.setCompareBeanList(compareBeanList);

		//排行
		ReturnResultBean rankData = getRankData(resourceBeanList);
		resultBean.setRankFrontBeanList(rankData.getRankFrontBeanList());
		resultBean.setRankBackBeanList(rankData.getRankBackBeanList());
		resultBean.setShareRankFrontBeanList(rankData.getShareRankFrontBeanList());
		resultBean.setShareRankBackBeanList(rankData.getShareRankBackBeanList());

		return resultBean;
	}

	/**
	 * 获取资源统计部分数据
	 * @param resourceBeanList
	 * @return
	 */
	private List<ResourceCountBean> getCountBeanList(List<ResourceBean> resourceBeanList) {
		Map<Integer, List<ResourceBean>> typeCollect = resourceBeanList.stream().collect(Collectors.groupingBy(ResourceBean::getType));
		List<ResourceCountBean> countBeanList = new ArrayList<>();
		typeCollect.forEach((type, typeList) -> {
			ResourceCountBean resourceCountBean = new ResourceCountBean();
			resourceCountBean.setType(type);
			Map<Integer, List<ResourceBean>> resTypeCollect = typeList.stream().collect(Collectors.groupingBy(ResourceBean::getResType));
			resTypeCollect.forEach((resType, resList) -> {
				Integer count = resList.stream().mapToInt(ResourceBean::getCreateCount).sum();
				if (resType == 1) {
					resourceCountBean.setQuestionCount(count);
				}
				if (resType == 2) {
					resourceCountBean.setPaperCount(count);
				}
				if (resType == 3) {
					resourceCountBean.setCoursewareCount(count);
				}
				if (resType == 4) {
					resourceCountBean.setMicrocourseCount(count);
				}
				if (resType == 6) {
					resourceCountBean.setBeikePkgCount(count);
				}
			});
			countBeanList.add(resourceCountBean);
		});
		return countBeanList;
	}

	/**
	 * 根据日周月List查出对应的走势数据
	 * @param resourceBeanList
	 * @param commList
	 * @return
	 */
	private List<ResourceTrendBean> getTrendData(List<ResourceBean> resourceBeanList, List<CommProp> commList) throws ParseException {
		Map<Integer, List<ResourceBean>> typeCollect = resourceBeanList.stream().collect(Collectors.groupingBy(ResourceBean::getType));
		List<ResourceTrendBean> trendList = new ArrayList<>();
		for (CommProp comm : commList) {
			Date startDate = DateUtils.parseDate(comm.getStartDate());
			Date endDate = DateUtils.addDays(DateUtils.parseDate(comm.getEndDate()), 1);
			ResourceTrendBean resourceTrendBean = new ResourceTrendBean();
			resourceTrendBean.setStartDate(comm.getStartDate());
			resourceTrendBean.setEndDate(DateUtils.formatDate(endDate));
			resourceTrendBean.setDateStr(comm.getDateStr());
			typeCollect.forEach((type, typeList) -> {
				Integer count = typeList.stream().filter(t -> t.getStartTime().getTime() >= startDate.getTime() && t.getEndTime().getTime() < endDate.getTime())
						.mapToInt(ResourceBean::getCreateCount).sum();
				if (type == 1) {
					resourceTrendBean.setCreateCount(count);
				}
				if (type == 2) {
					resourceTrendBean.setShareCount(count);
				}
			});
			trendList.add(resourceTrendBean);
		}
		return trendList;
	}


	/**
	 * 获取对比数据
	 * @param resourceBeanList
	 * @return
	 */
	private List<ResourceCompareBean> getCompareData(RequestVo vo, List<ResourceBean> resourceBeanList) {
		Map<Integer, List<ResourceBean>> typeCollect = resourceBeanList.stream().collect(Collectors.groupingBy(ResourceBean::getType));

		List<ResourceCompareBean> compareBeanList = new ArrayList<>();
		if (RequestVo.GRADE.equalsIgnoreCase(vo.getCompType())) {
			List<ResourceCompareBean> gradeCompareList = new ArrayList<>();
			//根据schoolId（当前登录用户）查所有的gradeIds
			List<GradeRemote> gradeList = commService.findGradeOfSchool();
			//遍历gradeIds,根据gradeId查该年级对应的teacherIds
			gradeList.forEach(g -> {
				//获得年级对应的数据
				Long gradeId = g.getGradeId();
				//根据gradeId查得teacherIds
				List<Long> teacherIds = commService.findTeacherIdsBySchoolIdGradeId(vo.getSchoolId(), gradeId);
				ResourceCompareBean resourceCompareBean = new ResourceCompareBean();
				resourceCompareBean.setGradeId(gradeId);
				resourceCompareBean.setGradeName(g.getGradeName());
				ResourceCompareBean bean = dealCreateShareCount(typeCollect, teacherIds, null, false);
				resourceCompareBean.setCreateCount(bean.getCreateCount());
				resourceCompareBean.setShareCount(bean.getShareCount());
				gradeCompareList.add(resourceCompareBean);

			});

			compareBeanList = gradeCompareList;

		} else if (RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType()) || RequestVo.GRADE_SUBJECT.equalsIgnoreCase(vo.getCompType())) {
			Long subjectId = vo.getSubjectId();
			if (subjectId != null) {//有学科
				//获得学科对应的数据
				ResourceCompareBean resourceCompareBean = new ResourceCompareBean();
				resourceCompareBean.setSubjectId(subjectId);
				resourceCompareBean.setSubjectName(SubjectContext.getSubject(subjectId).getSubjectName());
				typeCollect.forEach((type, typeList) -> {
					int count = typeList.stream().mapToInt(ResourceBean::getCreateCount).sum();
					if (type == 1) {
						resourceCompareBean.setCreateCount(count);
					}
					if (type == 2) {
						resourceCompareBean.setShareCount(count);
					}
				});
				compareBeanList.add(resourceCompareBean);
			} else {//无学科
				if (RequestVo.ALL_SUBJECT.equalsIgnoreCase(vo.getCompType())) {//筛选条件“无年级”,无学科时：年级|学科（点击学科）,遍历该校所有学科
					//根据schoolId查该校所有的subjectIds
					List<SubjectRemote> subjectRemoteList = commService.findSubjectOfSchool();
					List<ResourceCompareBean> subjectCompareList = new ArrayList<>();
					//遍历subjectIds，根据subjectId查对应学科的teacherIds
					subjectRemoteList.forEach(s -> {
						//根据schoolId、subjectId查所有的老师
						List<UserRemote> teacherList = userRemoteService.findTeacherListBySchoolIdAndSubjectId(vo.getSchoolId(), s.getSubjectId());
						List<Long> teacherIds = new ArrayList<>();
						teacherList.forEach(t -> {
							teacherIds.add(t.getId());
						});
						ResourceCompareBean resourceCompareBean = new ResourceCompareBean();
						resourceCompareBean.setSubjectId(s.getSubjectId());
						resourceCompareBean.setSubjectName(s.getSubjectName());
						ResourceCompareBean bean = dealCreateShareCount(typeCollect, teacherIds, s.getSubjectId(), true);
						resourceCompareBean.setCreateCount(bean.getCreateCount());
						resourceCompareBean.setShareCount(bean.getShareCount());
						subjectCompareList.add(resourceCompareBean);

					});

					compareBeanList = subjectCompareList;

				} else {//筛选条件“有年级”,无学科时：班级|学科（点击学科）
					//根据schoolId查该校所有的subjectIds
					List<SubjectRemote> subjectRemoteList = commService.findSubjectOfSchool();
					List<ResourceCompareBean> subjectCompareList = new ArrayList<>();
					//遍历subjectIds，根据subjectId查对应学科的teacherIds
					subjectRemoteList.forEach(s -> {
						//根据schoolId、gradeId、subjectId查所有的老师
						List<Long> teacherIds = commService.findTeacherIdsBySchoolIdGradeIdSubjectId(vo.getSchoolId(), vo.getGradeId(), s.getSubjectId());

						ResourceCompareBean resourceCompareBean = new ResourceCompareBean();
						resourceCompareBean.setSubjectId(s.getSubjectId());
						resourceCompareBean.setSubjectName(s.getSubjectName());
						ResourceCompareBean bean = dealCreateShareCount(typeCollect, teacherIds, s.getSubjectId(), true);
						resourceCompareBean.setCreateCount(bean.getCreateCount());
						resourceCompareBean.setShareCount(bean.getShareCount());
						subjectCompareList.add(resourceCompareBean);

					});

					compareBeanList = subjectCompareList;
				}
			}

		} else if (RequestVo.CLAZZ.equalsIgnoreCase(vo.getCompType())) {//筛选条件“有年级”,学科不一定
			List<ResourceCompareBean> classCompareList = new ArrayList<>();
			//根据schoolId、gradeId查出所有的classIds
			ClazzQuery clazzQuery = new ClazzQuery();
			clazzQuery.setSchoolId(vo.getSchoolId());
			clazzQuery.setGradeId(vo.getGradeId());
			/*clazzQuery.setType(1);*/
			List<Clazz> clazzByQuery = klassRemoteService.findClazzByQuery(clazzQuery);
			//遍历classIds,根据classId查该班级的teacherIds
			clazzByQuery.forEach(c -> {
				//获得班级对应的数据
				Long classId = c.getClassId();
				List<Long> teacherIds = klassRemoteService.findTeacherIdsByClassId(classId);
				ResourceCompareBean resourceCompareBean = new ResourceCompareBean();
				resourceCompareBean.setClassId(classId);
				resourceCompareBean.setClassName(c.getClassName());
				ResourceCompareBean bean = dealCreateShareCount(typeCollect, teacherIds, null, false);
				resourceCompareBean.setCreateCount(bean.getCreateCount());
				resourceCompareBean.setShareCount(bean.getShareCount());
				classCompareList.add(resourceCompareBean);
			});
			compareBeanList = classCompareList.stream().sorted(new CommService.CnComparator<ResourceCompareBean>(true, true, "className", ResourceCompareBean.class)).collect(Collectors.toList());
		}

		return compareBeanList;
	}


	/**
	 * 处理创建和分享数据
	 * @param typeCollect
	 * @param teacherIds
	 * @return
	 */
	private ResourceCompareBean dealCreateShareCount(Map<Integer, List<ResourceBean>> typeCollect, List<Long> teacherIds, Long subjectId, boolean isSubject) {
		ResourceCompareBean resourceCompareBean = new ResourceCompareBean();
		typeCollect.forEach((type, typeList) -> {
			int count = 0;
			for (Long id : teacherIds) {
				count = count + typeList.stream().filter(t -> {
					if (isSubject) {
						return Objects.equals(t.getCreatedBy(), id) && t.getSubjectId().equals(subjectId);
					} else {
						return Objects.equals(t.getCreatedBy(), id);
					}
				}).mapToInt(ResourceBean::getCreateCount).sum();
			}
			if (type == 1) {
				resourceCompareBean.setCreateCount(count);
			}
			if (type == 2) {
				resourceCompareBean.setShareCount(count);
			}
		});
		return resourceCompareBean;
	}

	/**
	 * 获取排行数据
	 * @param resourceBeanList
	 * @return
	 */
	private ReturnResultBean getRankData(List<ResourceBean> resourceBeanList) {
		ReturnResultBean resultBean = new ReturnResultBean();
		List<ResourceRankBean> rankBeanList = new ArrayList<>();
		//先根据老师分组
		Map<Long, List<ResourceBean>> collectList = resourceBeanList.stream().collect(Collectors.groupingBy(ResourceBean::getCreatedBy));
		collectList.forEach((teacherId, resBeanList) -> {
			ResourceRankBean resourceRankBean = new ResourceRankBean();
			resourceRankBean.setTeacherId(teacherId);
			resourceRankBean.setTeacherName(UserBaseContext.getUserBaseByUserId(teacherId).getUserName());
			//该老师的创建总数
			int createCount = resBeanList.stream().filter(r -> r.getType() == 1).mapToInt(ResourceBean::getCreateCount).sum();
			//该老师的分享总数
			int shareCount = resBeanList.stream().filter(r -> r.getType() == 2).mapToInt(ResourceBean::getCreateCount).sum();
			resourceRankBean.setCreateCount(createCount);
			resourceRankBean.setShareCount(shareCount);
			rankBeanList.add(resourceRankBean);
		});
		List<ResourceRankBean> createCollect = rankBeanList.stream().sorted(Comparator.comparing(ResourceRankBean::getCreateCount).reversed()).collect(Collectors.toList());
		List<ResourceRankBean> shareCollect = rankBeanList.stream().sorted(Comparator.comparing(ResourceRankBean::getShareCount).reversed()).collect(Collectors.toList());

		RankResultBean createRankFrontBeanList = getRankDataToResource(createCollect, true);
		RankResultBean shareRankBackBeanList = getRankDataToResource(shareCollect, false);

		resultBean.setRankFrontBeanList(createRankFrontBeanList.getRankFrontBeanList());
		resultBean.setRankBackBeanList(createRankFrontBeanList.getRankBackBeanList());
		resultBean.setShareRankFrontBeanList(shareRankBackBeanList.getRankFrontBeanList());
		resultBean.setShareRankBackBeanList(shareRankBackBeanList.getRankBackBeanList());

		return resultBean;
	}

	/**
	 * 设置资源排行（创建和分享）
	 * @param rankBeanList
	 * @param isCreate
	 * @return
	 */
	private RankResultBean getRankDataToResource(List<ResourceRankBean> rankBeanList, boolean isCreate) {
		List<ResourceRankBean> rankFrontBeanList = new ArrayList<>();
		List<ResourceRankBean> rankBackBeanList = new ArrayList<>();
		if (rankBeanList.size() > 0 && rankBeanList.size() <= 5) {
			rankBeanList.forEach(b -> {
				if (isCreate) {
					rankFrontBeanList.add(setRankBeanByResource(b.getTeacherName(), b.getCreateCount(), true));
				} else {
					rankFrontBeanList.add(setRankBeanByResource(b.getTeacherName(), b.getShareCount(), false));
				}

			});
			for (int i = 0; i < 5 - rankBeanList.size(); i++) {
				if (isCreate) {
					rankFrontBeanList.add(setRankBeanByResource("", 0, true));
				} else {
					rankFrontBeanList.add(setRankBeanByResource("", 0, false));
				}

			}
			for (int i = 0; i < 5; i++) {
				if (isCreate) {
					rankBackBeanList.add(setRankBeanByResource("", 0, true));
				} else {
					rankBackBeanList.add(setRankBeanByResource("", 0, false));
				}

			}
		} else if (rankBeanList.size() > 5 && rankBeanList.size() <= 10) {
			rankFrontBeanList.addAll(rankBeanList.stream().limit(5).collect(Collectors.toList()));
			List<ResourceRankBean> skip = rankBeanList.stream().skip(5).collect(Collectors.toList());
			skip.forEach(b -> {
				if (isCreate) {
					rankBackBeanList.add(setRankBeanByResource(b.getTeacherName(), b.getCreateCount(), true));
				} else {
					rankBackBeanList.add(setRankBeanByResource(b.getTeacherName(), b.getShareCount(), false));
				}

			});
			for (int i = 0; i < 5 - skip.size(); i++) {
				if (isCreate) {
					rankBackBeanList.add(setRankBeanByResource("", 0, true));
				} else {
					rankBackBeanList.add(setRankBeanByResource("", 0, false));
				}

			}

		} else if (rankBeanList.size() > 10) {
			rankFrontBeanList.addAll(rankBeanList.stream().limit(5).collect(Collectors.toList()));
			rankBackBeanList.addAll(rankBeanList.stream().skip(rankBeanList.size() - 5).collect(Collectors.toList()));
		} else {
			for (int i = 0; i < 5; i++) {
				rankFrontBeanList.add(setRankBeanByResource("", 0, true));
				rankBackBeanList.add(setRankBeanByResource("", 0, false));

			}

		}

		RankResultBean resultBean = new RankResultBean();
		resultBean.setRankFrontBeanList(rankFrontBeanList);
		resultBean.setRankBackBeanList(rankBackBeanList);
		return resultBean;

	}

	/**
	 * 设置资源排名bean
	 * @param teacherName
	 * @param count
	 * @param isCreate
	 * @return
	 */
	private ResourceRankBean setRankBeanByResource(String teacherName, int count, boolean isCreate) {
		ResourceRankBean bean = new ResourceRankBean();
		bean.setTeacherName(teacherName);
		if (isCreate) {
			bean.setCreateCount(count);
		} else {
			bean.setShareCount(count);
		}
		return bean;
	}

	/**
	 * 明细分页请求（并且java排序）
	 * @param vo
	 * @param page
	 * @return
	 */
	public List<ResourceDetailBean> queryResourceDetailDataPage(RequestVo vo, Page page) {
		List<ResourceDetailBean> resourceDetailBeanList = queryResourceDetailDataList(vo);
		page.setTotalSize(resourceDetailBeanList.size());
		resourceDetailBeanList = commService.handleOrderData(vo, resourceDetailBeanList, ResourceDetailBean.class).stream()
				.skip((page.getCurPage() - 1) * page.getPageSize()).limit(page.getPageSize()).collect(Collectors.toList());

		return resourceDetailBeanList;
	}


	/**
	 * 根据筛选条件查询明细数据
	 * @param vo
	 * @return
	 */
	/*public List<ResourceDetailBean> queryResourceDetailData(RequestVo vo) {
		List<ResourceDetailBean> resourceDetailBeanList = queryResourceDetailDataList(vo);
		List<ResourceDetailBean> collectList = resourceDetailBeanList.stream().sorted(Comparator.comparing(ResourceDetailBean::getCreateCount)).collect(Collectors.toList());
		final int[] i = {1};
		collectList.forEach(r -> {
			r.setIndex(i[0]++);
		});
		return collectList;
	}*/

	/**
	 * 点击字段排序后调用
	 * @param vo
	 * @return
	 */
	/*public List<ResourceDetailBean> queryResourceDetailSortData(RequestVo vo) {
		List<ResourceDetailBean> resourceDetailBeanList = queryResourceDetailDataList(vo);
		final int[] i = {1};
		resourceDetailBeanList.forEach(a -> {
			a.setIndex(i[0]++);
		});
		List<ResourceDetailBean> attendanceDetailBeanList1 = commService.handleOrderData(vo, resourceDetailBeanList, ResourceDetailBean.class);
		return attendanceDetailBeanList1;
	}*/

	/**
	 * 根据筛选条件查询资源的明细数据list
	 * @param vo
	 * @return
	 */
	public List<ResourceDetailBean> queryResourceDetailDataList(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		if (StringUtils.isEmpty(vo.getOrderAttr()) || StringUtils.isEmpty(vo.getOrderType())) {
			vo.setOrderAttr("createCount");
			vo.setOrderType("desc");
		}
		//根据查询条件筛选后的mongoList数据
		List<ResourceBean> resourceBeanList = getTeacherIdsByRequestVo(vo);
		List<ResourceDetailBean> resourceDetailBeanList = new ArrayList<>();
		//根据teacherId、subjectId分组后的数据
		Map<Long, Map<Long, List<ResourceBean>>> collectList = resourceBeanList.stream().collect(Collectors.groupingBy(ResourceBean::getCreatedBy, Collectors.groupingBy(ResourceBean::getSubjectId)));
		collectList.forEach((createdBy, listBycreatedBy) -> {
			listBycreatedBy.forEach((subjectId, listBySubjectId) -> {
				ResourceDetailBean resourceDetailBean = new ResourceDetailBean();
				resourceDetailBean.setTeacherId(createdBy);
				resourceDetailBean.setTeacherName(UserBaseContext.getUserBaseByUserId(createdBy).getUserName());
				resourceDetailBean.setSubjectId(subjectId);
				SubjectRemote subject = SubjectContext.getSubject(subjectId);
				if (subject != null) {
					resourceDetailBean.setSubjectName(subject.getSubjectName());
				}
				Map<Integer, List<ResourceBean>> typeCollect = listBySubjectId.stream().collect(Collectors.groupingBy(ResourceBean::getType));

				typeCollect.forEach((type, listByType) -> {
					Map<Integer, List<ResourceBean>> resTypeCollect = listByType.stream().collect(Collectors.groupingBy(ResourceBean::getResType));
					int typeCount = listByType.stream().mapToInt(ResourceBean::getCreateCount).sum();
					if (type == 1) {
						resourceDetailBean.setCreateCount(typeCount);
						resTypeCollect.forEach((resType, listByResType) -> {
							int resTypeCount = listByResType.stream().mapToInt(ResourceBean::getCreateCount).sum();
							if (resType == 1) {
								resourceDetailBean.setCreateQuestionCount(resTypeCount);
							}
							if (resType == 2) {
								resourceDetailBean.setCreatePaperCount(resTypeCount);
							}
							if (resType == 3) {
								resourceDetailBean.setCreateCoursewareCount(resTypeCount);
							}
							if (resType == 4) {
								resourceDetailBean.setCreateMicrocourseCount(resTypeCount);
							}
							if (resType == 6) {
								resourceDetailBean.setCreateBeikePkgCount(resTypeCount);
							}
						});
					}
					if (type == 2) {
						resourceDetailBean.setShareCount(typeCount);
						resTypeCollect.forEach((resType, listByResType) -> {
							int resTypeCount = listByResType.stream().mapToInt(ResourceBean::getCreateCount).sum();
							if (resType == 1) {
								resourceDetailBean.setShareQuestionCount(resTypeCount);
							}
							if (resType == 2) {
								resourceDetailBean.setSharePaperCount(resTypeCount);
							}
							if (resType == 3) {
								resourceDetailBean.setShareCoursewareCount(resTypeCount);
							}
							if (resType == 4) {
								resourceDetailBean.setShareMicrocourseCount(resTypeCount);
							}
							if (resType == 6) {
								resourceDetailBean.setShareBeikePkgCount(resTypeCount);
							}
						});
					}
				});
				resourceDetailBeanList.add(resourceDetailBean);
			});

		});
		return commService.handleOrderData(vo, resourceDetailBeanList, ResourceDetailBean.class);
	}

	/**
	 * 根据查询条件筛选后的mongoList数据
	 * @param vo
	 * @return
	 */
	private List<ResourceBean> getTeacherIdsByRequestVo(RequestVo vo) {
		List<ResourceBean> resourceBeanList = null;
		List<Long> teacherIds = null;
		if (vo.getGradeId() == null && vo.getSubjectId() == null) {
			teacherIds = klassRemoteService.findTeacherIdsBySchoolId(vo.getSchoolId());
		} else if (vo.getGradeId() == null && vo.getSubjectId() != null) {
			//根据schoolId、subjectId查所有的老师
			List<UserRemote> teacherList = userRemoteService.findTeacherListBySchoolIdAndSubjectId(vo.getSchoolId(), vo.getSubjectId());
			List<Long> ids = new ArrayList<>();
			teacherList.forEach(t -> {
				ids.add(t.getId());
			});
			teacherIds = ids;
		} else if (vo.getGradeId() != null && vo.getSubjectId() == null) {
			teacherIds = commService.findTeacherIdsBySchoolIdGradeId(vo.getSchoolId(), vo.getGradeId());
		} else if (vo.getGradeId() != null && vo.getSubjectId() != null) {
			//根据schoolId、gradeId、subjectId查所有的老师
			teacherIds = commService.findTeacherIdsBySchoolIdGradeIdSubjectId(vo.getSchoolId(), vo.getGradeId(), vo.getSubjectId());
		}

		List<ResourceBean> resourceBeanListByMongo = resourceMongoDao.queryResourceBeanList(teacherIds, vo.getSchoolId(), vo.getSubjectId(), DateUtils.parseDate(vo.getStartDate()), DateUtils.addDays(DateUtils.parseDate(vo.getEndDate()), 1));
		resourceBeanList = resourceBeanListByMongo.stream().filter(r -> r.getCreatedBy() != null && r.getSubjectId() != null).collect(Collectors.toList());

		return resourceBeanList;
	}


	/**
	 * @param vo schoolId:必须
	 *           gradeId:可选
	 *           subjectId:可选
	 *           teacherName:可选（模糊匹配）
	 *           startDate:必须
	 *           endDate:必须
	 * @return
	 */
	public List<ResourceDetailBean> queryResourceAndDetail(RequestVo vo) {
		List<ResourceDetailBean> resourceDetailBeanList = queryResourceDetailDataList1(vo);
		if (StringUtils.hasText(vo.getTeacherName())) {
			Pattern pattern = Pattern.compile(vo.getTeacherName());
			List<ResourceDetailBean> result = new ArrayList<>();
			for (ResourceDetailBean resourceDetailBean : resourceDetailBeanList) {
				Matcher matcher = pattern.matcher(resourceDetailBean.getTeacherName());
				if (matcher.find()) {
					result.add(resourceDetailBean);
				}
			}
			return result;
		} else {
			return resourceDetailBeanList;
		}
	}

	/**
	 * 只给统计总表调用
	 * @param vo
	 * @return
	 */
	private List<ResourceDetailBean> queryResourceDetailDataList1(RequestVo vo) {
		//设置通用参数（schoolId、limit-天数、trendType-走势、compType-对比）
		commService.setCommPropToRequestVo(vo);
		//根据查询条件筛选后的mongoList数据
		List<ResourceBean> resourceBeanList = getTeacherIdsByRequestVo(vo);
		List<ResourceDetailBean> resourceDetailBeanList = new ArrayList<>();
		//根据teacherId、subjectId分组后的数据
		Map<Long, Map<Long, List<ResourceBean>>> collectList = resourceBeanList.stream().collect(Collectors.groupingBy(ResourceBean::getCreatedBy, Collectors.groupingBy(ResourceBean::getSubjectId)));
		collectList.forEach((createdBy, listBycreatedBy) -> {
			listBycreatedBy.forEach((subjectId, listBySubjectId) -> {
				ResourceDetailBean resourceDetailBean = new ResourceDetailBean();
				resourceDetailBean.setTeacherId(createdBy);
				resourceDetailBean.setTeacherName(UserBaseContext.getUserBaseByUserId(createdBy).getUserName());
				resourceDetailBean.setSubjectId(subjectId);
				SubjectRemote subject = SubjectContext.getSubject(subjectId);
				if (subject != null) {
					resourceDetailBean.setSubjectName(subject.getSubjectName());
				}
				Map<Integer, List<ResourceBean>> typeCollect = listBySubjectId.stream().collect(Collectors.groupingBy(ResourceBean::getType));

				typeCollect.forEach((type, listByType) -> {
					Map<Integer, List<ResourceBean>> resTypeCollect = listByType.stream().collect(Collectors.groupingBy(ResourceBean::getResType));
					int typeCount = listByType.stream().mapToInt(ResourceBean::getCreateCount).sum();
					if (type == 1) {
						resourceDetailBean.setCreateCount(typeCount);
						resTypeCollect.forEach((resType, listByResType) -> {
							int resTypeCount = listByResType.stream().mapToInt(ResourceBean::getCreateCount).sum();
							if (resType == 1) {
								resourceDetailBean.setCreateQuestionCount(resTypeCount);
							}
							if (resType == 2) {
								resourceDetailBean.setCreatePaperCount(resTypeCount);
							}
							if (resType == 3) {
								resourceDetailBean.setCreateCoursewareCount(resTypeCount);
							}
							if (resType == 4) {
								resourceDetailBean.setCreateMicrocourseCount(resTypeCount);
							}
							if (resType == 6) {
								resourceDetailBean.setCreateBeikePkgCount(resTypeCount);
							}
						});
					}
					if (type == 2) {
						resourceDetailBean.setShareCount(typeCount);
						resTypeCollect.forEach((resType, listByResType) -> {
							int resTypeCount = listByResType.stream().mapToInt(ResourceBean::getCreateCount).sum();
							if (resType == 1) {
								resourceDetailBean.setShareQuestionCount(resTypeCount);
							}
							if (resType == 2) {
								resourceDetailBean.setSharePaperCount(resTypeCount);
							}
							if (resType == 3) {
								resourceDetailBean.setShareCoursewareCount(resTypeCount);
							}
							if (resType == 4) {
								resourceDetailBean.setShareMicrocourseCount(resTypeCount);
							}
							if (resType == 6) {
								resourceDetailBean.setShareBeikePkgCount(resTypeCount);
							}
						});
					}
				});
				resourceDetailBeanList.add(resourceDetailBean);
			});

		});
		return resourceDetailBeanList;
	}

	/**
	 * 查老师ids
	 * @param schoolId
	 * @param gradeId
	 * @param subjectId
	 * @param queryType 查询类型:
	 *                  1.根据schoolId查全校的老师ids,
	 *                  2.根据schoolId和subjectId查该校该学科所有的老师ids,
	 *                  3.根据gradeId查该校该年级所有的老师ids,
	 *                  4.根据schoolId、gradeId、subjectId查该校该年级该学科所有的老师ids
	 * @return
	 */
	public List<Long> queryTeacherIds(Long schoolId, Long gradeId, Long subjectId, int queryType) {
		List<Long> teacherIds = new ArrayList<>();
		if (queryType == 1) {
			teacherIds = klassRemoteService.findTeacherIdsBySchoolId(schoolId);
		} else if (queryType == 2) {
			//根据schoolId、subjectId查所有的老师
			List<UserRemote> teacherList = userRemoteService.findTeacherListBySchoolIdAndSubjectId(schoolId, subjectId);
			List<Long> ids = new ArrayList<>();
			teacherList.forEach(t -> {
				ids.add(t.getId());
			});
			teacherIds = ids;
		} else if (queryType == 3) {
			teacherIds = commService.findTeacherIdsBySchoolIdGradeId(schoolId, gradeId);
		} else if (queryType == 4) {
			//根据schoolId、gradeId、subjectId查所有的老师
			teacherIds = commService.findTeacherIdsBySchoolIdGradeIdSubjectId(schoolId, gradeId, subjectId);
		}
		return teacherIds;
	}


}
