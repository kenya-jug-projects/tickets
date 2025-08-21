package com.kenyajug.core;
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
import com.kenyajug.tickets.TicketRepository;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DatabaseManager {
    public static void createDatabase() throws IOException, SQLException {
        var databaseUrl = fetchDatabase();
        try(Connection connection = DriverManager.
                getConnection(databaseUrl,"","")){
            var createSQL = """
                CREATE TABLE IF NOT EXISTS Tickets(
                    id BIGINTEGER PRIMARY KEY,
                    title TEXT NOT NULL,
                    description TEXT NOT NULL,
                    domain TEXT --nullable, can be empty
                )
                """;
            var statement = connection.prepareStatement(createSQL);
            statement.execute();
        }
    }
    public static String fetchDatabase() throws IOException {
        try(InputStream inputStream = TicketRepository.class
                .getClassLoader()
                .getResourceAsStream("env.properties")) {
            if (inputStream == null) throw new IOException("Failed to open env config file");
            var properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty("db.url");
        }
    }
    public static void clearDatabase() throws IOException, SQLException {
        var databaseUrl = fetchDatabase();
        try(Connection connection = DriverManager.
                getConnection(databaseUrl,"","")){
            var createSQL = """
                DELETE FROM Tickets
                """;
            var statement = connection.prepareStatement(createSQL);
            statement.execute();
        }
    }
}
