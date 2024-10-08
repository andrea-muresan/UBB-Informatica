package app.model.dto;

import app.model.User;

public class DtoUtils {
    public static User getFromDTO(UserDto userDto){
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        return new User(username, password);
    }
    public static UserDto getDTO(User user){
        String username = user.getUsername();
        String pass = user.getPassword();
        return new UserDto(username, pass);
    }
}