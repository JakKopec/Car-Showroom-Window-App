package sample;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "rating", schema = "carsv2")
public class RatingEntity {
    private Integer ratingId;
    private Double rate;
    private Date rateDate;
    private String text;

    @Id
    @Column(name = "ratingID", nullable = false)
    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }

    @Basic
    @Column(name = "rate", nullable = false, precision = 0)
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "rateDate", nullable = true)
    public Date getRateDate() {
        return rateDate;
    }

    public void setRateDate(Date rateDate) {
        this.rateDate = rateDate;
    }

    @Basic
    @Column(name = "text", nullable = true, length = 100)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingEntity that = (RatingEntity) o;

        if (ratingId != null ? !ratingId.equals(that.ratingId) : that.ratingId != null) return false;
        if (rate != null ? !rate.equals(that.rate) : that.rate != null) return false;
        if (rateDate != null ? !rateDate.equals(that.rateDate) : that.rateDate != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ratingId != null ? ratingId.hashCode() : 0;
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (rateDate != null ? rateDate.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
