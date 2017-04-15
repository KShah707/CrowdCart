package com.google.android.gms.samples.vision.ocrreader;
import java.util.ArrayList;

public class ShoppingList {
	ArrayList<String> list;
	
	public void addItem(String food) {
		list.add(food);
	}
	
	public void removeItem(String food) {
		list.remove(food);
	}
	
	public void setList(ArrayList<String> list) {
		this.list = list;
	}
	
	public String getItem(int index) {
		return list.get(index);
	}
	
	public ArrayList<String> getList(){
		return list;
	}
}
