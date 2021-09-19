package com.example.testrest.bot.resource;

import com.github.kshashov.telegram.api.TelegramMvcController;

public interface BinanceApiConfiguration extends TelegramMvcController {

    String getApiKey();
    String getApiSecret();
}
