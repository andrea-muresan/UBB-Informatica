using System;
using System.Collections.Generic;
using System.Windows.Forms;
using travelAgency.repository;
using travelAgency.service;

namespace travelAgency.GUI
{
    public partial class Form1 : Form
    {
        private Service _service;
        public Form1(Service service)
        {
            this._service = service;
            InitializeComponent();
        }
        
        private void logIn_Click(object sender, EventArgs e)
        {
            string username = usernameInput.Text;
            string password = passwordInput.Text;
            
            if (_service.LogInVerify(username, password))
            { 
                (new App(this._service)).Show();
            }
        }

        private void label3_Click(object sender, EventArgs e)
        {
            (new SignUp(this._service)).Show();
        }
    }
}