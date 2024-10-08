
using System;
using Model;
using Persistence;

namespace Persistence
{
    public interface IUserRepository : ICrudRepository<int, User>
    {
        User FindByUsername(String username);
    }
}