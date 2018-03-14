package cn.strong.leke.monitor.core.dao.mybatis;


import cn.strong.leke.monitor.mongo.resource.model.query.ResourceTopSta;
import cn.strong.leke.remote.model.tukor.SchoolStageRemote;
import cn.strong.leke.remote.model.tukor.SubjectRemote;

public interface IResourceInfoDao {
	
	//习题
	ResourceTopSta getQuestionInfo(Long resId);
	
	//试卷
	ResourceTopSta getPaperInfo(Long resId);
	
	//课件
	ResourceTopSta getCourseWareInfo(Long resId);
	
	//微课
	ResourceTopSta getMicroCourseInfo(Long resId);
	
	//习题册
	ResourceTopSta getWorkBookInfo(Long resId);
	
	//备课包
	ResourceTopSta getBeikePkgInfo(Long resId);
	
	SchoolStageRemote getSchoolStageByResearch(long userId);
	
	SubjectRemote getSubjectByResearch(long userId);

}
