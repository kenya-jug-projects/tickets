package com.kenyajug;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenyajug.core.DatabaseManager;
import com.kenyajug.tickets.TicketRepository;
import com.kenyajug.tickets.TicketsDashboard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.sql.SQLException;
@Slf4j
public class App extends Application {
    public static void main(String[] args) throws SQLException, IOException {
        System.out.println("initializing database...");
        DatabaseManager.createDatabase();
        var ticketsRepository = new TicketRepository();
        ticketsRepository.initWithJson();
        System.out.println("finished initializing database ✅");
        var ticket = ticketsRepository.randomTicket();
        System.out.println("Ticker\n" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ticket));
        System.out.println("Launching App UI...");
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        var ticketsRepository = new TicketRepository();
        var dashboardView = new TicketsDashboard(ticketsRepository);
        var scene = new Scene(dashboardView,1200,850, Color.BLACK);
        primaryStage.setTitle("Kenya JUG Tickets 🇰🇪");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
