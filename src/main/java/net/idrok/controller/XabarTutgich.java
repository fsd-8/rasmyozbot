package net.idrok.controller;


import net.idrok.config.Constant;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

public class XabarTutgich extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {

                try {
                    String text = update.getMessage().getText();
                    SendMessage message;
                    if (text.equals(ButtonConstants.RU)) {
                        message = new SendMessage(update.getMessage().getChatId().toString(), "Siz rus tilini tanladingiz");
                        message.setReplyMarkup(null);

                    } else if (text.equals(ButtonConstants.UZ)) {
                        message = new SendMessage(update.getMessage().getChatId().toString(), "Siz o'zbek tilini tanladingiz");
                        message.setReplyMarkup(null);
                    } else if (text.equals("button")) {
                        message = new SendMessage(update.getMessage().getChatId().toString(), "Xabaringiz: " + text);
                        message.setReplyMarkup(rasmTanlash());
                    } else {
                        message = new SendMessage(update.getMessage().getChatId().toString(), "Xabaringiz: " + text);
                    }
                    execute(message);


                    // file yuborish
//                   SendDocument photo = new SendDocument();
//                   photo.setDocument(new InputFile(new File("logo.png")));
//
//                    photo.setChatId(update.getMessage().getChatId().toString());
//
//                   execute(photo);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (update.getMessage().hasDocument()) {

//                 // rasm yuklash
//                PhotoSize photo = photos.get(photos.size() - 1); // eng tiniq rasmni yuklash
//                GetFile getFileRequest = new GetFile();
//                getFileRequest.setFileId(photo.getFileId());;


                // fayl yuklash
                Document document = update.getMessage().getDocument();
                GetFile getFileRequest = new GetFile();
                getFileRequest.setFileId(document.getFileId());

                try {
                    org.telegram.telegrambots.meta.api.objects.File tFile = execute(getFileRequest);
                    File ff = new File("files");
                    ff.mkdirs();
                    ff = new File("files/" + update.getMessage().getChatId() + "_" + update.getMessage().getMessageId() + "_" + document.getFileName());
                    ff.createNewFile();
                    File file = downloadFile(tFile, ff);
                    System.out.println(file.getName());


                } catch (TelegramApiException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            AnswerCallbackQuery acq = new AnswerCallbackQuery();
            acq.setCallbackQueryId(callbackQuery.getId());
            acq.setShowAlert(true);
            acq.setText("Siz " + callbackQuery.getData() + "ni bosdingiz");

            try {
                execute(acq);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            EditMessageText editMessageText = new EditMessageText();
            editMessageText       .setChatId(callbackQuery.getMessage().getChatId());
            editMessageText       .setMessageId(callbackQuery.getMessage().getMessageId());
            editMessageText       .setText("siz "+ data + " ni bosdingiz. " + LocalDateTime.now().toString());
            InlineKeyboardMarkup rkm = rasmTanlash();
            for(List<InlineKeyboardButton> l: rkm.getKeyboard()) {
                for (InlineKeyboardButton k : l) {
                    if (k.getCallbackData().equals(data)) {

                        k.setText("✅ " + k.getText());
                    }
                }
            }
            editMessageText.setReplyMarkup(rkm);
            try {
                execute(editMessageText);
            } catch (TelegramApiException e) {
e.printStackTrace();            }


            SendMessage sm = new SendMessage(callbackQuery.getMessage().getChatId().toString(), "Sizning bosgan tugmangiz: " + data);
            sm.setReplyToMessageId(callbackQuery.getMessage().getMessageId());
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private InlineKeyboardMarkup rasmTanlash() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> qator = new ArrayList<>();
        int k = 1;
        for (int i = 0; i < 3; i++) {
            List<InlineKeyboardButton> buttonsList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                InlineKeyboardButton button = new InlineKeyboardButton(String.valueOf(k));
                button.setCallbackData(k + "");
                buttonsList.add(button);
                k++;
            }
          qator.add(buttonsList);
        }
        inlineKeyboardMarkup.setKeyboard(qator);
        return inlineKeyboardMarkup;
    }

    private ReplyKeyboard tilTanlash() {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        KeyboardRow birinchiQator = new KeyboardRow();
        birinchiQator.add(ButtonConstants.UZ);
        birinchiQator.add(ButtonConstants.RU);
        replyKeyboard.setOneTimeKeyboard(true);
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setKeyboard(List.of(birinchiQator));
        return replyKeyboard;
    }


    @Override
    public String getBotUsername() {
        return Constant.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return Constant.BOT_TOKEN;
    }
}

class ButtonConstants {
    public static final String UZ = "O'zbek";
    public static final String RU = "RUSKIY";
}