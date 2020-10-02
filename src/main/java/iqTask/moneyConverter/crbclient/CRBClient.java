package iqTask.moneyConverter.crbclient;

import iqTask.moneyConverter.objects.ValCurs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;

public class CRBClient {
    private String publicURI = "http://www.cbr.ru/scripts/XML_daily.asp";

    public ValCurs getCurrentValCurs() {
        try {
            URL url = new URL(publicURI);
            JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ValCurs resp = (ValCurs) jaxbUnmarshaller.unmarshal(url);

            return resp;
        } catch (
                MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (
                JAXBException jaxbException) {
            jaxbException.printStackTrace();
        }
        return null;
    }
}
