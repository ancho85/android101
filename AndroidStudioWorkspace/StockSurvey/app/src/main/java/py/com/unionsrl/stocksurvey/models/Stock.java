package py.com.unionsrl.stocksurvey.models;

/**
 * Created by ancho on 07/03/15.
 */
public class Stock {

    private Integer code;
    private String name;
    private String lot;
    private Integer qty;
    private String dateTime;
    private String phoneNumber;

    public Stock(Integer code, String name, String lot, Integer qty, String dateTime, String phoneNumber) {
        setCode(code);
        setName(name);
        setLot(lot);
        setQty(qty);
        setDateTime(dateTime);
        setPhoneNumber(phoneNumber);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
