package kh.com.kshrd.restaurant.enums;

public enum ImageType {
	MENU(0),
	FOOD(1),
	DRINK(2),
	INSIDE(3),
	OUTSIDE(4);
	
	private int value;
	private ImageType(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
}
