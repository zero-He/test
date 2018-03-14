package cn.strong.leke.homework.util;

import java.util.List;

public class PhaseUtils {

	public static final Integer PHASE_1 = 1;
	public static final Integer PHASE_2 = 2;
	public static final Integer PHASE_3 = 4;
	
	/**
	 * 将使用阶段，转换成  组合使用阶段
	 * @param usePhases
	 * @return
	 */
	public static Integer combo(List<Integer> usePhases){
		Integer comboPhase = parsePhase(usePhases.get(0));
		if(usePhases.size() > 1) {
			for (int i = 1;i< usePhases.size(); i++) {
				comboPhase = comboPhase | parsePhase(usePhases.get(i)); 
			}
		}
		return comboPhase;
	}

	public static Boolean isLastPhase(Integer usePhase, Integer comboPhase) {
		Integer phase = parsePhase(usePhase);
		if (PHASE_3.equals(phase)) {
			return true;
		} else {
			return PHASE_1.equals(phase) && !validatePhase(comboPhase, PHASE_2) && !validatePhase(comboPhase, PHASE_3)
					|| PHASE_2.equals(phase) && !validatePhase(comboPhase, PHASE_3);
		}
	}
	
	/**
	 * 将usePhase 
	 * @param usePhase
	 * @return
	 */
	public static Integer parsePhase(Integer usePhase){
		if(usePhase.equals(3)) {
			return PHASE_3;
		}
		return usePhase;
	}
	
	/** 校验 comboPhase 是否包含 phase 使用阶段
	 * @param comboPhase
	 * @param phase
	 * @return
	 */
	public static boolean validatePhase(Integer comboPhase, Integer phase) {
		return (comboPhase & phase) == phase;
	}

}
