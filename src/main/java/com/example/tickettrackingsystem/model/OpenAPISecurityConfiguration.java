package com.example.tickettrackingsystem.model;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info =@Info(
                title = "Ticket Tracking System API",
                version = "${api.version}",
                license = @License(
                        name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                description = "This is a  ticket tracking system. This system allows QA to report a bug and RD can mark a bug as resolved.\n" +
                        "A. Phase I Requirement:\n" +
                        "- There are two types of user: QA and RD.\n" +
                        "- Only QA can create a bug, edit a bug and delete a bug.\n" +
                        "- Only RD can resolve a bug.\n" +
                        "- Summary field and Description filed are must have of a bug when QA is creating a bug.\n" +
                        "\n" +
                        "B. Phase II Requirement:\n" +
                        "- All the users can leave any comments below a ticket.\n" +
                        "- Adding new field Severity and Priority to a ticket.\n" +
                        "- Adding new type of user \"PM\" that can create new ticket type \"Feature Request\". And only RD can mark it as resolved.\n" +
                        "- Adding new ticket type \"Test Case\" that only QA can create and resolve. It's read-only for other type of users.\n" +
                        "- Adding Administrator user that can manage all the stuffs including adding new QA, RD and PM user.\n" +
                        "Some useful links:\n" +
                        "- [The Ticket Tracking System repository](https://github.com/Yen-YuChu/spring-jwt-ticket-tracking-system.git)"
        ),
        servers = @Server(
                url = "${api.server.url}",
                description = "For Ruckus DEMO"
        )
)
public class OpenAPISecurityConfiguration {
}
