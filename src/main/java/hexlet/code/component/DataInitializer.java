package hexlet.code.component;

import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private CustomUserDetailsService userService;
    @Autowired
    private TaskStatusRepository statusRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var email = "hexlet@example.com";
        var userData = new User();
        userData.setEmail(email);
        userData.setPasswordDigest("qwerty");
        userService.createUser(userData);

        var ts1 = new TaskStatus();
        ts1.setName("Draft");
        ts1.setSlug("draft");
        statusRepository.save(ts1);
        var ts2 = new TaskStatus();
        ts2.setName("To review");
        ts2.setSlug("to_review");
        statusRepository.save(ts2);
        var ts3 = new TaskStatus();
        ts3.setName("To be fixed");
        ts3.setSlug("to_be_fixed");
        statusRepository.save(ts3);
        var ts4 = new TaskStatus();
        ts4.setName("To publish");
        ts4.setSlug("to_publish");
        statusRepository.save(ts4);
        var ts5 = new TaskStatus();
        ts5.setName("Published");
        ts5.setSlug("published");
        statusRepository.save(ts5);
    }
}
