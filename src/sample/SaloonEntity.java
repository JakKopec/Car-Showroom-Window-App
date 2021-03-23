package sample;

import javax.persistence.*;

@Entity
@Table(name = "saloon", schema = "carsv2")
public class SaloonEntity {
    private Integer saloonId;
    private String saloonName;
    private String saloonLocation;

    @Id
    @Column(name = "saloonID", nullable = false)
    public Integer getSaloonId() {
        return saloonId;
    }

    public void setSaloonId(Integer saloonId) {
        this.saloonId = saloonId;
    }

    @Basic
    @Column(name = "saloonName", nullable = false, length = 50)
    public String getSaloonName() {
        return saloonName;
    }

    public void setSaloonName(String saloonName) {
        this.saloonName = saloonName;
    }

    @Basic
    @Column(name = "saloonLocation", nullable = false, length = 50)
    public String getSaloonLocation() {
        return saloonLocation;
    }

    public void setSaloonLocation(String saloonLocation) {
        this.saloonLocation = saloonLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaloonEntity that = (SaloonEntity) o;

        if (saloonId != null ? !saloonId.equals(that.saloonId) : that.saloonId != null) return false;
        if (saloonName != null ? !saloonName.equals(that.saloonName) : that.saloonName != null) return false;
        if (saloonLocation != null ? !saloonLocation.equals(that.saloonLocation) : that.saloonLocation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = saloonId != null ? saloonId.hashCode() : 0;
        result = 31 * result + (saloonName != null ? saloonName.hashCode() : 0);
        result = 31 * result + (saloonLocation != null ? saloonLocation.hashCode() : 0);
        return result;
    }
}
