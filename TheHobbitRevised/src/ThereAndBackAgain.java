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
		for (Traveler t : party1)
			System.out.println(t.getName() + " has traveled " + t.getDistanceTraveled() + " miles.");

		System.out.println("\n\nPART 2: \n");

		String[] dwarfNames = {"Fili", "Kili", "Dori", "Ori", "Nori", "Balin", "Dwalin",
		"Oin", "Gloin", "Bifur", "Bofur", "Bombur", "Thorin"};

		// Make a new ArrayList to hold a 2nd party of Travelers called party2:
		ArrayList<Traveler> party2 = new ArrayList<>();
		// Make a new Hobbit called "Bilbo" and add him to party2
		party2.add(new Hobbit("Bilbo"));
		// Make a new Wizard called "Gandalf" and add him to party2.
		party2.add(new Wizard("Gandalf", "Grey"));

		//write createParty
		// Call the createParty method and pass it party2 and the dwarfNames array.
		// create party should add all the new dwarves to party2,
		createParty(party2, dwarfNames);

		//Write allTravel
		// Finally, call the allTravel method passing it party2 and 100 (representing
		// the 100 miles that party2 has traveled together.
		allTravel(party2, 100);
		//Make sure your code prints out the name and distances party2 has traveled.

		for (Traveler t : party2)
			System.out.println(t.getName() + " has traveled " + t.getDistanceTraveled() + " miles.");
	}

	private static void createParty(ArrayList<Traveler> party, String[] nameList)  {
		for (String name : nameList)
			party.add(new Dwarf(name));
	}

	private static void allTravel(ArrayList<Traveler> party, int miles) {
		for (Traveler traveler: party)
			traveler.travel(miles);
	}
}
