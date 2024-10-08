using System;
using System.Collections.Generic;

namespace Model.dto
{
    [Serializable]
    public class UserDto
    {
        public string Username { get; set; }
        public string Password { get; set; }

        public UserDto(string username, string password)
        {
            Username = username;
            Password = password;
        }
        
        public override bool Equals(object obj)
        {
            return obj is UserDto dto &&
                   Username == dto.Username &&
                   Password == dto.Password;
        }

        public override int GetHashCode()
        {
            int hashCode = 568732665;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Username);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Password);
            return hashCode;
        }
        
    }
}