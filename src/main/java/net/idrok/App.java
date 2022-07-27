package net.idrok;

import net.idrok.controller.RegisterHandler;
import net.idrok.controller.XabarTutgich;
import net.idrok.db.DBInit;
import net.idrok.db.DataSource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws TelegramApiException, SQLException {
        if(!DataSource.connect()){
            System.out.println("Database is not connected");
            System.exit(1);
        }
        System.out.println("Database is connected!");

        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new RegisterHandler());
        api.registerBot(new XabarTutgich());
    }
}
// 1. sessiani tekshirish
// 2. agar sessia mavjud bo'lmasa yangi user deb qarab uni ro'yxatga olish
// 3. agar sessia mavjud bo'lsa oxirgi amalni chiqarish