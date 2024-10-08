using System.ComponentModel;

namespace Client.GUI
{

    partial class SignUp
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }

            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.usernameField = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.passwordField = new System.Windows.Forms.TextBox();
            this.checkPasswordField = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.SignUpBtn = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // usernameField
            // 
            this.usernameField.Location = new System.Drawing.Point(68, 64);
            this.usernameField.Name = "usernameField";
            this.usernameField.Size = new System.Drawing.Size(194, 26);
            this.usernameField.TabIndex = 0;
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(68, 38);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(100, 23);
            this.label1.TabIndex = 1;
            this.label1.Text = "Usename:";
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(68, 128);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(100, 23);
            this.label2.TabIndex = 2;
            this.label2.Text = "Password:";
            // 
            // passwordField
            // 
            this.passwordField.Location = new System.Drawing.Point(68, 154);
            this.passwordField.Name = "passwordField";
            this.passwordField.PasswordChar = '*';
            this.passwordField.Size = new System.Drawing.Size(194, 26);
            this.passwordField.TabIndex = 3;
            // 
            // checkPasswordField
            // 
            this.checkPasswordField.Location = new System.Drawing.Point(68, 252);
            this.checkPasswordField.Name = "checkPasswordField";
            this.checkPasswordField.PasswordChar = '*';
            this.checkPasswordField.Size = new System.Drawing.Size(194, 26);
            this.checkPasswordField.TabIndex = 4;
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(68, 226);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(144, 23);
            this.label3.TabIndex = 5;
            this.label3.Text = "Check password:";
            // 
            // SignUpBtn
            // 
            this.SignUpBtn.Location = new System.Drawing.Point(187, 324);
            this.SignUpBtn.Name = "SignUpBtn";
            this.SignUpBtn.Size = new System.Drawing.Size(75, 33);
            this.SignUpBtn.TabIndex = 6;
            this.SignUpBtn.Text = "Sign Up";
            this.SignUpBtn.UseVisualStyleBackColor = true;
            this.SignUpBtn.Click += new System.EventHandler(this.SignUpBtn_Click);
            // 
            // SignUp
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.SignUpBtn);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.checkPasswordField);
            this.Controls.Add(this.passwordField);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.usernameField);
            this.Name = "SignUp";
            this.Text = "SignUp";
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox passwordField;
        private System.Windows.Forms.TextBox checkPasswordField;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button SignUpBtn;

        private System.Windows.Forms.TextBox usernameField;

        #endregion
    }
}