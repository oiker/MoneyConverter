package iqTask.moneyConverter.objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="ValCurs")
public class ValCurs {


    private String Date;

    private String name;

    private List<iqTask.moneyConverter.objects.Valute> Valute;

    public ValCurs(){}
    public ValCurs(String Date, String name, List<Valute> Valute){
        super();
        this.Date = Date;
        this.name = name;
        this.Valute = Valute;
    }

    @XmlAttribute(name = "Date")
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Valute")
    public List<Valute> getValute() {
        return Valute;
    }

    public void setValute(List<Valute> Valute) {
        this.Valute = Valute;
    }

    @Override
    public String toString(){
        String valCurs =  "Date = " + this.Date + "\n" + "name = " + this.name + "\n Valutes: \n";
        for (Valute valute : this.Valute) {
            valCurs = valCurs + valute.toString();
        }
        return valCurs;
    }
}
