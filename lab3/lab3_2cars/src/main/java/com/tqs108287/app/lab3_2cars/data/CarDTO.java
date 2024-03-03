package com.tqs108287.app.lab3_2cars.data;

public class CarDTO {
    private Long id;
    private String maker;
    private String model;

    public static CarDTO fromEmployeeEntity(Car car){
        return new CarDTO(car.getCarId(), car.getMaker(), car.getModel());
    }

    public Car toCarEntity(){
        return new Car(getId(), getMaker(), getModel());
    }

    public CarDTO(Long id, String maker, String model) {
        this.id = id;
        this.maker = maker;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
