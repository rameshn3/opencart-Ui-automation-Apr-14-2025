package com.apitesting.pojos;

public class Booking{
    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private String additionalneeds;

    public BookingDates getBookingdates(){
        return bookingdates;
    }

    public void setBookingdates(BookingDates bookingdates){
        this.bookingdates=bookingdates;
    }

    private BookingDates bookingdates;



    public Booking(){

    }
    public Booking(String fname,String lname,String aneeds,int tprice,boolean dpaid,BookingDates bdates ){
        setFirstname(fname);
        setLastname(lname);
        setAdditionalneeds(aneeds);
        setDepositpaid(dpaid);
        setTotalprice(tprice);
        setBookingdates(bdates);
    }
    public String getFirstname(){
        return firstname;
    }

    public void setFirstname(String firstname){
        this.firstname=firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public void setLastname(String lastname){
        this.lastname=lastname;
    }

    public int getTotalprice(){
        return totalprice;
    }

    public void setTotalprice(int totalprice){
        this.totalprice=totalprice;
    }

    public boolean isDepositpaid(){
        return depositpaid;
    }

    public void setDepositpaid(boolean depositpaid){
        this.depositpaid=depositpaid;
    }

    public String getAdditionalneeds(){
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds){
        this.additionalneeds=additionalneeds;
    }


}