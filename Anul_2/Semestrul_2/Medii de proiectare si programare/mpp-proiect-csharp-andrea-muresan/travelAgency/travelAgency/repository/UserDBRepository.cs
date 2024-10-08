using System;
using System.Collections.Generic;
using System.Data;
using travelAgency.domain;
using travelAgency.utils;

namespace travelAgency.repository
{
    public class UserDBRepository : IUserRepository
    {
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        IDictionary<String, string> props;
        private Encrypt _encrypt = new Encrypt();
        
        public UserDBRepository(IDictionary<String, string> props)
        {
            log.Info("Creating UserDBRepository");
            this.props = props;
        }

        public IEnumerable<User> FindAll()
        {
            throw new NotImplementedException();
        }

        public User? FindOne(int id)
        {
            throw new NotImplementedException();
        }

        public void Save(User entity)
        {
            log.InfoFormat("Saving user values: {0}, {1}", entity.Username, "###");
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText =
                    "INSERT INTO users(username, password) " +
                    "VALUES (@username, @password)";
                var paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = entity.Username;
                comm.Parameters.Add(paramUsername);
                
                
                var paramPassword = comm.CreateParameter();
                paramPassword.ParameterName = "@password";
                paramPassword.Value = _encrypt.EncodePasswordToBase64(entity.Password);
                comm.Parameters.Add(paramPassword);
                

                int noVal = comm.ExecuteNonQuery();
                log.InfoFormat("{0} users saved", noVal);
            }
        }

        public void Delete(int id)
        {
            throw new NotImplementedException();
        }

        public void Update(User entity)
        {
            throw new NotImplementedException();
        }

        public User FindByUsername(string username)
        {
            log.InfoFormat("Entering FindByUsername with value {0}", username);
            IDbConnection con = DBUtils.getConnection(props);
            
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, username, password from users where username=@username";
                IDbDataParameter paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = username;
                comm.Parameters.Add(paramUsername);
            
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        String usernameV = dataR.GetString(1);
                        String passwordV = dataR.GetString(2);
                        User user = new User(usernameV, passwordV);
                        user.Id = idV;
                        log.InfoFormat("Exiting findByUsername with value {0}", user);
                        return user;
                    }
                }
            }
            
            log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }
    }
}