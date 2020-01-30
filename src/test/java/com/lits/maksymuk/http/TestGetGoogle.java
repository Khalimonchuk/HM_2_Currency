package com.lits.maksymuk.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.squareup.okhttp.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TestGetGoogle {




    @Test
    public void testGetGoogle() throws IOException {

        // Initialize HTTP Client
        OkHttpClient okHttpClient = new OkHttpClient();

        // Prepare HTTP Request
        Request getGoogleRequest = new Request.Builder()
                .url("https://www.google.com/")
                .build();

        // Execute Request / Obtain response
        Response getGoogleResponse = okHttpClient
                .newCall(getGoogleRequest)
                .execute();

        String responseContentType = getGoogleResponse.header("Content-Type");

        Assert.assertEquals(getGoogleResponse.code(), 200);
        Assert.assertEquals(responseContentType, "text/html; charset=ISO-8859-1");
    }



    @Test (dataProvider = "RateTests")
    public void testGetCurrencyExchangeRate(String currencyName, String currencyCode, BigDecimal amount) throws IOException {

        // Initialize HTTP Client
        OkHttpClient okHttpClient = new OkHttpClient();

        // Prepare HTTP Request
        Request getNbuRequest = new Request.Builder()
                .url("https://bank.gov.ua/NBU_Exchange/exchange?date=20.01.2020&json")
                .build();

        // Execute Request / Obtain response
        Response getNbuResponse = okHttpClient
                .newCall(getNbuRequest)
                .execute();

        // Create
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Response string
        String string = getNbuResponse.body().string();

        // Tells Jackson how to read array
        CollectionType collectionType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, CurrencyRate.class);

        List<CurrencyRate> currencyRates = objectMapper
                .readValue(string, collectionType);

        //for (CurrencyRate currencyRate : currencyRates) {
        //    System.out.println(currencyRate);
        //}


        CurrencyRate a = currencyRates.stream()
                .filter(CurrencyRate -> currencyName.equals(CurrencyRate.getCurrencyCodeL()))
                .filter(CurrencyRate -> currencyCode.equals(CurrencyRate.getCurrencyCode()))
                .findAny()
                .orElse(null);

        Assert.assertEquals(getNbuResponse.code(), 200);
        Assert.assertEquals(a.getAmount(), amount);


        // HOMEWORK WRITE ASSERTS
        // Check Currency rate by currency code
        // USD amount is 24.2527



    }

    @Test
    public void testLogin() throws IOException {

        // 1 DOWNLOAD POSTMAN
        // 2 REGISTER USER (via POSTMAN)
        // 3 ACTIVATE USER (via EMAIL)

        // 4 LOGIN USER (via JAVA CODE)
        OkHttpClient client = new OkHttpClient();

        Request postLogin = new Request.Builder()
                .url("https://europe-west2-search-app-263e2.cloudfunctions.net/webapp/api/auth/login")
                .post(RequestBody.create(MediaType.parse("application/json"), "{\n" +
                        "\t\"email\":\"drolgmaks+16@gmail.com\",\n" +
                        "\t\"password\":\"Qwerty123456\"\n" +
                        "}")).build();

        Response execute = client.newCall(postLogin).execute();

        // 5 CONVERT RESPONSE TO JAVA MODEL
    }
     

    public static class CurrencyRate {
        @JsonProperty("StartDate")
        private String StartDate;
        @JsonProperty("TimeSign")
        private String TimeSign;
        @JsonProperty("CurrencyCode")
        private String CurrencyCode;
        @JsonProperty("CurrencyCodeL")
        private String CurrencyCodeL;
        @JsonProperty("Units")
        private int Units;
        @JsonProperty("Amount")
        private BigDecimal Amount;

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String startDate) {
            StartDate = startDate;
        }

        public String getTimeSign() {
            return TimeSign;
        }

        public void setTimeSign(String timeSign) {
            TimeSign = timeSign;
        }

        public String getCurrencyCode() {
            return CurrencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            CurrencyCode = currencyCode;
        }

        public String getCurrencyCodeL() {
            return CurrencyCodeL;
        }

        public void setCurrencyCodeL(String currencyCodeL) {
            CurrencyCodeL = currencyCodeL;
        }

        public int getUnits() {
            return Units;
        }

        public void setUnits(int units) {
            Units = units;
        }

        public BigDecimal getAmount() {
            return Amount;
        }

        public void setAmount(BigDecimal amount) {
            Amount = amount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CurrencyRate that = (CurrencyRate) o;
            return Objects.equals(CurrencyCodeL, that.CurrencyCodeL);
        }

        @Override
        public int hashCode() {
            return Objects.hash(CurrencyCodeL);
        }

        @Override
        public String toString() {
            return "CurrencyRate{" +
                    "StartDate='" + StartDate + '\'' +
                    ", TimeSign='" + TimeSign + '\'' +
                    ", CurrencyCode='" + CurrencyCode + '\'' +
                    ", CurrencyCodeL='" + CurrencyCodeL + '\'' +
                    ", Units='" + Units + '\'' +
                    ", Amount='" + Amount + '\'' +
                    '}';
        }
    }


    @DataProvider
    public Object[][] RateTests() {

        return new Object[][] {
                {"USD", "840", BigDecimal.valueOf(24.2527)},
                {"AZN", "944", BigDecimal.valueOf(14.2831)},
                {"NOK", "578", BigDecimal.valueOf(2.7237)},
                {"XDR", "960", BigDecimal.valueOf(33.4831)},
                {"XPD", "964", BigDecimal.valueOf(57762.17)}

        };


}}
