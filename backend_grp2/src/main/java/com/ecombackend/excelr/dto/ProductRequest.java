package com.ecombackend.excelr.dto;

public class ProductRequest {
	   private String name;
	    private double price;
	    private String imgSrc;
	    private String category;
	    private String storage;
	    private String color;
	    private String brand;
	    private String size;
	    
	    

	    public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}

		// Getters and Setters
	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public double getPrice() {
	        return price;
	    }

	    public void setPrice(double price) {
	        this.price = price;
	    }

	    public String getImgSrc() {
	        return imgSrc;
	    }

	    public void setImgSrc(String imgSrc) {
	        this.imgSrc = imgSrc;
	    }

	    public String getCategory() {
	        return category;
	    }

	    public void setCategory(String category) {
	        this.category = category;
	    }

	    public String getStorage() {
	        return storage;
	    }

	    public void setStorage(String storage) {
	        this.storage = storage;
	    }

	    public String getColor() {
	        return color;
	    }

	    public void setColor(String color) {
	        this.color = color;
	    }

	    public String getBrand() {
	        return brand;
	    }

	    public void setBrand(String brand) {
	        this.brand = brand;
	    }
	}

