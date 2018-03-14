package cn.strong.leke.diag.util;

import cn.strong.leke.diag.model.teachingMonitor.RankBean;
import cn.strong.leke.diag.model.teachingMonitor.RankResultBean;
import cn.strong.leke.diag.model.teachingMonitor.ReturnResultBean;
import cn.strong.leke.diag.model.teachingMonitor.resource.ResourceRankBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 设置排行工具类
 * @Author LIU.SHITING
 * @Version 1.4
 * @Date 2017-07-28 14:12:13
 */
public class SetRankUtils<T extends RankBean> {


	/**
	 * 设置排行前5名和后5名(根据参数中的汇总类型)
	 * @param rankBeanList
	 * @param ParamType
	 * @return
	 */
	public RankResultBean getRankData(List<T> rankBeanList, String ParamType) {
		List<T> rankFrontBeanList = new ArrayList<>();
		List<T> rankBackBeanList = new ArrayList<>();
		if (rankBeanList.size() > 0 && rankBeanList.size() <= 5) {
			rankBeanList.forEach(b -> {
				if (ParamType.equals("BigDecimal")) {
					rankFrontBeanList.add(setRankBeanByBigDecimal(b.getTeacherName(), handlerPoint(b.getTotalLevel()), true));
				} else {
					rankFrontBeanList.add(setRankBeanByInt(b.getTeacherName(), b.getTotalCount(), true));
				}

			});
			for (int i = 0; i < 5 - rankBeanList.size(); i++) {
				if (ParamType.equals("BigDecimal")) {
					rankFrontBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), true));
				} else {
					rankFrontBeanList.add(setRankBeanByInt("", 0, true));
				}

			}
			for (int i = 0; i < 5; i++) {
				if (ParamType.equals("BigDecimal")) {
					rankBackBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), true));
				} else {
					rankBackBeanList.add(setRankBeanByInt("", 0, true));
				}

			}
		} else if (rankBeanList.size() > 5 && rankBeanList.size() <= 10) {
			rankFrontBeanList.addAll(rankBeanList.stream().limit(5).collect(Collectors.toList()));
			List<T> skip = rankBeanList.stream().skip(5).collect(Collectors.toList());
			skip.forEach(b -> {
				if (ParamType.equals("BigDecimal")) {
					rankBackBeanList.add(setRankBeanByBigDecimal(b.getTeacherName(), handlerPoint(b.getTotalLevel()), true));
				} else {
					rankBackBeanList.add(setRankBeanByInt(b.getTeacherName(), b.getTotalCount(), true));
				}

			});
			for (int i = 0; i < 5 - skip.size(); i++) {
				if (ParamType.equals("BigDecimal")) {
					rankBackBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), true));
				} else {
					rankBackBeanList.add(setRankBeanByInt("", 0, true));
				}

			}

		} else if (rankBeanList.size() > 10) {
			rankFrontBeanList.addAll(rankBeanList.stream().limit(5).collect(Collectors.toList()));
			rankBackBeanList.addAll(rankBeanList.stream().skip(rankBeanList.size() - 5).collect(Collectors.toList()));
		} else {
			for (int i = 0; i < 5; i++) {
				if (ParamType.equals("BigDecimal")) {
					rankFrontBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), true));
					rankBackBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), true));
				} else {
					rankFrontBeanList.add(setRankBeanByInt("", 0, true));
					rankBackBeanList.add(setRankBeanByInt("", 0, true));
				}

			}

		}

		RankResultBean<T> resultBean = new RankResultBean<T>();
		resultBean.setRankFrontBeanList(rankFrontBeanList);
		resultBean.setRankBackBeanList(rankBackBeanList);
		return resultBean;

	}

	/**
	 * 设置排行前5名和后5名(根据参数中的汇总类型)
	 * @param rankBeanList
	 * @param ParamType
	 * @return
	 */
	public RankResultBean getRankData(List<T> rankBeanList, String ParamType, boolean isTeacher) {
		List<T> rankFrontBeanList = new ArrayList<>();
		List<T> rankBackBeanList = new ArrayList<>();
		if (rankBeanList.size() > 0 && rankBeanList.size() <= 5) {
			rankBeanList.forEach(b -> {
				if (isTeacher) {
					if (ParamType.equals("BigDecimal")) {
						rankFrontBeanList.add(setRankBeanByBigDecimal(b.getTeacherName(), handlerPoint(b.getTotalLevel()), true));
					} else {
						rankFrontBeanList.add(setRankBeanByInt(b.getTeacherName(), b.getTotalCount(), true));
					}
				} else {
					if (ParamType.equals("BigDecimal")) {
						rankFrontBeanList.add(setRankBeanByBigDecimal(b.getStudentName(), handlerPoint(b.getTotalLevel()), false));
					} else {
						rankFrontBeanList.add(setRankBeanByInt(b.getStudentName(), b.getTotalCount(), false));
					}
				}


			});
			for (int i = 0; i < 5 - rankBeanList.size(); i++) {
				if (isTeacher) {
					if (ParamType.equals("BigDecimal")) {
						rankFrontBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), true));
					} else {
						rankFrontBeanList.add(setRankBeanByInt("", 0, true));
					}
				} else {
					if (ParamType.equals("BigDecimal")) {
						rankFrontBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), false));
					} else {
						rankFrontBeanList.add(setRankBeanByInt("", 0, false));
					}
				}

			}
			for (int i = 0; i < 5; i++) {
				if (isTeacher) {
					if (ParamType.equals("BigDecimal")) {
						rankBackBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), true));
					} else {
						rankBackBeanList.add(setRankBeanByInt("", 0, true));
					}
				} else {
					if (ParamType.equals("BigDecimal")) {
						rankBackBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), false));
					} else {
						rankBackBeanList.add(setRankBeanByInt("", 0, false));
					}
				}

			}
		} else if (rankBeanList.size() > 5 && rankBeanList.size() <= 10) {
			rankFrontBeanList.addAll(rankBeanList.stream().limit(5).collect(Collectors.toList()));
			List<T> skip = rankBeanList.stream().skip(5).collect(Collectors.toList());
			skip.forEach(b -> {
				if (isTeacher) {
					if (ParamType.equals("BigDecimal")) {
						rankBackBeanList.add(setRankBeanByBigDecimal(b.getTeacherName(), handlerPoint(b.getTotalLevel()), true));
					} else {
						rankBackBeanList.add(setRankBeanByInt(b.getTeacherName(), b.getTotalCount(), true));
					}
				} else {
					if (ParamType.equals("BigDecimal")) {
						rankBackBeanList.add(setRankBeanByBigDecimal(b.getStudentName(), handlerPoint(b.getTotalLevel()), false));
					} else {
						rankBackBeanList.add(setRankBeanByInt(b.getStudentName(), b.getTotalCount(), false));
					}
				}

			});
			for (int i = 0; i < 5 - skip.size(); i++) {
				if (isTeacher) {
					if (ParamType.equals("BigDecimal")) {
						rankBackBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), true));
					} else {
						rankBackBeanList.add(setRankBeanByInt("", 0, true));
					}
				} else {
					if (ParamType.equals("BigDecimal")) {
						rankBackBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), false));
					} else {
						rankBackBeanList.add(setRankBeanByInt("", 0, false));
					}
				}

			}

		} else if (rankBeanList.size() > 10) {
			rankFrontBeanList.addAll(rankBeanList.stream().limit(5).collect(Collectors.toList()));
			rankBackBeanList.addAll(rankBeanList.stream().skip(rankBeanList.size() - 5).collect(Collectors.toList()));
		} else {
			for (int i = 0; i < 5; i++) {
				if (isTeacher) {
					if (ParamType.equals("BigDecimal")) {
						rankFrontBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), true));
						rankBackBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), true));
					} else {
						rankFrontBeanList.add(setRankBeanByInt("", 0, true));
						rankBackBeanList.add(setRankBeanByInt("", 0, true));
					}
				} else {
					if (ParamType.equals("BigDecimal")) {
						rankFrontBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), false));
						rankBackBeanList.add(setRankBeanByBigDecimal("", BigDecimal.valueOf(0), false));
					} else {
						rankFrontBeanList.add(setRankBeanByInt("", 0, false));
						rankBackBeanList.add(setRankBeanByInt("", 0, false));
					}
				}

			}

		}

		RankResultBean<T> resultBean = new RankResultBean<T>();
		resultBean.setRankFrontBeanList(rankFrontBeanList);
		resultBean.setRankBackBeanList(rankBackBeanList);
		return resultBean;

	}

	/**
	 * 设置排名bean
	 * @param name
	 * @param totalCount
	 * @return
	 */
	private T setRankBeanByInt(String name, int totalCount, boolean isTeacher) {
		RankBean bean = new RankBean();
		if(isTeacher){
			bean.setTeacherName(name);
		}else {
			bean.setStudentName(name);
		}
		bean.setTotalCount(totalCount);
		return (T) bean;
	}

	private T setRankBeanByBigDecimal(String name, BigDecimal totalLevel, boolean isTeacher) {
		RankBean bean = new RankBean();
		if (isTeacher) {
			bean.setTeacherName(name);
		} else {
			bean.setStudentName(name);
		}
		bean.setTotalLevel(totalLevel);
		return (T) bean;
	}

	/**
	 * 四舍五入保留小数点后一位
	 * @param param
	 * @return
	 */
	private BigDecimal handlerPoint(BigDecimal param){
		return param.setScale(1, BigDecimal.ROUND_HALF_UP);
	}

}
