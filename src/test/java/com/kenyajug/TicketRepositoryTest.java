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
import com.kenyajug.core.DatabaseManager;
import com.kenyajug.tickets.Ticket;
import com.kenyajug.tickets.TicketRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ExtendWith(MockitoExtension.class)
public class TicketRepositoryTest {
    @InjectMocks
    private TicketRepository ticketRepository;
    @BeforeEach
    public void setUp() throws SQLException, IOException {
        DatabaseManager.createDatabase();
        DatabaseManager.clearDatabase();
    }
    @Test
    public void shouldSaveTicketTest() throws IOException, SQLException {
        var ticket = new Ticket(1L,"Ticket Title","A description","Networking");
        var isSaved = ticketRepository.save(ticket);
        assertThat(isSaved).isTrue();
    }
    @Test
    public void shouldFetchDatabaseTest() throws IOException {
        var databaseName = DatabaseManager.fetchDatabase();
        assertThat(databaseName).isNotNull();
        assertThat(databaseName).isEqualTo("jdbc:sqlite:test_tickets_db");
    }
    @RepeatedTest(5)
    public void shouldFetchRandomTicker() throws SQLException,IOException {
        var tickets = List.of(
                new Ticket(1L, "Login issue", "User cannot log in with correct credentials", "Authentication"),
                new Ticket(2L, "Password reset", "Password reset email not being sent", "Authentication"),
                new Ticket(3L, "UI glitch", "Dashboard widgets overlap on smaller screens", "Frontend"),
                new Ticket(4L, "API error", "500 error when calling /orders endpoint", "Backend"),
                new Ticket(5L, "Database timeout", "Queries taking too long during peak hours", "Database"),
                new Ticket(6L, "Search bug", "Search returns incomplete results", "Search"),
                new Ticket(7L, "Notification delay", "Push notifications delayed by 10 minutes", "Messaging"),
                new Ticket(8L, "Payment failure", "Credit card payments not processed", "Payments"),
                new Ticket(9L, "Report export", "CSV export feature broken", "Reporting"),
                new Ticket(10L, "Security alert", "Suspicious login not flagged", "Security")
        );
        var isSaved = ticketRepository.save(tickets);
        assertThat(isSaved).isTrue();
        var randomTicket = ticketRepository.randomTicket();
        assertThat(randomTicket).isNotNull();
    }
    @Test
    public void shouldCheckIfTicketsIsEmptyTest() throws IOException,SQLException{
        var tickets = List.of(
                new Ticket(1L, "Login issue", "User cannot log in with correct credentials", "Authentication"),
                new Ticket(2L, "Password reset", "Password reset email not being sent", "Authentication"),
                new Ticket(3L, "UI glitch", "Dashboard widgets overlap on smaller screens", "Frontend"),
                new Ticket(4L, "API error", "500 error when calling /orders endpoint", "Backend"),
                new Ticket(5L, "Database timeout", "Queries taking too long during peak hours", "Database"),
                new Ticket(6L, "Search bug", "Search returns incomplete results", "Search"),
                new Ticket(7L, "Notification delay", "Push notifications delayed by 10 minutes", "Messaging"),
                new Ticket(8L, "Payment failure", "Credit card payments not processed", "Payments"),
                new Ticket(9L, "Report export", "CSV export feature broken", "Reporting"),
                new Ticket(10L, "Security alert", "Suspicious login not flagged", "Security")
        );
        var isSaved = ticketRepository.save(tickets);
        assertThat(isSaved).isTrue();
        var isEmpty = ticketRepository.isTicketsEmpty();
        assertThat(isEmpty).isFalse();
    }
    @Test
    public void shouldCheckIfTicketsIsEmptyCase2Test() throws IOException,SQLException{
        var isEmpty = ticketRepository.isTicketsEmpty();
        assertThat(isEmpty).isTrue();
    }
    @Test
    public void shouldInitTicketsWithJsonTest() throws IOException,SQLException {
        var isInitialized = ticketRepository.initWithJson();
        assertThat(isInitialized).isTrue();
        var randomTicket = ticketRepository.randomTicket();
        assertThat(randomTicket).isNotNull();
        assertThat(randomTicket.title()).isNotEmpty();
        assertThat(randomTicket.description()).isNotEmpty();
        assertThat(randomTicket.domain()).isNotEmpty();
    }
    @AfterEach
    public void cleanUp() throws SQLException, IOException {
        DatabaseManager.clearDatabase();
    }
}
