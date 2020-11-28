package com.raven.app.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "common_items")
public class Common {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="itemid")
	private int itemid;
	
	@Column(name="Username1")
	private String username1;
	
	@Column(name="Username2")
	private String username2;
	
	@Column(name="Category")
	private String category;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Quantity")
	private int quantity;
	
	@Column(name="Price")
	private double price;
	
	public int getItemid() {
		return itemid;
	}
	public void setItemid(int itemid) {
		this.itemid = itemid;
	}
	public String getUsername1() {
		return username1;
	}
	public void setUsername1(String username1) {
		this.username1 = username1;
	}
	public String getUsername2() {
		return username2;
	}
	public void setUsername2(String username2) {
		this.username2 = username2;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Common [itemid=" + itemid + ", username1=" + username1 + ", username2=" + username2 + ", category="
				+ category + ", name=" + name + ", quantity=" + quantity + ", price=" + price + "]";
	}
}
