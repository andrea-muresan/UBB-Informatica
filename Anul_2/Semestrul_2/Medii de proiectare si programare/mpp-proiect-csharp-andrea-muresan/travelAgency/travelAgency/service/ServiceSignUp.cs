using travelAgency.repository;

namespace travelAgency.service;

public class ServiceSignUp
{
    private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    private UserDBRepository _userRepo;

    public ServiceSignUp(UserDBRepository userRepo)
    {
        log.InfoFormat("Creating ServiceSignUp");
        _userRepo = userRepo;
    }
    
    
}