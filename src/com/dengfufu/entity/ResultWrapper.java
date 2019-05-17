package com.dengfufu.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultWrapper{
	/**
	 * 
	 */
	int error;
	String status;
	String date;
	ArrayList<Results> results;
	
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<Results> getResults() {
		return results;
	}
	public void setResults(ArrayList<Results> results) {
		this.results = results;
	}
	
}
