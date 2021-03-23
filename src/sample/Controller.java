package sample;

import org.hibernate.*;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller
{
    SessionFactory factory;
    Controller(SessionFactory factory)
    {
        this.factory=factory;
    }

    public Integer addVehicle(int carID,String brand,String model,String itemCondition,int price,int productionYear,int mileage,double engineCapacity)
    {
        Session session = factory.openSession();
        org.hibernate.Transaction transaction = null;
        Integer vehicleID = null;

        try {
            transaction = session.beginTransaction();
            CarEntity carEntity = new CarEntity(carID,brand,model,itemCondition,price,productionYear,mileage,engineCapacity);
            vehicleID = (Integer) session.save(carEntity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return vehicleID;
    }


    public List<SaloonEntity> listSaloons()
    {
        Session session = factory.openSession();
        org.hibernate.Transaction transaction = null;
        List <SaloonEntity> saloonEntities = session.createQuery("from SaloonEntity").list();
        return saloonEntities;
    }


    /* Method to  READ all the vehicles */
    public List<CarEntity> listVehicles( )
    {
        Session session = factory.openSession();
        org.hibernate.Transaction transaction = null;
        List <CarEntity> vehicles = session.createQuery("from CarEntity ").list();
        return vehicles;
        /*
        try {
            transaction = session.beginTransaction();
            /*List<Vehicle>*/ //vehicles = session.createQuery("FROM Vehicle").list();

            /*
            for (Iterator iterator = vehicles.iterator(); iterator.hasNext();)
            {
                Vehicle vhcl = (Vehicle) iterator.next();
                System.out.priceint("Brand: " + vhcl.getBrand());
                System.out.priceint("  Model: " + vhcl.getModel());
                System.out.priceintln("  Production Year: " + vhcl.getProduction_year());
            }
            */
        /*
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.priceintStackTrace();
        } finally {
            session.close();
        }
        return vehicles;*/
    }

    public List<CarEntity> searchBrand(String Brand)
    {
        Session session = factory.openSession();
        List <CarEntity> vehicles = session.createQuery("FROM CarEntity WHERE carBrand = '"+Brand+"'").list();
        return vehicles;
    }

    public List<CarEntity> searchBySaloonID(String saloonID)
    {
        Session session = factory.openSession();
        Integer id= Integer.parseInt(saloonID);
        List <CarEntity> vehicles = session.createQuery("FROM CarEntity WHERE saloonBySaloonId = '"+saloonID+"'").list();
        //List <CarEntity> vehicles = new ArrayList<>();
        //Query q = session.createQuery("from CarEntity where saloonId = :id");
        return vehicles;
    }



    /* Method to DELETE an vehicle from the records */
    public void deleteVehicle(Integer VehicleID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            CarEntity carEntity = (CarEntity) session.get(CarEntity.class, VehicleID);
            session.delete(carEntity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /*
    public List<CarEntity> searchBySaloonID(int id)
    {
        Session session = factory.openSession();
        Criteria criteria=session.createCriteria(Vehicle.class);
        criteria.add(Restrictions.eq("saloonId",id));
        List<CarEntity> vehicles=criteria.list();
        return vehicles;
    }*/
}

