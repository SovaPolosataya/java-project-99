package hexlet.code.service;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();

        List<UserDTO> userDTOS = users.stream()
                .map(userMapper::map)
                .toList();
        return userDTOS;
    }

    public UserDTO create(UserCreateDTO createDTO) {
        User user = userMapper.map(createDTO);
        userRepository.save(user);

        var userDTO = userMapper.map(user);
        return userDTO;
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id=" + id + " not found!"));

        UserDTO dto = userMapper.map(user);
        return dto;
    }

    public UserDTO update(UserUpdateDTO userData, Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id=" + id + " not found!"));
        userMapper.update(userData, user);
        userRepository.save(user);

        var userDTO = userMapper.map(user);
        return userDTO;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
