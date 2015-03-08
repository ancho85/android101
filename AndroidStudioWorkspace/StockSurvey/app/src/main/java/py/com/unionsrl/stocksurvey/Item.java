package py.com.unionsrl.stocksurvey;

/**
 * Created by ancho on 07/03/15.
 */
public class Item {
    /**
     *
     */
    private Integer code;
    private String name;
    private String barCode;

    public Item(Integer code, String name, String barCode) {
        setCode(code);
        setName(name);
        setBarCode(barCode);
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}