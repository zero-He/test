package cn.strong.leke.homework.util;

import java.util.ArrayList;
import java.util.List;

public class MiddleBuilder {

	private Middle middle;

	private MiddleBuilder(String title, String message) {
		this.middle = new Middle();
		this.middle.setTitle(title);
		this.middle.setMessage(message);
	}

	public MiddleBuilder setMessage(String message) {
		this.middle.setMessage(message);
		return this;
	}

	public MiddleBuilder addButton(String name, String link) {
		Button button = new Button();
		button.setName(name);
		button.setLink(link);
		this.middle.buttons.add(button);
		return this;
	}

	public MiddleBuilder setLekeVal(Integer lekeVal) {
		this.middle.setLekeVal(lekeVal);
		return this;
	}

	public MiddleBuilder setExpVal(Integer expVal) {
		this.middle.setExpVal(expVal);
		return this;
	}

	public MiddleBuilder addCloseWindow() {
		this.middle.setCloseWindow(true);
		return this;
	}

	public Middle get() {
		return this.middle;
	}

	public static MiddleBuilder start(String title) {
		return new MiddleBuilder(title, null);
	}

	public static class Middle {
		private String title;
		private String message;
		private Integer expVal;
		private Integer lekeVal;
		private boolean closeWindow;
		private List<Button> buttons = new ArrayList<>();

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public Integer getExpVal() {
			return expVal;
		}

		public void setExpVal(Integer expVal) {
			this.expVal = expVal;
		}

		public Integer getLekeVal() {
			return lekeVal;
		}

		public void setLekeVal(Integer lekeVal) {
			this.lekeVal = lekeVal;
		}

		public boolean isCloseWindow() {
			return closeWindow;
		}

		public void setCloseWindow(boolean closeWindow) {
			this.closeWindow = closeWindow;
		}

		public List<Button> getButtons() {
			return buttons;
		}

		public void setButtons(List<Button> buttons) {
			this.buttons = buttons;
		}
	}

	public static class Button {
		private String name;
		private String link;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}
	}
}
