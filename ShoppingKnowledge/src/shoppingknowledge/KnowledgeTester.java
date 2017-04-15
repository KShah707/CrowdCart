package shoppingknowledge;

public class KnowledgeTester {

	public static void main(String[] args) {
		String[] receipt = {"sahara bread",
				            "sb garbnzo beans",
				            "sb canola oil",
				            "jfc sesame seed",
				            "cucumbers",
				            "ex lrg tomatoes"};
		KnowledgeEngine engine = new KnowledgeEngine();
		for (String item : receipt) {
			System.out.println(item + " : " + engine.closest(item));
		}
	}

}
