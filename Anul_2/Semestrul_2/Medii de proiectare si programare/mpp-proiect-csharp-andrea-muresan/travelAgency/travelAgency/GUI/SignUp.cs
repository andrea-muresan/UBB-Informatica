using System;
using System.Windows.Forms;
using travelAgency.service;

namespace travelAgency.GUI;

public partial class SignUp : Form
{
    private Service _service;
    
    public SignUp(Service srv)
    {
        this._service = srv;
        InitializeComponent();
    }


    private void SignUpBtn_Click(object sender, EventArgs e)
    {
        string username = usernameField.Text;
        string password = passwordField.Text;
        string checkpassword = checkPasswordField.Text;

        if (password != checkpassword)
        {
            MessageBox.Show("Password and Check password are not the same");
        }
        else
        {
            MessageBox.Show(_service.SignUp(username, password));
        }
    }
}