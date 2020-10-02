package iqTask.moneyConverter.helper;

import iqTask.moneyConverter.crbclient.CRBClient;
import iqTask.moneyConverter.objects.ValCurs;
import iqTask.moneyConverter.objects.Valute;

import javax.annotation.Resource;
import java.util.List;

public class ConvertationHelper {

    @Resource
    private CRBClient crbClient;

    public Double Convertation(int amount, String nameFirst, String nameSecond) {
        ValCurs valCurs = crbClient.getCurrentValCurs();
        List<Valute> list = valCurs.getValute();
        Valute first = null;
        Valute second = null;
        for (int i = 0; i < list.size(); i++) {
            if ( list.get(i).getName().contains(nameFirst) ) {
                first = list.get(i);
            }
            if ( list.get(i).getName().contains(nameSecond) ) {
                second = list.get(i);
            }
        }
        double result = ((amount * extractValuteValue(first) * (extractValuteValue(first)) / (extractValuteValue(second) * extractValuteValue(second))));
        return result;
    }

    private Double extractValuteValue(Valute valute) {
        String formattedValue = valute.getValue().replace(',', '.');
        Double result = Double.parseDouble(formattedValue);
        return result;
    }

}
