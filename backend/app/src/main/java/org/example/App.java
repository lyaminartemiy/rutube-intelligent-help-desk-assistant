/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example;

import org.example.model.entity.Employee;
import org.example.service.AuthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
        ctx.getBean(AuthService.class).sendSignUpDataToEmail(
                "Петров Виктор Администраторович",
                Employee.Role.ADMIN,
                "olenkaevstratova@gmail.com"
        );
        ctx.getBean(AuthService.class).sendSignUpDataToEmail(
                "Петров Виктор ПОЛЬЗАКТП",
                Employee.Role.TECH_SUPPORT_EMPLOYEE,
                "olenkaevstratova@gmail.com"
        );
//        ctx.getBean(AuthService.class).sendSignUpDataToEmail(
//                "Петров Виктор Администраторович",
//                Employee.Role.TECH_SUPPORT_EMPLOYEE,
//                "olenkaevstratova@gmail.com"
//        );
//        ctx.getBean(AuthService.class).sendSignUpDataToEmail(
//                "Петров Виктор Администраторович",
//                Employee.Role.TECH_SUPPORT_EMPLOYEE,
//                "olenkaevstratova@gmail.com"
//        );

//        var sessionService = ctx.getBean(SessionService.class);
//        sessionService.createNewSession("123");
//        sessionService.createNewSession("456");
//        sessionService.createNewSession("789");
//        sessionService.createNewSession("0123");
//        sessionService.sendSessionToTechSupport("0123");
//        sessionService.sendSessionToTechSupport("123");
//        sessionService.sendSessionToTechSupport("456");
//        sessionService.sendSessionToTechSupport("789");
//
//        var updateMessageService = ctx.getBean(UpdateMessageService.class);
//        updateMessageService.createOrUpdateMessageInDatabase(
//                "123",
//                "fake-message-id1",
//                "Спасите помогите я такой-то такой-то",
//                ZonedDateTime.now(ZoneOffset.UTC),
//                null
//        );
//        updateMessageService.createOrUpdateMessageInDatabase(
//                "456",
//                "fake-message-id2",
//                "Спасите помогите я такой-то такой-то",
//                ZonedDateTime.now(ZoneOffset.UTC),
//                null
//        );
//        updateMessageService.createOrUpdateMessageInDatabase(
//                "789",
//                "fake-message-id3",
//                "Спасите помогите я такой-то такой-то",
//                ZonedDateTime.now(ZoneOffset.UTC),
//                null
//        );
//        var techSupRqService = ctx.getBean(TechSupportRequestService.class);
//        techSupRqService.assignEmployeeToRequest(ctx.getBean(TechSupportRequestRepository.class).findAll().getFirst().getId(), ctx.getBean(EmployeeRepository.class).findAll().stream().filter(e -> e.getRole().equals(Employee.Role.TECH_SUPPORT_EMPLOYEE)).toList().getFirst());
//        techSupRqService.assignEmployeeToRequest(ctx.getBean(TechSupportRequestRepository.class).findAll().getFirst().getId(), ctx.getBean(EmployeeRepository.class).findAll().stream().filter(e -> e.getRole().equals(Employee.Role.TECH_SUPPORT_EMPLOYEE)).toList().getLast());
//        techSupRqService.assignEmployeeToRequest(ctx.getBean(TechSupportRequestRepository.class).findAll().getLast().getId(), ctx.getBean(EmployeeRepository.class).findAll().stream().filter(e -> e.getRole().equals(Employee.Role.TECH_SUPPORT_EMPLOYEE)).toList().getFirst());
//        techSupRqService.closeRequestById(ctx.getBean(TechSupportRequestRepository.class).findAll().stream().filter(rq -> rq.getSession().getChatId().equals("0123")).toList().getLast().getId());


//        Thread.sleep(20000);
//
//        ctx.getBean(SessionService.class).createNewSession("123");
//        ctx.getBean(SessionService.class).sendSessionToTechSupport("123");
//
//        Thread.sleep(5000);
//
//        ctx.getBean(SessionService.class).createNewSession("456");
//        ctx.getBean(SessionService.class).sendSessionToTechSupport("456");
////        ctx.getBean(EmailService.class).sendEmail("semyon.chernomurov@gmail.com", "Тема", "Текст");
    }
}
