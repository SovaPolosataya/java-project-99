package hexlet.code.controller.api;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "")
    public ResponseEntity<List<UserDTO>> index() {
        List<UserDTO> users = userService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(users.size()))
                .body(users);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO show(@PathVariable Long id) {

        return userService.findById(id);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreateDTO dto) {

        return userService.create(dto);
    }

    @PreAuthorize("@userRepository.findById(#id).get().getEmail() == authentication.name")
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(@RequestBody @Valid UserUpdateDTO updateDto, @PathVariable Long id) {

        return userService.update(updateDto, id);
    }

    @PreAuthorize("@userRepository.findById(#id).get().getEmail() == authentication.name")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
