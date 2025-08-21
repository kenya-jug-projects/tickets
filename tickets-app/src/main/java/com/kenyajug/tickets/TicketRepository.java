package com.kenyajug.tickets;
/*
 * Apache 2.0 License
 *
 * Copyright (c) 2025 Kenya JUG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import com.kenyajug.core.DatabaseManager;
import com.kenyajug.core.JsonManager;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
public class TicketRepository {
    public boolean save(Ticket ticket) throws IOException,SQLException {
        var databaseUrl = DatabaseManager.fetchDatabase();
        try(Connection connection = DriverManager.getConnection(databaseUrl,"","")) {
            var statement = connection.prepareStatement("""
                    INSERT INTO Tickets VALUES(?,?,?,?)
                    """);
            statement.setLong(1,ticket.id());
            statement.setString(2,ticket.title());
            statement.setString(3,ticket.description());
            statement.setString(4,ticket.domain());
            statement.execute();
            return true;
        }
    }
    public boolean save(List<Ticket> tickets) throws IOException,SQLException {
        var databaseUrl = DatabaseManager.fetchDatabase();
        try(Connection connection = DriverManager.getConnection(databaseUrl,"","")) {
            var statement = connection.prepareStatement("""
                    INSERT INTO Tickets VALUES(?,?,?,?)
                    """);
            for (Ticket ticket : tickets) {
                statement.setLong(1,ticket.id());
                statement.setString(2,ticket.title());
                statement.setString(3,ticket.description());
                statement.setString(4,ticket.domain());
                statement.execute();
            }
            return true;
        }
    }
    public Ticket randomTicket() throws IOException,SQLException{
        var databaseUrl = DatabaseManager.fetchDatabase();
        try(Connection connection = DriverManager.getConnection(databaseUrl,"","")) {
            var statement = connection.prepareStatement("""
                    SELECT * FROM Tickets ORDER BY RANDOM() LIMIT 1
                    """);
            var randResult = statement.executeQuery();
            if(randResult.next()) {
                var id = randResult.getLong(1);
                var title = randResult.getString(2);
                var description = randResult.getString(3);
                var domain = randResult.getString(4);
                return new Ticket(id,title,description,domain);
            } else
                throw new SQLException("Did not find any random tickets in database");
        }
    }
    public boolean isTicketsEmpty() throws IOException,SQLException {
        var databaseUrl = DatabaseManager.fetchDatabase();
        try(Connection connection = DriverManager.getConnection(databaseUrl,"","")) {
            var statement = connection.prepareStatement("""
                    SELECT COUNT(*) FROM Tickets
                    """);
            var result = statement.executeQuery();
            if (result.next()){
                return result.getLong(1) < 1;
            } else throw new IOException("Failed to check tickets database size; empty result");
        }
    }
    public boolean initWithJson() throws IOException,SQLException{
        if(!isTicketsEmpty()) return false;
        try(InputStream inputStream = DatabaseManager.class
                .getClassLoader()
                .getResourceAsStream("ticket_data.json")){
            if (inputStream == null) throw new IOException("Failed to open data json file");
            var jsonBytes = inputStream.readAllBytes();
            var jsonString = new String(jsonBytes);
            var tickets = JsonManager.jsonTickets(jsonString);
            save(tickets);
            return true;
        }
    }
}
