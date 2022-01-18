package com.example.banksystem.rate;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RateService {

    public Map<String,String> getExchangeRatesXML() {
        Map<String,String> rates = new TreeMap<>();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse("http://www.cbr.ru/scripts/XML_daily_eng.asp");
            NodeList nodeList = document.getChildNodes();
            for(int x=0,size= 34; x<size; x++) {
                rates.put(nodeList.item(0).getChildNodes().item(x).getChildNodes().item(3).getTextContent(),nodeList.item(0).getChildNodes().item(x).getChildNodes().item(4).getTextContent());
            }
        } catch (Exception ignored) {}
        return rates;
    }
}