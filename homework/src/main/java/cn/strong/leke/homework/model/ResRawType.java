package cn.strong.leke.homework.model;

import java.util.HashMap;
import java.util.Map;

import cn.strong.leke.beike.model.MicrocourseDTO;
import cn.strong.leke.beike.model.Types;
import cn.strong.leke.model.paper.PaperDTO;

/**
 * 资源原始类型。
 */
public enum ResRawType {

	// 课件
	PPT("ppt", "PPT"), DOC("doc", "文档"), IMG("img", "图片"), AUDIO("mp3", "音频"), VIDEO("mp4", "视频"), RAR("rar", "压缩包"),
	// 试卷
	NORMAL_PAPER("paper", "普通试卷"), PUSH_PAPER("pushpaper", "推送试卷"), ANSWERSHEET("answersheet",
			"答题卡"), ANSWERFILE("answerfile", "附件试卷"),
	// 微课
	RECORD("record", "录制微课"), UPLOAD("upload", "上传微课"),
	// 其它
	OTHER("other", "其它");

	/**
	 * 图标
	 */
	public final String icon;
	/**
	 * 类型名称
	 */
	public final String name;

	private ResRawType(String icon, String name) {
		this.icon = icon;
		this.name = name;
	}

	private static Map<Types, ResRawType> TYPES;
	static {
		Map<Types, ResRawType> map = new HashMap<>();
		map.put(Types.PPT, PPT);
		map.put(Types.DOC, DOC);
		map.put(Types.IMG, IMG);
		map.put(Types.MP3, AUDIO);
		map.put(Types.MP4, VIDEO);
		map.put(Types.ELSE, OTHER);
		map.put(Types.RAR, RAR);
		TYPES = map;
	}

	public static ResRawType resolve(String input) {
		for (ResRawType rawType : ResRawType.values()) {
			if (rawType.icon.equals(input)) {
				return rawType;
			}
		}
		return ResRawType.OTHER;
	}
	
	public static boolean isMedia(String input) {
		ResRawType rawType = ResRawType.resolve(input);
		return VIDEO == rawType || AUDIO == rawType;
	}

	/**
	 * 从 Types 枚举变换为 ResType 枚举
	 * 
	 * @param type
	 * @return
	 */
	public static ResRawType fromCoursewareType(String type) {
		return type == null ? OTHER : TYPES.getOrDefault(Types.valueOf(type), OTHER);
	}

	/**
	 * 从试卷类型变换为 RawType 枚举
	 * @param paperType
	 * @return
	 */
	public static ResRawType fromPaperType(Integer paperType) {
		if (paperType == PaperDTO.PAPER_TYPE_NORMAL) {
			return NORMAL_PAPER;
		} else if (paperType == PaperDTO.PAPER_TYPE_ANSWER_SHEET) {
			return ANSWERSHEET;
		} else {
			return ANSWERFILE;
		}
	}

	/**
	 * 从微课类型变换为 RawType 枚举
	 * @param microcourseType
	 * @return
	 */
	public static ResRawType fromMicrocourseType(MicrocourseDTO microcourse) {
		if (microcourse.getMicrocourseFile() != null) {
			return fromCoursewareType(microcourse.getMicrocourseFile().getType());
		} else {
			return VIDEO;
		}
	}
}