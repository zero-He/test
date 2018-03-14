package cn.strong.leke.diag.util;

import cn.strong.leke.common.serialize.support.json.JsonUtils;
import cn.strong.leke.context.base.ParametersContext;
import cn.strong.leke.context.user.cst.RoleCst;
import cn.strong.leke.diag.model.Exp;

import java.util.List;

/**
 * 经验转换
 * @author chenxiaoyue
 * @time 2014-12-25上午10:05:11
 */
public class ExpUtil {

	private static final int[] LEVEL_CEILS = {-1, 0, 200, 400, 600, 800, 1000, 2000, 3000, 4000, 5000, 6000, 8000,
			11000, 14000, 17000, 20000, 25000, 30000, 35000, 40000, 45000, 50000, 55000, 60000, 65000, 70000, 75000,
			80000, 85000, 90000, 100000, 100000000};

	public static void main(String[] args) {
		System.out.println(ExpUtil.getLevel(400));
	}

	/**
	 * 根据经验值返回用户等级值
	 * @param expVal 经验值
	 * @return
	 */
	public static int getLevel(int expVal) {
		for (int i = 0; i < LEVEL_CEILS.length; i++) {
			if (LEVEL_CEILS[i] > expVal) {
				return i - 1;
			}
		}
		return LEVEL_CEILS.length - 1;
	}

	/**
	 * 根据等级获取最低所需经验值
	 * @param level
	 * @return
	 */
	public static int getMinExp(int level) {
		return LEVEL_CEILS[level];
	}

	/**
	 * 根据旧经验值和新经验值判断是否等级提升。
	 * @param oldExp 旧经验值
	 * @param newExp 新经验值
	 * @return
	 */
	public static boolean isUpLevel(int oldExp, int newExp) {
		return getLevel(oldExp) < getLevel(newExp);
	}

	/**
	 * 获取经验对象
	 * @param expVal
	 * @param roleId
	 * @return
	 */
	public static Exp getExp(Integer expVal, Long roleId) {
		Exp exp = new Exp();
		exp.setExp(expVal == null ? 0 : expVal);
		exp.setLevel(getLevel(exp.getExp()));
		int levelExp = getMinExp(exp.getLevel());
		int _levelExp = getMinExp(exp.getLevel() + 1) - 1;
		exp.setHonor(honor(exp.getLevel(), roleId));
		exp.setLackExp(_levelExp - exp.getExp());
		if (_levelExp - levelExp != 0) {
			exp.setPercent((exp.getExp() - levelExp) * 100 / (_levelExp - levelExp));
		} else {
			exp.setPercent(0);
		}
		return exp;
	}

	/**
	 * 获取头衔
	 * @param level
	 * @param roleId
	 * @return
	 */
	public static String honor(Integer level, Long roleId) {
		String json = null;
		if (RoleCst.STUDENT.equals(roleId)) {
			json = ParametersContext.getString("LEVEL_STUDENT_LIST");
		} else {
			json = ParametersContext.getString("LEVEL_TEACHER_LIST");
		}
		List<String> levels = JsonUtils.readList(json, String.class);
		level = level < 0 ? 0 : level;
		if (level < levels.size()) {
			return levels.get(level);
		}
		return levels.get(levels.size() - 1);
	}
}
