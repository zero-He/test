package cn.strong.leke.monitor.core.service.resource.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.strong.leke.beike.model.RepositoryBeikePkgQuery;
import cn.strong.leke.common.utils.BeanUtils;
import cn.strong.leke.context.base.SchoolContext;
import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.model.BaseModel;
import cn.strong.leke.model.base.School;
import cn.strong.leke.monitor.core.dao.mybatis.IResourceInfoDao;
import cn.strong.leke.model.paper.query.RepositoryPaperQuery;
import cn.strong.leke.model.question.querys.RepositoryQuestionQuery;
import cn.strong.leke.model.question.querys.RepositoryWorkbookQuery;
import cn.strong.leke.model.repository.RepoDayGroup;
import cn.strong.leke.model.repository.query.RepositoryCoursewareQuery;
import cn.strong.leke.model.repository.query.RepositoryMicrocourseQuery;
import cn.strong.leke.monitor.core.service.resource.IResourceService;
import cn.strong.leke.monitor.mongo.resource.IResourceUsedDao;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceTopQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceTopSta;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceUsedQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceUsedSta;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolResourceUsedSta;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolUsedTrendQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolUsedTrendSta;
import cn.strong.leke.monitor.util.StatUtils;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;
import cn.strong.leke.remote.service.beike.IBeikePkgRemoteService;
import cn.strong.leke.remote.service.beike.ICoursewareRemoteService;
import cn.strong.leke.remote.service.microcourse.IMicrocourseRemoteService;
import cn.strong.leke.remote.service.paper.IPaperRemoteService;
import cn.strong.leke.remote.service.question.IQuestionRemoteService;
import cn.strong.leke.remote.service.question.IWorkbookRemoteService;

@Service
public class ResourceService implements IResourceService {

	@Autowired
	private IResourceUsedDao resourceDao;

	@Autowired
	private IResourceInfoDao resourceInfoDao;

	@Autowired
	private ICoursewareRemoteService coursewareRemoteService;

	@Autowired
	private IQuestionRemoteService questionRemoteService;

	@Autowired
	private IWorkbookRemoteService workbookRemoteService;

	@Autowired
	private IBeikePkgRemoteService beikePkgRemoteService;

	@Autowired
	private IPaperRemoteService paperRemoteService;

	@Autowired
	private IMicrocourseRemoteService microcourseRemoteService;

	@Override
	public List<SchoolResourceUsedSta> getSchoolResourceUsedSta(ResourceUsedQuery query) {

		List<SchoolResourceUsedSta> schoolResourceUsedStaes = resourceDao.getSchoolResourceUsedSta(query);

		for (SchoolResourceUsedSta schoolResourceUsedSta : schoolResourceUsedStaes) {

			Long schoolId = schoolResourceUsedSta.getSchoolId();

			List<Document> documents = resourceDao.getResourceBySchoolId(query, schoolId);

			School school = SchoolContext.getSchoolBySchoolId(schoolId);

			schoolResourceUsedSta.setSchoolName(school.getName());

			for (Document document : documents) {

				Integer usedType = document.getInteger("usedType");

				int usedNum = document.getInteger("usedNum");

				switch (usedType) {
				case 1:
					schoolResourceUsedSta.setQuoteNum(usedNum);
					break;
				case 2:
					schoolResourceUsedSta.setDownloadNum(usedNum);

					break;
				default:
					schoolResourceUsedSta.setCompileNum(usedNum);
					break;
				}

			}
		}
		return schoolResourceUsedStaes;
	}

