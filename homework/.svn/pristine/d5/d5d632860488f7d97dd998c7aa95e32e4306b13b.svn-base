package cn.strong.leke.homework.model;


public enum Kemu{
	
	Default(0L, "default"),
	Chinese(1L, "chinese"),
	Math(2L, "math"),
	English(3L, "english"),
	Physics(4L, "physics"),
	Chemistry(5L, "chemistry"),
	Biology(6L, "biology"),
	Politics(7L, "politics"),
	History(8L, "history"),
	Geography(9L, "geography"),
	Science(10L, "science"),
	Art(28L, "art"),
	Pe(29L, "pe"),
	Morality(41L, "morality");
	private Kemu(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	private String name;
	
	private Long id;
	
	public String getName() {
		return name;
	}
	public Long getId() {
		return id;
	}
	public static String getKemu(Long id) {
		for(Kemu v:Kemu.values()){
			if(v.id.equals(id)){
				return v.getName();
			}
		}
		return Default.getName();
	}
}
