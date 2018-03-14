package cn.strong.leke.monitor.core.service.resource;

import java.text.ParseException;
import java.util.List;

import cn.strong.leke.framework.page.jdbc.Page;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceTopQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceTopSta;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceUsedQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.ResourceUsedSta;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolResourceUsedSta;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolUsedTrendQuery;
import cn.strong.leke.monitor.mongo.resource.model.query.SchoolUsedTrendSta;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

public interface IResourceService {
	
	public List<SchoolResourceUsedSta> getSchoolResourceUsedSta(ResourceUsedQuery query);
	
	public ResourceUsedSta getResourceUsedChart(ResourceUsedQuery query);

	public List<ResourceTopSta> getResourceTopInfo(ResourceTopQuery query, Page page);
	
	public List<SchoolUsedTrendSta> getSchoolUsedNum(SchoolUsedTrendQuery query) throws ParseException;
	
	SchoolStageRemote getSchoolStageByResearch(long userId);
	
	SubjectRemote getSubjectByResearch(long userId);
	
}
