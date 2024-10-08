using System;
using Client.GUI;
using Networking;
using Services;
using System.Windows.Forms;

namespace Client
{
    public class StartClient
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            
           
            //IChatServer server=new ChatServerMock();   
            //FOLOSITI FISIERE DE CONFIGURARE PENTRU A OBTINE IP SI PORT
            //exemplu in GTKClient
            IServices server = new ServerProxy("127.0.0.1", 55556);
            ClientCtrl ctrl=new ClientCtrl(server);
            LogIn win=new LogIn(ctrl);
            Application.Run(win);
        }
    }
}