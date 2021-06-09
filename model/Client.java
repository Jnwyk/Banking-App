package model;

import java.io.Serializable;

public class Client implements Serializable {

    private int id;
    private String name;
    private String sname;
    private String pesel;
    private Adress adress;
    private double balance;

    public Client(int id, String name, String sname, String pesel, Adress adres, double balance) {
        this.id = id;
        this.name = name;
        this.sname = sname;
        this.pesel = pesel;
        this.adress = adres;
        this.balance = balance;
        System.out.println("Account created!");
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSname() {
        return sname;
    }

    public String getPesel() {
        return pesel;
    }

    public Adress getAdress() {
        return adress;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws InvalidAmount{
        if(amount <= 0)
            throw new InvalidAmount("Incorrect amount");
        this.balance += amount;
        System.out.println("Deposited");
    }

    public void withdraw(double amount) throws InvalidAmount{
        if(amount <= 0 || amount > this.balance)
            throw new InvalidAmount("Ziomus masz za malo hajsu");
        this.balance -= amount;
        System.out.println("Withdrawn");
    }

    public void transfer(Client client, double amount) throws InvalidAmount{
        if(amount <= 0 || amount >= this.balance)
            throw new InvalidAmount("Ziomus masz za malo hajsu");
        this.withdraw(amount);
        client.deposit(amount);
    }
}

