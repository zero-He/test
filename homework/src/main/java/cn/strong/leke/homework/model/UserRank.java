package cn.strong.leke.homework.model;

import java.util.Map;

import cn.strong.leke.data.mongo.annotations.ObjectId;
import cn.strong.leke.data.mongo.annotations._id;

public class UserRank {

	@_id
	@ObjectId
	public String id;
	private Long userId;
	private String userName;
	private Long classId;
	private String className;
	private Long schoolId;
	private Integer exerciseVal;
	private Integer exercise1;
	private Integer exercise2;
	private Integer exercise3;
	private Integer total1;
	private Integer total2;
	private Integer total3;
	private Integer lastDay;
	private Map<Integer, Integer> counts;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getExerciseVal() {
		return exerciseVal;
	}

	public void setExerciseVal(Integer exerciseVal) {
		this.exerciseVal = exerciseVal;
	}

	public Integer getExercise1() {
		return exercise1;
	}

	public void setExercise1(Integer exercise1) {
		this.exercise1 = exercise1;
	}

	public Integer getExercise2() {
		return exercise2;
	}

	public void setExercise2(Integer exercise2) {
		this.exercise2 = exercise2;
	}

	public Integer getExercise3() {
		return exercise3;
	}

	public void setExercise3(Integer exercise3) {
		this.exercise3 = exercise3;
	}

	public Integer getTotal1() {
		return total1;
	}

	public void setTotal1(Integer total1) {
		this.total1 = total1;
	}

	public Integer getTotal2() {
		return total2;
	}

	public void setTotal2(Integer total2) {
		this.total2 = total2;
	}

	public Integer getTotal3() {
		return total3;
	}

	public void setTotal3(Integer total3) {
		this.total3 = total3;
	}

	public Integer getLastDay() {
		return lastDay;
	}

	public void setLastDay(Integer lastDay) {
		this.lastDay = lastDay;
	}

	public Map<Integer, Integer> getCounts() {
		return counts;
	}

	public void setCounts(Map<Integer, Integer> counts) {
		this.counts = counts;
	}
}
