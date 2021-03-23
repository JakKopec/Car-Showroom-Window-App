package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Transaction implements Serializable
{
    public int vehicleID;
    public String name;
    public String brand;

    public Transaction()
    {
        this.vehicleID = -1;
        this.name = "404";
        this.brand="none";
    }

    public Transaction(int vehicleID, String name,String brand)
    {
        this.vehicleID = vehicleID;
        this.name = name;
        this.brand=brand;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        vehicleID = in.read();
        name = in.readUTF();
        brand=in.readUTF();
    }

    private void writeObject(ObjectOutputStream out) throws IOException
    {
        out.write(vehicleID);
        out.writeUTF(name);
        out.writeUTF(brand);
    }
}
