using System.Windows.Forms;
using travelAgency.domain;
using travelAgency.repository;

namespace travelAgency.service;

public class ServiceLogIn
{
    private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    private UserDBRepository _userRepo;

    public ServiceLogIn(UserDBRepository userRepo)
    {
        log.InfoFormat("Creating ServiceLogIn");
        _userRepo = userRepo;
    }

    public bool LogInVerify(string username, string password)
    {
        log.InfoFormat("Entering LogInVerify with values: username={0}, password=###", username);
        
        User user = _userRepo.FindByUsername(username);
        if (user == null)
        {
            log.InfoFormat("Username={0} does not exist", username);
            MessageBox.Show(@"Username does not exist");
            return false;
        } 
        if (user.Password == password)
        {
            log.InfoFormat("username={0} and password=### are correct", username);
            return true;
        }
        log.InfoFormat("username={0} found - password incorrect", username);
        MessageBox.Show(@"Wrong password");
        return false;
    }
}