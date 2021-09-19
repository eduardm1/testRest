package com.example.testrest.bot.resource;

import com.example.testrest.coin.model.CoinEntity;
import com.example.testrest.coin.model.dto.CoinHistoryDTO;
import com.example.testrest.coin.model.dto.CoinPriceDTO;
import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.TelegramRequest;
import com.github.kshashov.telegram.api.bind.annotation.BotController;
import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendAnimation;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@BotController
public class Bot implements BinanceApiConfiguration {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bot.token}")
    private String token;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.secret}")
    private String apiSecret;

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getApiSecret() {
        return apiSecret;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }


    @BotRequest(
      value = "/price {symbol:[\\S]+}",
      type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
  public BaseRequest symbol(@BotPathVariable("symbol") String symbol, Chat chat) {
      try {
          CoinPriceDTO coinDto = restTemplate.getForObject("https://api.binance.com/api/v3/ticker/price?symbol=" + symbol, CoinPriceDTO.class);
          return new SendMessage(chat.id(), "Pair: " + coinDto.getSymbol() + "\n" + "Price: " + coinDto.getPrice());

      } catch (Exception e) {
          return new SendMessage(chat.id(), e.toString());
      }
    }

    @BotRequest(
            value = "/history {symbol:[\\S]+}",
            type = {MessageType.CALLBACK_QUERY, MessageType.MESSAGE})
    public BaseRequest history(@BotPathVariable("symbol") String symbol, Chat chat) {
        try {
            CoinHistoryDTO coinHistoryDTODto = restTemplate.getForObject("https://api.binance.com/api/v3/ticker/24hr?symbol=" + symbol, CoinHistoryDTO.class);
            return new SendMessage(chat.id(), "Pair: " + coinHistoryDTODto.getSymbol() + "\n" + "Price change: " + coinHistoryDTODto.getPriceChange() + "\n" +
                    "Price change percentage: " + coinHistoryDTODto.getPriceChangePercent() + "%");

        } catch (Exception e) {
            return new SendMessage(chat.id(), e.toString());
        }
    }

    @MessageRequest("/candle {pair:[\\S]+}")
    public String helloWithName(@BotPathVariable("pair") String pair,Chat chat, TelegramRequest request) {
        WebSocketController socket = new WebSocketController(this.apiKey, this.apiSecret);
        socket.subcribeCandleStickData(chat.id());

            return "Hello, ";
    }

    @MessageRequest("/helloCallback")
    public String helloWithCustomCallback(TelegramRequest request, User user) {
        request.setCallback(new Callback() {
            @Override
            public void onResponse(BaseRequest request, BaseResponse response) {
                // TODO
            }

            @Override
            public void onFailure(BaseRequest request, IOException e) {
                // TODO
            }
        });
        return "Hello, " + request+ "!";
    }



}
