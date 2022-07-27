package net.idrok.controller;

import net.idrok.config.Constant;
import net.idrok.model.Section;
import net.idrok.model.Session;
import net.idrok.service.SessionService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.List;

public class RegisterHandler extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return Constant.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return Constant.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                User telegramuser = message.getFrom();
                Session session = SessionService.getById(telegramuser.getId());
                if (session == null) {
                    // yangi userni boshlash
                    session = new Session();
                    session.setUserId(telegramuser.getId());
                    session.setSection(Section.REGISTER_FIRST_NAME);
                    SessionService.create(session);

                    execute(new SendMessage(message.getChatId().toString(), "Ismingizni kiriting: "));
                    return;

                }
                if (message.isCommand() && message.getText().equals("/start")) {

                    switch (session.getSection()) {
                        case REGISTER_FIRST_NAME:
                            execute(new SendMessage(message.getChatId().toString(), "Ismingizni kiriting: "));
                            break;
                        case REGISTER_LAST_NAME:
                            execute(new SendMessage(message.getChatId().toString(), "Familiyangizni kiriting: "));
                            break;
                        case REGISTER_PHONE_NUMBER: {
                            SendMessage sm = new SendMessage(message.getChatId().toString(), "Telefon raqamingiz kiriting: ");
                            KeyboardButton kb = new KeyboardButton("Telefon raqam yuborish");
                            kb.setRequestContact(true);
                            sm.setReplyMarkup(new ReplyKeyboardMarkup(List.of(new KeyboardRow(List.of(kb)))));
                            execute(sm);
                        }
                        break;
                        default: {
                            session.setSection(Section.HOME);
                            SessionService.update(session);
                            boshOynaXabar(message.getChatId().toString());
                        }
                    }


                }
                else if (message.hasText()) {
                    switch (session.getSection()) {
                        case HOME: {
                            String text = message.getText();
                            switch (text) {
                                case Menyu.BAYRAM_8_MART:
                                    rasmTanlash8Mart(text);
                                    break;
                                case Menyu.BAYRAM_HAYIT:
                                    rasmTanlashHayit(text);
                                    break;
                                case Menyu.BAYRAM_YANGI_YIL:
                                    rasmTanlashYangiYil(text);
                                    break;
                                default: {
                                    boshOynaXabar(message.getChatId().toString());
                                }
                            }
                        }
                        break;
                        case REGISTER_FIRST_NAME: {
                            session.setFirstName(message.getText());
                            session.setSection(Section.REGISTER_LAST_NAME);
                            SessionService.update(session);
                            execute(new SendMessage(message.getChatId().toString(), "Familiyangizni kiriting"));
                        } break;
                        case REGISTER_LAST_NAME: {
                           session.setLastName(message.getText());
                            session.setSection(Section.REGISTER_PHONE_NUMBER);
                            SessionService.update(session);
                            telefonRaqamSurash(message.getChatId().toString());
                            } break;
                        case REGISTER_PHONE_NUMBER: {
                            session.setPhone(message.getText());
                            session.setSection(Section.HOME);
                            SessionService.update(session);
                            boshOynaXabar(message.getChatId().toString());
                        }

                    }

                } else if(message.hasContact() && session.getSection()==Section.REGISTER_PHONE_NUMBER){
                    session.setPhone(message.getText());
                    session.setSection(Section.HOME);
                    SessionService.update(session);
                    boshOynaXabar(message.getChatId().toString());
                }
            }
        } catch (SQLException | TelegramApiException e) {
            e.printStackTrace();
        }

    }


    private void rasmTanlashYangiYil(String text) {
    }

    private void rasmTanlashHayit(String text) {
    }

    private void rasmTanlash8Mart(String text) {



    }

    private void boshOynaXabar(String chatId) {
        SendMessage sm = new SendMessage(chatId, "Pastdagi menyulardan birini tanlang ");

        sm.setReplyMarkup(
                new ReplyKeyboardMarkup(List.of(
                        new KeyboardRow(List.of(
                                new KeyboardButton(Menyu.BAYRAM_8_MART),
                                new KeyboardButton(Menyu.BAYRAM_HAYIT),
                                new KeyboardButton(Menyu.BAYRAM_YANGI_YIL))))));
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    private void telefonRaqamSurash(String chatId) {
        SendMessage sm = new SendMessage(chatId, "Iltimos telefon raqamingizni yuboring");
        KeyboardButton button = new KeyboardButton("Telefon raqam");
        button.setRequestContact(true);
        KeyboardRow row = new KeyboardRow();
        row.add(button);
        sm.setReplyMarkup(new ReplyKeyboardMarkup(List.of(row)));
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

class Menyu {
    public final static String BAYRAM_8_MART = "8 - mart uchun tabriklar";
    public final static String BAYRAM_HAYIT = "Hayit bayrami uchun tabirklar";
    public final static String BAYRAM_YANGI_YIL = "Yangi yil bayrami uchun tabirklar";
}
