package cn.strong.leke.homework.model;

import cn.strong.leke.paper.sheet.model.position.Position;

public class XPosition extends Position {
	private static final long serialVersionUID = 1L;
	private int idx;

	public XPosition() {
		super();
	}

	public XPosition(int idx, Position position) {
		super(position.getX(), position.getY(), position.getW(), position.getH());
		this.idx = idx;
	}

	public XPosition(int idx, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.idx = idx;
	}

	public void max(int val) {
		this.setX(this.getX() - val);
		this.setY(this.getY() - val);
		this.setW(this.getW() + 2 * val);
		this.setH(this.getH() + 2 * val);
	}

	public void zoom(int val) {
		this.setX(this.getX() * val / 100);
		this.setY(this.getY() * val / 100);
		this.setW(this.getW() * val / 100);
		this.setH(this.getH() * val / 100);
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}
}