namespace Model.dto
{
    public class DtoUtils
    {
        public static User GetFromDTO(UserDto userDto)
        {
            string username = userDto.Username;
            string password = userDto.Password;
            return new User(username, password);
        }

        public static UserDto getDTO(User user)
        {
            string username = user.Username;
            string password = user.Password;
            return new UserDto(username, password);
        }
            
    }
}