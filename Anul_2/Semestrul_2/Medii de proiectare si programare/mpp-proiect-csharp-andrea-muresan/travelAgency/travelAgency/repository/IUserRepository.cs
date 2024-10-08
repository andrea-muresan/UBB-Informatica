
using System;
using travelAgency.domain;

namespace travelAgency.repository
{
    public interface IUserRepository : ICrudRepository<int, User>
    {
        User FindByUsername(String username);
    }
}