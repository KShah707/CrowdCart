import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.javatuples.*;

public class ShoppingList {
	ArrayList<String> list;
	
	public void addItem(String food) {
		list.add(food);
	}
	
	public void removeItem(String food) {
		list.remove(food);
	}
	
	public String getItem(int index) {
		return list.get(index);
	}
	
	public ArrayList<String> getList(){
		return list;
	}
}
