package model;
public class Stop {
	private String name;
	private String animal;
	private String place;
	private String object;
	public Stop(String name, String animal, String place, String object) {
		this.name = name;
		this.animal = animal;
		this.place = place;
		this.object = object;
	}
	public int[] calculateScore(Stop o) {
		int[] values = new int[5];
		if (!name.trim().isEmpty())
			if (name.trim().equalsIgnoreCase(o.getName().trim()))
				values[1] = 50;
			else
				values[1] = 100;
		if (!animal.trim().isEmpty())
			if (animal.trim().equalsIgnoreCase(o.getAnimal().trim()))
				values[2] = 50;
			else
				values[2] = 100;
		if (!place.trim().isEmpty())
			if (place.trim().equalsIgnoreCase(o.getPlace().trim()))
				values[3] = 50;
			else
				values[3] = 100;
		if (!object.trim().isEmpty())
			if (object.trim().equalsIgnoreCase(o.getObject().trim()))
				values[4] = 50;
			else
				values[4] = 100;
		values[0] = values[1]+values[2]+values[3]+values[4];
		return values;
	}
	public String getName() {
		return name;
	}
	public String getAnimal() {
		return animal;
	}
	public String getPlace() {
		return place;
	}
	public String getObject() {
		return object;
	}
}