import java.util.ArrayList;

public class ThereAndBackAgain {
	public static void main(String[] args) {
		Hobbit frodo = new Hobbit("Frodo");
		Hobbit sam = new Hobbit("Sam");
		Dwarf gimli = new Dwarf("Gimli");

		// Create a traveling party called party1 by creating an array of Travelers
		// and filling it with frodo, sam, and gimli
		Traveler[] party1 = {frodo, sam, gimli};
		// Then, use a loop to make all travelers go a distance of 50 miles
		for (Traveler traveler : party1)
			traveler.travel(50);
		// For each Traveler in the travelingParty, print their name and how far they've
		// traveled in miles.
		for (Traveler traveler : party1)
			System.out.println(traveler.getName() + " has traveled " + traveler.getDistanceTraveled() + " miles.");
	}
}
