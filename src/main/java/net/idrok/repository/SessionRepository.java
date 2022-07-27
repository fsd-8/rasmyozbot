package net.idrok.repository;

import net.idrok.db.DataSource;
import net.idrok.model.Section;
import net.idrok.model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SessionRepository {

    public static Session findById(Long id) throws SQLException {
        String sql = "select * from session where id = ?;";
        PreparedStatement ps = DataSource.ps(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Session(rs.getLong("id"), Section.getByOrdinal(rs.getInt("status")), rs.getTimestamp("last_update").toLocalDateTime());
        }
        return null;
    }

    public static List<Session> findAll() throws SQLException {
        String sql = "select * from session;";
        PreparedStatement ps = DataSource.ps(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Session> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Session(rs.getLong("id"), Section.getByOrdinal(rs.getInt("status")), rs.getTimestamp("last_update").toLocalDateTime()));
        }
        rs.close();
        ps.close();
        return list;
    }


    public static boolean create(Session session) throws SQLException {
        String sql = "insert into session(id, status, last_update) values (?, ?, ?);";
        PreparedStatement ps = DataSource.ps(sql);
        ps.setLong(1, session.getUserId());
        ps.setInt(2, session.getSection().ordinal());
        ps.setTimestamp(3, Timestamp.valueOf(session.getLastUpdate()));
        return ps.executeUpdate() > 0;
    }
    public static boolean update(Session session) throws SQLException {
        String sql = "update session set status = ?, last_update = ? where id = ?";
        PreparedStatement ps = DataSource.ps(sql);
        ps.setInt(1, session.getSection().ordinal());
        ps.setTimestamp(2, Timestamp.valueOf(session.getLastUpdate()));
        ps.setLong(3, session.getUserId());
        return ps.executeUpdate() > 0;
    }

    public static boolean delete(Session session) throws SQLException {
        String sql = "delete from bino where id = ?;";
        PreparedStatement ps = DataSource.ps(sql);
        ps.setLong(1, session.getUserId());
        return  ps.executeUpdate() > 0;
    }


}
