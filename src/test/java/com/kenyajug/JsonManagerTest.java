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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kenyajug.core.JsonManager;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class JsonManagerTest {
    @Test
    public void shouldReadTicketsJson() throws JsonProcessingException {
        var sampleJson = """
                [
                  {
                    "id": 1,
                    "title": "Coffee Shop Receipt",
                    "description": "Achieng’ runs a small café in Kisumu. At the end of each sale, she wants a program that asks for the prices of items purchased and then prints the total bill along with tax.",
                    "domain": "Business"
                  },
                  {
                    "id": 2,
                    "title": "Train Ticket Calculator",
                    "description": "Kamau is building a ticket system for the Nairobi–Mombasa SGR. The program should take a passenger’s age and distance of travel, then calculate the fare (children get 50% off, seniors get 30% off).",
                    "domain": "Transport"
                  }
                ]
                """;
        var tickets = JsonManager.jsonTickets(sampleJson);
        assertThat(tickets).isNotNull();
        assertThat(tickets).isNotEmpty();
        assertThat(tickets.size()).isEqualTo(2);
        var firstTicket = tickets.getFirst();
        assertThat(firstTicket).isNotNull();
        assertThat(firstTicket.id()).isEqualTo(1);
        assertThat(firstTicket.title()).isEqualTo("Coffee Shop Receipt");
        assertThat(firstTicket.domain()).isEqualTo("Business");
    }
}
