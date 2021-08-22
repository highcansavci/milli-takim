package model.bundle;
public class Futbolcu {
	
	private int formaNo;
	private String firstName;
	private String lastName;
	private int rating;
	private Found found;
	
	public Futbolcu(int formaNo, String firstName, String lastName) {
		this.formaNo = formaNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.rating = (int)(Math.random() * 5 + 1);
		this.found = Found.NOT_FOUND;
	}
	
	public void setFormaNo(int formaNo) {
		this.formaNo = formaNo;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void changeFoundStatus(Found found) {
		this.found = found;
	}
	
	public Found getFoundStatus() {
		return found;
	}
	
	public void changeRating() {
		this.rating = (int)(Math.random() * 5 + 1);
	}
	
	public int getFormaNo() {
		return formaNo;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public int getRating() {
		return rating;
	}
	
	public String showRating() {
		String star = "";
		for(int i = 0; i < rating; ++i)
			star += "*";
		return star;
	}
	
	@Override
	public String toString() {
		return formaNo + "-" + firstName + " " + lastName + " " + showRating();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		else if(obj instanceof Futbolcu) {
			if(!this.firstName.equals(((Futbolcu) obj).getFirstName()))
				return false;
			if(!this.lastName.equals(((Futbolcu) obj).getLastName()))
				return false;
			if(this.formaNo != ((Futbolcu) obj).getFormaNo())
				return false;
			return true;
		}
		else
			return false;
	}
}
