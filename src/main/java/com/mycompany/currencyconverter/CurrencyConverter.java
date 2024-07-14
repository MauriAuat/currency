package com.mycompany.currencyconverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CurrencyConverter {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/b3cd548f403ae5a8d97a25ac/latest/";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Convertidor de Divisas API: api.apilayer.com/exchangerates_data");

        JLabel fromLabel = new JLabel("De");
        JComboBox<String> baseCurrency;
        String[] currencies = {"USD - Dollar", "EUR - Euro", "ARS - Peso Argentino", "MXN - Peso Mexicano", "CAD - Dolar Canadiense", "GBP - Libra Esterlina",
                "JPY - Yen", "CNH - Yuan", "SEK - Corona Sueca"};
        baseCurrency = new JComboBox<>(currencies);

        JLabel toLabel = new JLabel("a:");
        JComboBox<String> targetCurrency;
        targetCurrency = new JComboBox<>(currencies);
        JLabel amountLabel = new JLabel("Monto:");

        JTextField amount = new JTextField(10);

        JButton convertButton = new JButton("Convertir");

        JPanel panel = new JPanel();
        panel.add(fromLabel);
        panel.add(baseCurrency);
        panel.add(toLabel);
        panel.add(targetCurrency);
        panel.add(amountLabel);
        panel.add(amount);
        panel.add(convertButton);

        frame.setLocationRelativeTo(null);
        frame.setBounds(400, 200, 400, 200);
        panel.setBounds(400, 200, 400, 200);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String baseCurrencyCode = extractCurrencyCode((String) baseCurrency.getSelectedItem());
                    String targetCurrencyCode = extractCurrencyCode((String) targetCurrency.getSelectedItem());
                    double amountValue = Double.parseDouble(amount.getText());

                    double exchangeRate = getExchangeRate(baseCurrencyCode, targetCurrencyCode);
                    double convertedAmount = amountValue * exchangeRate;

                    JOptionPane.showMessageDialog(frame, String.format("%.2f %s es %.2f %s", amountValue, baseCurrencyCode, convertedAmount, targetCurrencyCode));
                } catch (IOException | InterruptedException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al convertir la moneda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static double getExchangeRate(String baseCurrency, String targetCurrency) throws IOException, InterruptedException {
        String url = API_URL + baseCurrency;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Failed to get exchange rate");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response.body());
        JsonNode ratesNode = rootNode.get("conversion_rates");

        if (ratesNode == null || !ratesNode.has(targetCurrency)) {
            throw new IOException("Failed to get exchange rate for target currency");
        }

        return ratesNode.get(targetCurrency).asDouble();
    }

    private static String extractCurrencyCode(String currency) {
        return currency.split(" - ")[0];
    }
}
