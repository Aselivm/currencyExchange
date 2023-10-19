package org.primshits.currency_exchange.controller;

import org.primshits.currency_exchange.models.ExchangeRate;
import org.primshits.currency_exchange.util.InputStringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/exchangeRate/*")
public class ExchangeRatePage extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String currencyPair = InputStringUtils.parsePathInfo(req);
        ExchangeRate exchangeRate = exchangeRatesService.getExchangeRateFromCurrencyPair(currencyPair);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(),exchangeRate);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        String currencyPair = InputStringUtils.parsePathInfo(req);
        String baseCurrencyCode = currencyPair.substring(0,3);
        String targetCurrencyCode = currencyPair.substring(3);
        double rate = Double.parseDouble(req.getParameter("rate"));
        exchangeRatesService.updateRate(baseCurrencyCode, targetCurrencyCode, rate);
        ExchangeRate exchangeRate = exchangeRatesService.get(baseCurrencyCode,targetCurrencyCode);
        objectMapper.writeValue(resp.getOutputStream(),exchangeRate);
    }
}