	@Override
	public ResourceUsedSta getResourceUsedChart(ResourceUsedQuery query) {
		ResourceUsedSta result = new ResourceUsedSta();
		List<RepoDayGroup> repoDayGroups = new ArrayList<RepoDayGroup>();
		
		List<ResourceUsedSta> resourceUsedStas = resourceDao.getResourceGroupByDate(query);
		
		int usedNum = resourceDao.getResourceUsedNum(query);
		
		int count = resourceDao.getResourceUsedCount(query);
		int resouceNum = 0;

		int newResourceNum = 0;
		Integer source = query.getResSource();

		if (source == 1) {
			query.setSchoolId(0L);
		} else if (source == 2) {
			query.setSchoolId(0L);
			query.setUserId(2L);
		} else if (source == 3) {
			query.setShareScope(6);
		}

		switch (query.getResType()) {
		case 1:// 习题
			RepositoryQuestionQuery questionQuery = new RepositoryQuestionQuery();

			BeanUtils.copyProperties(questionQuery, query);

			newResourceNum = questionRemoteService.countLekeQuestion(questionQuery);
			repoDayGroups = questionRemoteService.groupCreatedOnLekeQuestion(questionQuery);
			questionQuery.setStartDateTime(null);

			resouceNum = questionRemoteService.countLekeQuestion(questionQuery);
			break;
		case 2:// 试卷
			RepositoryPaperQuery paperQuery = new RepositoryPaperQuery();

			BeanUtils.copyProperties(paperQuery, query);

			newResourceNum = paperRemoteService.countLekePaper(paperQuery);
			repoDayGroups = paperRemoteService.groupCreatedOnLekePaper(paperQuery);
			paperQuery.setStartDateTime(null);

			resouceNum = paperRemoteService.countLekePaper(paperQuery);
			break;
		case 3:// 课件
			RepositoryCoursewareQuery coursewareQuery = new RepositoryCoursewareQuery();

			BeanUtils.copyProperties(coursewareQuery, query);

			newResourceNum = coursewareRemoteService.countLekeCourseware(coursewareQuery);
			repoDayGroups = coursewareRemoteService.groupCreatedOnLekeCourseware(coursewareQuery);
			coursewareQuery.setStartDateTime(null);

			resouceNum = coursewareRemoteService.countLekeCourseware(coursewareQuery);
			break;
		case 4:// 微课
			RepositoryMicrocourseQuery microcourseQuery = new RepositoryMicrocourseQuery();

			BeanUtils.copyProperties(microcourseQuery, query);

			newResourceNum = microcourseRemoteService.countLekeMicrocourse(microcourseQuery);
			repoDayGroups = microcourseRemoteService.groupCreatedOnLekeMicrocourse(microcourseQuery);
			microcourseQuery.setStartDateTime(null);

			resouceNum = microcourseRemoteService.countLekeMicrocourse(microcourseQuery);
			break;
		case 5:// 习题册
			RepositoryWorkbookQuery workbookQuery = new RepositoryWorkbookQuery();

			BeanUtils.copyProperties(workbookQuery, query);
			repoDayGroups = workbookRemoteService.groupCreatedOnLekeWorkbook(workbookQuery);
			newResourceNum = workbookRemoteService.countLekeWorkbook(workbookQuery);
			workbookQuery.setStartDateTime(null);
			resouceNum = workbookRemoteService.countLekeWorkbook(workbookQuery);
			break;
		case 6:// 备课包
			RepositoryBeikePkgQuery beikePkgQuery = new RepositoryBeikePkgQuery();

			BeanUtils.copyProperties(beikePkgQuery, query);
			repoDayGroups = beikePkgRemoteService.groupCreatedOnLekeBeikePkg(beikePkgQuery);
			newResourceNum = beikePkgRemoteService.countLekeBeikePkg(beikePkgQuery);
			beikePkgQuery.setStartDateTime(null);

			resouceNum = beikePkgRemoteService.countLekeBeikePkg(beikePkgQuery);
			break;

		}

		
		List<Date> dates = StatUtils.getDatesBetweenTwoDate(query.getStartDateTime(), query.getEndDataTime());
		

		List<ResourceUsedSta> list = new ArrayList<>();
		for (Date date : dates) {
			ResourceUsedSta res = new ResourceUsedSta();
			String formatDate = BaseModel.formatDate(date, "yyyyMMdd");
			res.setTs(formatDate);
			for (ResourceUsedSta resourceUsedSta : resourceUsedStas) {
				int usedResourceNum = resourceUsedSta.getResIds().size();

				resourceUsedSta.setUsedResourceNum(usedResourceNum);
				if (formatDate.equals(resourceUsedSta.getTs())) {
					res = resourceUsedSta;
				}
			}
			for (RepoDayGroup repoDayGroup : repoDayGroups) {
				if (repoDayGroup.getDay().replace("-", "").equals(formatDate)) {
					res.setNewResourceNum(repoDayGroup.getCount());
				}
			}
			list.add(res);
		}
		result.setResourceUsedStas(list);

		result.setUsedResourceNum(usedNum);

		result.setUsedRescourceCount(count);

		result.setResouceNum(resouceNum);

		result.setNewResourceNum(newResourceNum);

		return result;
	}

