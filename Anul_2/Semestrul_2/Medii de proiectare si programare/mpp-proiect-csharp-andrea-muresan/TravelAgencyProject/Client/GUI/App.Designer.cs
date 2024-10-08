using System.ComponentModel;
using System.Windows.Forms;

namespace Client.GUI
{
    partial class App
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
            this.flightsView = new System.Windows.Forms.DataGridView();
            this.searchBtn = new System.Windows.Forms.Button();
            this.searchView = new System.Windows.Forms.DataGridView();
            this.destinationTextBox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.clientNameTextBox = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.noSeatsTextBox = new System.Windows.Forms.TextBox();
            this.addresTextBox = new System.Windows.Forms.TextBox();
            this.touristsTextBox = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.buyBtn = new System.Windows.Forms.Button();
            this.logOutBtn = new System.Windows.Forms.Button();
            this.dateTimePicker1 = new System.Windows.Forms.DateTimePicker();
            ((System.ComponentModel.ISupportInitialize)(this.flightsView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.searchView)).BeginInit();
            this.SuspendLayout();
            // 
            // flightsView
            // 
            this.flightsView.ColumnHeadersHeightSizeMode =
                System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.flightsView.Location = new System.Drawing.Point(38, 52);
            this.flightsView.Name = "flightsView";
            this.flightsView.RowTemplate.Height = 28;
            this.flightsView.Size = new System.Drawing.Size(570, 239);
            this.flightsView.TabIndex = 0;
            // 
            // searchBtn
            // 
            this.searchBtn.Location = new System.Drawing.Point(516, 352);
            this.searchBtn.Name = "searchBtn";
            this.searchBtn.Size = new System.Drawing.Size(90, 38);
            this.searchBtn.TabIndex = 2;
            this.searchBtn.Text = "cauta";
            this.searchBtn.UseVisualStyleBackColor = true;
            this.searchBtn.Click += new System.EventHandler(this.searchBtn_Click);
            // 
            // searchView
            // 
            this.searchView.ColumnHeadersHeightSizeMode =
                System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.searchView.Location = new System.Drawing.Point(38, 416);
            this.searchView.Name = "searchView";
            this.searchView.RowTemplate.Height = 28;
            this.searchView.Size = new System.Drawing.Size(570, 201);
            this.searchView.TabIndex = 1;
            // 
            // destinationTextBox
            // 
            this.destinationTextBox.Location = new System.Drawing.Point(262, 352);
            this.destinationTextBox.Name = "destinationTextBox";
            this.destinationTextBox.Size = new System.Drawing.Size(164, 26);
            this.destinationTextBox.TabIndex = 4;
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(38, 319);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(111, 21);
            this.label1.TabIndex = 5;
            this.label1.Text = "data";
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(262, 319);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(108, 23);
            this.label2.TabIndex = 6;
            this.label2.Text = "destinatie";
            // 
            // clientNameTextBox
            // 
            this.clientNameTextBox.Location = new System.Drawing.Point(731, 121);
            this.clientNameTextBox.Name = "clientNameTextBox";
            this.clientNameTextBox.Size = new System.Drawing.Size(164, 26);
            this.clientNameTextBox.TabIndex = 7;
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(731, 95);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(108, 23);
            this.label3.TabIndex = 8;
            this.label3.Text = "nume client";
            // 
            // noSeatsTextBox
            // 
            this.noSeatsTextBox.Location = new System.Drawing.Point(731, 404);
            this.noSeatsTextBox.Name = "noSeatsTextBox";
            this.noSeatsTextBox.Size = new System.Drawing.Size(164, 26);
            this.noSeatsTextBox.TabIndex = 9;
            // 
            // addresTextBox
            // 
            this.addresTextBox.Location = new System.Drawing.Point(731, 309);
            this.addresTextBox.Name = "addresTextBox";
            this.addresTextBox.Size = new System.Drawing.Size(164, 26);
            this.addresTextBox.TabIndex = 10;
            // 
            // touristsTextBox
            // 
            this.touristsTextBox.Location = new System.Drawing.Point(731, 209);
            this.touristsTextBox.Name = "touristsTextBox";
            this.touristsTextBox.Size = new System.Drawing.Size(164, 26);
            this.touristsTextBox.TabIndex = 11;
            // 
            // label4
            // 
            this.label4.Location = new System.Drawing.Point(731, 378);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(108, 23);
            this.label4.TabIndex = 12;
            this.label4.Text = "numar locuri";
            // 
            // label5
            // 
            this.label5.Location = new System.Drawing.Point(731, 283);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(108, 23);
            this.label5.TabIndex = 13;
            this.label5.Text = "adresa client";
            // 
            // label6
            // 
            this.label6.Location = new System.Drawing.Point(731, 183);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(108, 23);
            this.label6.TabIndex = 14;
            this.label6.Text = "nume turisti";
            // 
            // buyBtn
            // 
            this.buyBtn.Location = new System.Drawing.Point(797, 465);
            this.buyBtn.Name = "buyBtn";
            this.buyBtn.Size = new System.Drawing.Size(97, 34);
            this.buyBtn.TabIndex = 15;
            this.buyBtn.Text = "cumpara";
            this.buyBtn.UseVisualStyleBackColor = true;
            this.buyBtn.Click += new System.EventHandler(this.buyBtn_Click);
            // 
            // logOutBtn
            // 
            this.logOutBtn.Location = new System.Drawing.Point(772, 582);
            this.logOutBtn.Name = "logOutBtn";
            this.logOutBtn.Size = new System.Drawing.Size(98, 34);
            this.logOutBtn.TabIndex = 16;
            this.logOutBtn.Text = "Log Out";
            this.logOutBtn.UseVisualStyleBackColor = true;
            this.logOutBtn.Click += new System.EventHandler(this.logOutBtn_Click);
            // 
            // dateTimePicker1
            // 
            this.dateTimePicker1.Format = System.Windows.Forms.DateTimePickerFormat.Short;
            this.dateTimePicker1.Location = new System.Drawing.Point(38, 352);
            this.dateTimePicker1.Name = "dateTimePicker1";
            this.dateTimePicker1.Size = new System.Drawing.Size(119, 26);
            this.dateTimePicker1.TabIndex = 17;
            this.dateTimePicker1.Value = new System.DateTime(2024, 3, 30, 14, 11, 30, 0);
            // 
            // App
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.ClientSize = new System.Drawing.Size(978, 644);
            this.Controls.Add(this.dateTimePicker1);
            this.Controls.Add(this.logOutBtn);
            this.Controls.Add(this.buyBtn);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.touristsTextBox);
            this.Controls.Add(this.addresTextBox);
            this.Controls.Add(this.noSeatsTextBox);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.clientNameTextBox);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.destinationTextBox);
            this.Controls.Add(this.searchBtn);
            this.Controls.Add(this.searchView);
            this.Controls.Add(this.flightsView);
            this.Location = new System.Drawing.Point(15, 15);
            this.Name = "App";
            ((System.ComponentModel.ISupportInitialize)(this.flightsView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.searchView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        private System.Windows.Forms.DateTimePicker dateTimePicker1;

        private System.Windows.Forms.Button logOutBtn;

        private System.Windows.Forms.Button buyBtn;

        private System.Windows.Forms.TextBox clientNameTextBox;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox noSeatsTextBox;
        private System.Windows.Forms.TextBox addresTextBox;
        private System.Windows.Forms.TextBox touristsTextBox;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;

        private System.Windows.Forms.Button searchBtn;
        private System.Windows.Forms.TextBox destinationTextBox;

        private System.Windows.Forms.DataGridView searchView;

        private System.Windows.Forms.DataGridView flightsView;

        #endregion
    }
}