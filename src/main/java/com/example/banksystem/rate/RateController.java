package com.example.banksystem.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping(path = "/BankSystem/exRates")
public class RateController {

    @Autowired
    private RateService rateService;

    @GetMapping("")
    public String getExchangeRates(Model model) {
        model.addAttribute("exchangeRates", rateService.getExchangeRatesXML());
        return "rate/exchangeRates";
    }

    @RequestMapping("/filterCurrency")
    public String filterRate(@RequestParam String filterCurrency, Model model) {
        Map<String, String> filteredRates = new TreeMap<>();
        if (filterCurrency != null && !filterCurrency.isEmpty()) {
            for (Map.Entry<String, String> value: rateService.getExchangeRatesXML().entrySet()) {
                if (value.getKey().contains(filterCurrency)) {
                    filteredRates.put(value.getKey(), value.getValue());
                }
            }
        }else {
            filteredRates = rateService.getExchangeRatesXML();
        }
        model.addAttribute("exchangeRates", filteredRates);
        return "rate/exchangeRates";
    }
}