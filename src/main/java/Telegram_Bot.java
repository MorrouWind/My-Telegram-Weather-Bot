import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Telegram_Bot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Telegram_Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message.hasText()) {
            switch (message.getText()) {
                case "/Допомога":
                    sendMsg(message, "Чим я можу вам допомогти?");
                    break;
                case "/Налаштування":
                    sendMsg(message, "Що вам потрібно налаштувати?");
                    break;
                case "/Погода":
                    sendMsg(message, "Введіть назву міста будь-якою мовою.");
                    break;
                default:
                    try {
                        sendMsg(message, Weather.getWeather(message.getText(), model) );
                    } catch (IOException e) {
                        sendMsg(message, "Я вас не розумію." + "\n" + "Варіанти того з чим я можу вам допомогти можете знайти знизу.");
                    }
            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton("/Допомога"));
        keyboardRow1.add(new KeyboardButton("/Налаштування"));
        keyboardRow1.add(new KeyboardButton("/Погода"));

        keyboardRowList.add(keyboardRow1);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    @Override
    public String getBotUsername() {
        return "FirstUkrop_bot";
    }

    @Override
    public String getBotToken() {
        return "5720892416:AAGURzV2-l8uI9KqQ1Tka27osVt8vPmhUmc";
    }
}
