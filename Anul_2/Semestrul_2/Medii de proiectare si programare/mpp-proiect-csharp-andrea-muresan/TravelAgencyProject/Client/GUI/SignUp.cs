using System;
using System.Windows.Forms;
using Services;


namespace Client.GUI
{

    public partial class SignUp : Form
    {
        private IServices _service;

        public SignUp(IServices srv)
        {
            this._service = srv;
            InitializeComponent();
        }


        private void SignUpBtn_Click(object sender, EventArgs e)
        {
        //     string username = usernameField.Text;
        //     string password = passwordField.Text;
        //     string checkpassword = checkPasswordField.Text;
        //
        //     if (password != checkpassword)
        //     {
        //         MessageBox.Show("Password and Check password are not the same");
        //     }
        //     else
        //     {
        //         MessageBox.Show(_service.SignUp(username, password));
        //     }
        }
    }

}