package hexlet.code.component;

import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
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
    @Autowired
    private LabelRepository labelRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String email = "hexlet@example.com";
        User userData = new User();
        userData.setEmail(email);
        userData.setPasswordDigest("qwerty");
        userService.createUser(userData);

        statusRepository.save(new TaskStatus("Draft", "draft"));
        statusRepository.save(new TaskStatus("To review", "to_review"));
        statusRepository.save(new TaskStatus("To be fixed", "to_be_fixed"));
        statusRepository.save(new TaskStatus("To publish", "to_publish"));
        statusRepository.save(new TaskStatus("Published", "published"));

        labelRepository.save(new Label("feature"));
        labelRepository.save(new Label("bug"));
    }
}
