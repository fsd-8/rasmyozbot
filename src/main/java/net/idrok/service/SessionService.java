package net.idrok.service;

import net.idrok.model.Session;
import net.idrok.repository.SessionRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class SessionService {

    public static List<Session> getAll() throws SQLException {
        //TODO qayta ishlash kerak bo'lsa logika yozing
        return SessionRepository.findAll();
    }

    public static boolean create(Session session) throws SQLException {
        if(session.getUserId() == null){
            System.err.println("session id null bo'lmasligi kerak");
            return false;
        }
        if(session.getSection() == null ){
            System.err.println("session nomi bo'lishi shart");
            return false;
        }
        session.setLastUpdate(LocalDateTime.now());
        return SessionRepository.create(session);
    }
    public static boolean update(Session session) throws SQLException {
        if(session.getUserId() == null){
            System.err.println("Session id bo'lmasligi kerak");
            return false;
        }
        if(session.getSection() == null ){
            System.err.println("Session nomi bo'lishi shart");
            return false;
        }
        session.setLastUpdate(LocalDateTime.now());
        return SessionRepository.update(session);
    }
    public static boolean delete(Session Session) throws SQLException {
        if(Session.getUserId() == null){
            System.err.println("Session id bo'lishi kerak");
            return false;
        }
        return SessionRepository.delete(Session);
    }

    public static Session getById(Long id) throws SQLException {
        if(id == null){
            System.err.println("id null bo'la olmaydi");
            return null;
        }
        return SessionRepository.findById(id);
    }
}
