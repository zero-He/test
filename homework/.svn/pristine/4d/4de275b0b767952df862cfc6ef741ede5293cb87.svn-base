package cn.strong.leke.homework.manage;

import org.springframework.stereotype.Component;

import cn.strong.leke.beike.model.CoursewareDTO;
import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.beike.model.MicrocourseFile;
import cn.strong.leke.homework.model.mobile.FileDesc;

@Component
public class FileDescService {

	public FileDesc convertToFileDesc(CoursewareDTO courseware) {
		FileDesc file = new FileDesc();
		file.setId(courseware.getFileId());
		file.setName(courseware.getName());
		file.setType(courseware.getType());
		return file;
	}

	public FileDesc convertToFileDesc(MicrocourseDTO microcourse) {
		MicrocourseFile mcf = microcourse.getMicrocourseFile();
		FileDesc file = new FileDesc();
		//新录制微课改值是null,所以必须非空判断
		if (mcf != null) {
			file.setId(mcf.getFileId());
			file.setName(microcourse.getMicrocourseName());
			file.setType(mcf.getType());
		}
		return file;
	}

}