	@Override
	public List<ResourceTopSta> getResourceTopInfo(ResourceTopQuery query, Page page) {

		List<ResourceTopSta> result = new ArrayList<>();

		List<ResourceTopSta> resIdAndUsednums = resourceDao.getResIdAndUsednum(query, page);

		if (!resIdAndUsednums.isEmpty()) {

			int rank = 0;
			for (ResourceTopSta resourceTopSta : resIdAndUsednums) {
				
				rank ++;
				
				resourceTopSta.setRank(rank);
				int resType = resourceTopSta.getResType();
				Long resId = resourceTopSta.getResId();
				// 1-习题 2-试卷 3-课件 4-微课 5-习题册 6-备课包
				switch (resType) {
				case 1:// 习题
					ResourceTopSta questionInfofromResId = resourceInfoDao.getQuestionInfo(resId);
					if(questionInfofromResId != null){
						resourceTopSta.setUploadName(questionInfofromResId.getUploadName());
						resourceTopSta.setStageName(questionInfofromResId.getStageName());
						resourceTopSta.setSubjectName(questionInfofromResId.getSubjectName());
						result.add(resourceTopSta);
					}else{
						resourceTopSta.setUploadName(null);
						resourceTopSta.setStageName(null);
						resourceTopSta.setSubjectName(null);
						result.add(resourceTopSta);
					}
					break;
				case 2:// 试卷
					ResourceTopSta paperInfofromResId = resourceInfoDao.getPaperInfo(resId);
					if(paperInfofromResId != null){
						resourceTopSta.setResourceName(paperInfofromResId.getResourceName());
						resourceTopSta.setUploadName(paperInfofromResId.getUploadName());
						resourceTopSta.setStageName(paperInfofromResId.getStageName());
						resourceTopSta.setSubjectName(paperInfofromResId.getSubjectName());
						result.add(resourceTopSta);
					}else{
						resourceTopSta.setResourceName(null);
						resourceTopSta.setUploadName(null);
						resourceTopSta.setStageName(null);
						resourceTopSta.setSubjectName(null);
						result.add(resourceTopSta);
					}
					break;
				case 3:// 课件
					ResourceTopSta courseWareInfofromResId = resourceInfoDao.getCourseWareInfo(resId);
					if(courseWareInfofromResId != null){
						resourceTopSta.setResourceName(courseWareInfofromResId.getResourceName());
						resourceTopSta.setUploadName(courseWareInfofromResId.getUploadName());
						resourceTopSta.setStageName(courseWareInfofromResId.getStageName());
						resourceTopSta.setSubjectName(courseWareInfofromResId.getSubjectName());
						result.add(resourceTopSta);
					}else{
						resourceTopSta.setResourceName(null);
						resourceTopSta.setUploadName(null);
						resourceTopSta.setStageName(null);
						resourceTopSta.setSubjectName(null);
						result.add(resourceTopSta);
					}
					break;
				case 4:// 微课
					ResourceTopSta microCourseInfofromResId = resourceInfoDao.getMicroCourseInfo(resId);
					if(microCourseInfofromResId != null){
						resourceTopSta.setResourceName(microCourseInfofromResId.getResourceName());
						resourceTopSta.setUploadName(microCourseInfofromResId.getUploadName());
						resourceTopSta.setStageName(microCourseInfofromResId.getStageName());
						resourceTopSta.setSubjectName(microCourseInfofromResId.getSubjectName());
						result.add(resourceTopSta);
					}else{
						resourceTopSta.setResourceName(null);
						resourceTopSta.setUploadName(null);
						resourceTopSta.setStageName(null);
						resourceTopSta.setSubjectName(null);
						result.add(resourceTopSta);
					}
					break;
				case 5:// 习题册
					ResourceTopSta workBookInfofromResId = resourceInfoDao.getWorkBookInfo(resId);
					if(workBookInfofromResId != null){
						resourceTopSta.setResourceName(workBookInfofromResId.getResourceName());
						resourceTopSta.setUploadName(workBookInfofromResId.getUploadName());
						resourceTopSta.setStageName(workBookInfofromResId.getStageName());
						resourceTopSta.setSubjectName(workBookInfofromResId.getSubjectName());
						result.add(resourceTopSta);
					}else{
						resourceTopSta.setResourceName(null);
						resourceTopSta.setUploadName(null);
						resourceTopSta.setStageName(null);
						resourceTopSta.setSubjectName(null);
						result.add(resourceTopSta);
					}
					break;
					
				case 6:// 备课包
					ResourceTopSta beikePkgInfofromResId = resourceInfoDao.getBeikePkgInfo(resId);
					if(beikePkgInfofromResId != null){
						resourceTopSta.setResourceName(beikePkgInfofromResId.getResourceName());
						resourceTopSta.setUploadName(beikePkgInfofromResId.getUploadName());
						resourceTopSta.setStageName(beikePkgInfofromResId.getStageName());
						resourceTopSta.setSubjectName(beikePkgInfofromResId.getSubjectName());
						result.add(resourceTopSta);
					}else{
						resourceTopSta.setResourceName(null);
						resourceTopSta.setUploadName(null);
						resourceTopSta.setStageName(null);
						resourceTopSta.setSubjectName(null);
						result.add(resourceTopSta);
					}
					break;
				}
			}
		}
		return result;
	}

