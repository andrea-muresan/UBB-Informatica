using System;
using System.Windows.Forms;
using Server;
using Services;

namespace Client.GUI
{
    public partial class LogIn : Form
    {
        private ClientCtrl ctrl;
        public LogIn(ClientCtrl ctrl)
        {
            this.ctrl = ctrl;
            InitializeComponent();
        }
        
        private void logIn_Click(object sender, EventArgs e)
        {
            string username = usernameInput.Text;
            string password = passwordInput.Text;
            
            // if (_service.LogInVerify(username, password))
            // { 
            //     (new App(this._service)).Show();
            // }
            try
            {
                ctrl.Login(username, password);
                //MessageBox.Show("Login succeded");
                App chatWin=new App(ctrl);
                chatWin.Text = "Chat window for " + username;
                chatWin.Show();
                this.Hide();
            }catch(Exception ex)
            {
                MessageBox.Show(this, "Login Error " + ex.Message/*+ex.StackTrace*/, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
        }
        
    }
}