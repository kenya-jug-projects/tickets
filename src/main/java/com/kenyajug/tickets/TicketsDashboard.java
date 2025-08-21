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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.IOException;
import java.sql.SQLException;
public class TicketsDashboard extends StackPane {
    private final TicketRepository ticketRepository;
    private final Label ticketTitleLabel;
    private final Label ticketDescriptionLabel;
    private final Label ticketDomainLabel;
    private final Label errorLabel;
    public TicketsDashboard(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
        Label appTitleLabel = new Label("Random Programming Task Generator");
        Label generateTicketLabel = new Label("Tap generate to produce new programming task");
        Button generateButton = new Button("Generate");
        ticketTitleLabel = new Label();
        ticketDomainLabel = new Label();
        ticketDescriptionLabel = new Label();
        ticketTitleLabel.setFont(Font.font("",FontWeight.BOLD,21));
        ticketTitleLabel.setTextFill(Color.WHITE);
        ticketDescriptionLabel.setFont(Font.font("",FontWeight.MEDIUM,21));
        ticketDescriptionLabel.setTextFill(Color.WHITE);
        appTitleLabel.setFont(new Font("",32));
        appTitleLabel.setTextFill(Color.WHITE);
        generateTicketLabel.setFont(new Font("",22));
        generateTicketLabel.setTextFill(Color.GRAY);
        generateButton.setFont(new Font("",22));
        ticketTitleLabel.setWrapText(true);
        ticketDescriptionLabel.setWrapText(true);
        ticketDescriptionLabel.setAlignment(Pos.CENTER);
        generateTicketLabel.setWrapText(true);
        this.errorLabel = new Label();
        generateButton.setOnAction(event -> {
            try {
                var ticket = this.ticketRepository.randomTicket();
                this.ticketTitleLabel.setText(ticket.title());
                this.ticketDescriptionLabel.setText(ticket.description());
                this.ticketDomainLabel.setText(ticket.domain());
            } catch (IOException | SQLException e) {
                errorLabel.setText("Failed to generate ticket, cause" + e.getLocalizedMessage());
            }
        });
        var margin = 12;
        var vBox = new VBox(
                margin,
                appTitleLabel,
                generateTicketLabel
        );
        var resultVBox = new VBox(
                margin,
                generateButton,
                ticketTitleLabel,
                ticketDescriptionLabel
        );
        var borderStroke = new BorderStroke(Color.GRAY, BorderStrokeStyle.DASHED, new CornerRadii(12),new BorderWidths(6));
        vBox.setAlignment(Pos.TOP_LEFT);
        resultVBox.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(22));
        this.getChildren().add(vBox);
        this.getChildren().add(resultVBox);
        setBackground(Background.fill(Color.BLACK));
    }

}
