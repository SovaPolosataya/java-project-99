package hexlet.code.controller;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdateDTO;
//import hexlet.code.exception.ResourceNotFoundException;
//import hexlet.code.mapper.UserMapper;
//import hexlet.code.model.User;
//import hexlet.code.repository.UserRepository;
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
@RequestMapping("/api")
public class UsersController {
//    @Autowired
//    private UserRepository userRepository;

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/users")
//    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> index() {
//        List<User> users = userRepository.findAll();
//        List<UserDTO> userDTOS = users.stream()
//                .map(userMapper::map)
//                .toList();
//        return userDTOS;
        List<UserDTO> users = userService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(users.size()))
                .body(users);
    }

    @GetMapping(path = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO show(@PathVariable Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User with id=" + id + " not found!"));
//
//        UserDTO dto = userMapper.map(user);
//
//        return dto;
        return userService.findById(id);
    }

    @PostMapping(path = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody UserCreateDTO dto) {
//        User user = userMapper.map(dto);
//        userRepository.save(user);
//
//        UserDTO userDto = userMapper.map(user);
//
//        return userDto;
        return userService.create(dto);
    }

//    @PreAuthorize("@userRepository.findById(#id).get().getEmail() == authentication.name")
    @PutMapping(path = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(@RequestBody @Valid UserUpdateDTO updateDto, @PathVariable Long id) {
//        User user = userRepository.findById(id)
//                        .orElseThrow(() -> new ResourceNotFoundException("User with id=" + id + " not found!"));
//        userMapper.update(updateDto, user);
//        userRepository.save(user);
//        UserDTO userDto = userMapper.map(user);
//        return userDto;
        return userService.update(updateDto, id);
    }

    @PreAuthorize("@userRepository.findById(#id).get().getEmail() == authentication.name")
    @DeleteMapping(path = "/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
//        userRepository.deleteById(id);
        userService.delete(id);
    }
}