	@Override
	public List<SchoolUsedTrendSta> getSchoolUsedNum(SchoolUsedTrendQuery query) throws ParseException {

		List<SchoolUsedTrendSta> schoolUsedTrendStas = resourceDao.getSchoolUsedNum(query); 
		
		List<Date> dates = StatUtils.getDatesBetweenTwoDate(new SimpleDateFormat("yyyy-MM-dd").parse(query.getStartDate()), new SimpleDateFormat("yyyy-MM-dd").parse(query.getEndDate()));
		List<SchoolUsedTrendSta> list = new ArrayList<>();
		for (Date date : dates) {
			SchoolUsedTrendSta res = new SchoolUsedTrendSta();
			String formatDate = BaseModel.formatDate(date, "yyyyMMdd");
			res.setTs(formatDate);
			for (SchoolUsedTrendSta schoolUsedTrendSta : schoolUsedTrendStas) {
				if (formatDate.equals(schoolUsedTrendSta.getTs())) {
					res.setUsedNum(schoolUsedTrendSta.getUsedNum());
					break;
				}else{
					res.setUsedNum(0);
				}
			}
			list.add(res);
		}
		return list;
	}

	@Override
	public SchoolStageRemote getSchoolStageByResearch(long userId) {
		return resourceInfoDao.getSchoolStageByResearch(userId);
	}

	@Override
	public SubjectRemote getSubjectByResearch(long userId) {
		return resourceInfoDao.getSubjectByResearch(userId);
	}

}
