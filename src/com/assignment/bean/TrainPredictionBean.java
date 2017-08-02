package com.assignment.bean;

public class TrainPredictionBean implements Comparable<TrainPredictionBean>  {
	private String Car;
	private String DestinationCode;
	private String DestinationName;
	private String Min;
	public String getCar() {
		return Car;
	}
	public void setCar(String car) {
		Car = car;
	}
	public String getDestinationCode() {
		return DestinationCode;
	}
	public void setDestinationCode(String destinationCode) {
		DestinationCode = destinationCode;
	}
	public String getDestinationName() {
		return DestinationName;
	}
	public void setDestinationName(String destinationName) {
		DestinationName = destinationName;
	}
	public String getMin() {
		return Min;
	}
	public void setMin(String min) {
		Min = min;
	}

	
	public int compareTo(TrainPredictionBean tpBean) {
		int tpBeanMin = 0;
		int currentObjMin = 0;
		try{
			 tpBeanMin = Integer.parseInt(tpBean.getMin());
		   }catch(Exception e){
			   tpBeanMin = 0;
		   }
		try{
			  currentObjMin = Integer.parseInt(this.getMin());
		   }catch(Exception e){
			  currentObjMin = 0;
		   }
		
        return currentObjMin - tpBeanMin;
    }
	

}
