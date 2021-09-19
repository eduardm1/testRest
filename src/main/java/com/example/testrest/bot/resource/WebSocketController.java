package com.example.testrest.bot.resource;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.CandlestickEvent;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WebSocketController {

    private String apiKey;
    private String apiSecret;

    public void subcribeCandleStickData(Long chatId) {
        BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance(apiKey, apiSecret).newWebSocketClient();
        client.onCandlestickEvent("BTCUSDT", CandlestickInterval.ONE_MINUTE, response -> {
            new SendMessage(chatId, response.toString());
        });
    }
}
