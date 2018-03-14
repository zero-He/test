package cn.strong.leke.homework.model;

public enum SheetErr {

	// 二维码异常
	UNKNOWN_QRCODE(10101),
	// 校正块异常
	UNKNOWN_ADJUST(10102),
	// 乐号异常
	UNKNOWN_LEKENO(10201),
	// 作业匹配异常
	NOMATCH_HOMEWORK(20102),
	// 重页异常
	PAGE_REPEAT(20201),
	// 缺页异常
	PAGE_MISSING(20202),
	// 定位点异常
	UNSHARP_POSITION(20301),
	// 填涂异常
	UNSHARP_FILLING(20302),
	// 疑似异常
	UNSHARP_SUSPECT(20401);

	public final Integer code;

	private SheetErr(Integer code) {
		this.code = code;
	}

	public boolean is(Integer errorNo) {
		return this.code.equals(errorNo);
	}

	public static boolean noerr(Integer errorNo) {
		return errorNo == 0 || UNSHARP_SUSPECT.code.equals(errorNo);
	}

	public static boolean errfill(Integer errorNo) {
		return UNSHARP_POSITION.code.equals(errorNo) || UNSHARP_FILLING.code.equals(errorNo);
	}

	public static boolean errpage(Integer errorNo) {
		return PAGE_REPEAT.code.equals(errorNo) || PAGE_MISSING.code.equals(errorNo);
	}

	public static boolean errmatch(Integer errorNo) {
		return NOMATCH_HOMEWORK.code.equals(errorNo);
	}
}
