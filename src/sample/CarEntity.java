package sample;

import javax.persistence.*;

@Entity
@Table(name = "car", schema = "carsv2")
public class CarEntity {
    private Integer carId;
    private String carBrand;
    private String carModel;
    private String carCondition;
    private Integer carPrice;
    private Integer carProductionYear;
    private Integer carMileage;
    private Double carEngineCapacity;

    public CarEntity() {

    }

    @Id
    @Column(name = "carID", nullable = false)
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    @Basic
    @Column(name = "carBrand", nullable = false, length = 50)
    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    @Basic
    @Column(name = "carModel", nullable = false, length = 50)
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Basic
    @Column(name = "carCondition", nullable = false, length = 50)
    public String getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(String carCondition) {
        this.carCondition = carCondition;
    }

    @Basic
    @Column(name = "carPrice", nullable = false)
    public Integer getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(Integer carPrice) {
        this.carPrice = carPrice;
    }

    @Basic
    @Column(name = "carProductionYear", nullable = false)
    public Integer getCarProductionYear() {
        return carProductionYear;
    }

    public void setCarProductionYear(Integer carProductionYear) {
        this.carProductionYear = carProductionYear;
    }

    @Basic
    @Column(name = "carMileage", nullable = false)
    public Integer getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(Integer carMileage) {
        this.carMileage = carMileage;
    }

    @Basic
    @Column(name = "carEngineCapacity", nullable = false, precision = 0)
    public Double getCarEngineCapacity() {
        return carEngineCapacity;
    }

    public void setCarEngineCapacity(Double carEngineCapacity) {
        this.carEngineCapacity = carEngineCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarEntity carEntity = (CarEntity) o;

        if (carId != null ? !carId.equals(carEntity.carId) : carEntity.carId != null) return false;
        if (carBrand != null ? !carBrand.equals(carEntity.carBrand) : carEntity.carBrand != null) return false;
        if (carModel != null ? !carModel.equals(carEntity.carModel) : carEntity.carModel != null) return false;
        if (carCondition != null ? !carCondition.equals(carEntity.carCondition) : carEntity.carCondition != null)
            return false;
        if (carPrice != null ? !carPrice.equals(carEntity.carPrice) : carEntity.carPrice != null) return false;
        if (carProductionYear != null ? !carProductionYear.equals(carEntity.carProductionYear) : carEntity.carProductionYear != null)
            return false;
        if (carMileage != null ? !carMileage.equals(carEntity.carMileage) : carEntity.carMileage != null) return false;
        if (carEngineCapacity != null ? !carEngineCapacity.equals(carEntity.carEngineCapacity) : carEntity.carEngineCapacity != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = carId != null ? carId.hashCode() : 0;
        result = 31 * result + (carBrand != null ? carBrand.hashCode() : 0);
        result = 31 * result + (carModel != null ? carModel.hashCode() : 0);
        result = 31 * result + (carCondition != null ? carCondition.hashCode() : 0);
        result = 31 * result + (carPrice != null ? carPrice.hashCode() : 0);
        result = 31 * result + (carProductionYear != null ? carProductionYear.hashCode() : 0);
        result = 31 * result + (carMileage != null ? carMileage.hashCode() : 0);
        result = 31 * result + (carEngineCapacity != null ? carEngineCapacity.hashCode() : 0);
        return result;
    }

    public CarEntity(Integer carId, String carBrand, String carModel, String carCondition, Integer carPrice, Integer carProductionYear, Integer carMileage, Double carEngineCapacity) {
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carCondition = carCondition;
        this.carPrice = carPrice;
        this.carProductionYear = carProductionYear;
        this.carMileage = carMileage;
        this.carEngineCapacity = carEngineCapacity;
    }
}
