using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Microsoft.Data.SqlClient;


namespace Project1
{
    public partial class Form1 : Form
    {
        DataSet ds = new DataSet();
        SqlDataAdapter daParent = new SqlDataAdapter();
        SqlDataAdapter daChild = new SqlDataAdapter();

        string ParentTableName = ConfigurationManager.AppSettings["ParentTableName"];
        string ChildTableName = ConfigurationManager.AppSettings["ChildTableName"];

        string ChildColumnNames = ConfigurationManager.AppSettings["ChildColumnNames"];
        List<string> ColumnNamesList = new List<string>(ConfigurationManager.AppSettings["ChildColumnNames"].Split(','));
        string ColumnNamesInsertParameters = ConfigurationManager.AppSettings["ColumnNamesInsertParameters"];

        public Form1()
        {
            
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            ConfigurationManager.RefreshSection("appSettings");
            string con = ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
            SqlConnection cs = new SqlConnection(con);

            string selectFromParent = ConfigurationSettings.AppSettings["selectFromParent"];
            daParent.SelectCommand = new SqlCommand(selectFromParent, cs);
            ds.Clear();
            daParent.Fill(ds, ParentTableName);
            dataGridViewParent.DataSource = ds.Tables[ParentTableName];

            int labelY = 3; 
            int textFieldY = 3; 
            

            foreach (string columnName in ColumnNamesList.Skip(1).Take(ColumnNamesList.Count - 1))
            {
                Label label = new Label();
                label.Text = columnName + ":";
                label.AutoSize = true;
                label.Location = new Point(0, labelY);

                TextBox textField = new TextBox();
                textField.Name = columnName;
                textField.Location = new Point(100, textFieldY);
                textField.Width = 160;

                panel.Controls.Add(label);
                panel.Controls.Add(textField);

                labelY += 20;
                textFieldY += 20;
            }
        }


        private void buttonAdd_Click(object sender, EventArgs e)
        {
            try
            {
                string con = ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
                SqlConnection cs = new SqlConnection(con);
                cs.Open();

                SqlCommand cmd = new SqlCommand("INSERT INTO " + ChildTableName + " (" + ChildColumnNames + ") VALUES (" + ColumnNamesInsertParameters + ")", cs);

                
                int id = Convert.ToInt32(dataGridViewParent.Rows[dataGridViewParent.CurrentCell.RowIndex].Cells[0].Value);
                cmd.Parameters.AddWithValue("@parentId", id);

                int pos = 1;
                List<string> ColumnNamesInsertParametersList = new List<string>(ConfigurationManager.AppSettings["ColumnNamesInsertParameters"].Split(','));
                foreach (string column in ColumnNamesList.Skip(1).Take(ColumnNamesList.Count - 1))
                {
                    TextBox textBox = (TextBox)panel.Controls[column];
                    cmd.Parameters.AddWithValue(ColumnNamesInsertParametersList[pos], textBox.Text);
                    pos++;
                }

                // Execute the command
                cmd.ExecuteNonQuery();

                // Refresh the child DataGridView
                if (ds.Tables.Contains(ChildTableName))
                {
                    ds.Tables[ChildTableName].Clear();
                }
                daChild.Fill(ds, ChildTableName);
                dataGridViewChild.DataSource = ds.Tables[ChildTableName];

                cs.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error occurred: " + ex.Message);
            }
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            try
            {
                string con = ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
                SqlConnection cs = new SqlConnection(con);
                cs.Open();

                string update = ConfigurationSettings.AppSettings["UpdateQuery"];
                SqlCommand cmd = new SqlCommand(update, cs);


                int id = Convert.ToInt32(dataGridViewChild.Rows[dataGridViewChild.CurrentCell.RowIndex].Cells[0].Value);
                cmd.Parameters.AddWithValue("@id", id);

                int pos = 1;
                List<string> ColumnNamesInsertParametersList = new List<string>(ConfigurationManager.AppSettings["ColumnNamesInsertParameters"].Split(','));
                foreach (string column in ColumnNamesList.Skip(1).Take(ColumnNamesList.Count - 1))
                {
                    TextBox textBox = (TextBox)panel.Controls[column];
                    cmd.Parameters.AddWithValue(ColumnNamesInsertParametersList[pos], textBox.Text);
                    pos++;
                }

                // Execute the command
                cmd.ExecuteNonQuery();

                // Refresh the child DataGridView
                if (ds.Tables.Contains(ChildTableName))
                {
                    ds.Tables[ChildTableName].Clear();
                }
                daChild.Fill(ds, ChildTableName);
                dataGridViewChild.DataSource = ds.Tables[ChildTableName];

                cs.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error occurred: " + ex.Message);
            }
        }

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            try
            {
                string con = ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
                SqlConnection cs = new SqlConnection(con);
                cs.Open();

                string delete = ConfigurationSettings.AppSettings["DeleteQuery"];
                SqlCommand cmd = new SqlCommand(delete, cs);

                int id = Convert.ToInt32(dataGridViewChild.Rows[dataGridViewChild.CurrentCell.RowIndex].Cells[0].Value);
                cmd.Parameters.AddWithValue("@id", id);

                // Execute the command
                cmd.ExecuteNonQuery();

                // Refresh the child DataGridView
                if (ds.Tables.Contains(ChildTableName))
                {
                    ds.Tables[ChildTableName].Clear();
                }
                daChild.Fill(ds, ChildTableName);
                dataGridViewChild.DataSource = ds.Tables[ChildTableName];

                cs.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error occurred: " + ex.Message);
            }
        }

        private void dataGridViewParent_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            
            if (dataGridViewParent.SelectedCells.Count == 0)
            {
                MessageBox.Show("Select an event!");
                return;
            } else if (dataGridViewParent.SelectedCells.Count > 1)
            {
                MessageBox.Show("Select only an event!");
                return;
            }
            
            int id = Convert.ToInt32(dataGridViewParent.Rows[dataGridViewParent.CurrentCell.RowIndex].Cells[0].Value);
            
            string con = ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
            SqlConnection cs = new SqlConnection(con);

 
            string selectFromChild = ConfigurationSettings.AppSettings["selectFromChild"];
                
                
            SqlCommand cmd = new SqlCommand(selectFromChild, cs);
            cmd.Parameters.AddWithValue("@id", id);
            
            if (ds.Tables.Contains(ChildTableName))
            {
                ds.Tables[ChildTableName].Clear();
            }

            daChild.SelectCommand = cmd;
            daChild.Fill(ds, ChildTableName);
            dataGridViewChild.DataSource = ds.Tables[ChildTableName];
        }


    }
    
}