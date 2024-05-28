using System;
using System.Collections.Generic;
using System.ComponentModel;
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
        private string connectionString = @"Server=LAPTOP-7V5C7GV\SQLEXPRESS; Database=S12; 
            Integrated Security=true; TrustServerCertificate=true;";
        DataSet ds = new DataSet();
        SqlDataAdapter parentAdapter;
        SqlDataAdapter childAdapter;
        BindingSource bsParent;
        BindingSource bsChild;
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    conn.Open();
                    parentAdapter = new SqlDataAdapter("SELECT * FROM TipuriFructe", conn);
                    childAdapter = new SqlDataAdapter("SELECT * FROM Fructe", conn);
                    parentAdapter.Fill(ds, "TipuriFructe"); // parent
                    childAdapter.Fill(ds, "Fructe"); // child
                    bsParent = new BindingSource();
                    bsParent.DataSource = ds.Tables["TipuriFructe"]; // parent
                    dataGridViewParent.DataSource = bsParent;
                    DataColumn parentColumn = ds.Tables["TipuriFructe"].Columns["Tid"]; // Cheia primara Parinte
                    DataColumn childColumn = ds.Tables["Fructe"].Columns["Tid"]; // Cheia Straina copil

                    DataRelation relation = new DataRelation("[FK__Fructe__Tid__398D8EEE]", parentColumn, childColumn); // Nume FK
                    ds.Relations.Add(relation);
                    bsChild = new BindingSource();
                    bsChild.DataSource = bsParent;
                    bsChild.DataMember = "[FK__Fructe__Tid__398D8EEE]"; // Nume FK
                    dataGridViewChild.DataSource = bsChild;

                }
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
                throw;
            }
        }

        private void buttonAdd_Click(object sender, EventArgs e)
        {
            if (dataGridViewParent.SelectedCells.Count == 0)
            {
                MessageBox.Show("Select an element from parent!");
                return;
            } else if (dataGridViewParent.SelectedCells.Count > 1)
            {
                MessageBox.Show("Select only an element from parent!");
                return;
            }
            
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    conn.Open();
                    SqlCommand insertCommand = new SqlCommand(
                        "INSERT INTO Fructe (Denumire, Culoare, LunaOptimaCulegere, PretMediu, Tid) " +
                        "VALUES (@Denumire, @Culoare, @LunaOptimaCulegere, @PretMediu, @Tid);", conn);

                    int parent_id = Convert.ToInt32(dataGridViewParent.Rows[dataGridViewParent.CurrentCell.RowIndex].Cells[0].Value);
                    insertCommand.Parameters.AddWithValue("@Tid", parent_id);
                    insertCommand.Parameters.AddWithValue("@Denumire", textBoxParam1.Text);
                    // insertCommand.Parameters.AddWithValue("@date_val", dateTimePickerDate.Value);
                    insertCommand.Parameters.AddWithValue("@Culoare", textBoxParam2.Text);
                    insertCommand.Parameters.AddWithValue("@LunaOptimaCulegere", textBoxParam3.Text);
                    insertCommand.Parameters.AddWithValue("@PretMediu", textBoxParam4.Text);


                    int insertRowCount = insertCommand.ExecuteNonQuery();
                    
                    childAdapter.SelectCommand.Connection = conn;
                    ds.Tables["Fructe"].Clear(); // tabel copil
                    childAdapter.Fill(ds, "Fructe"); // tabel copil

                    // ClearInputFields();

                }
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
                throw;
            }
        }

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            if (dataGridViewChild.SelectedCells.Count == 0)
            {
                MessageBox.Show("Select an element from child!");
                return;
            } else if (dataGridViewChild.SelectedCells.Count > 1)
            {
                MessageBox.Show("Select only an element from child!");
                return;
            }
            
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    conn.Open();
                    SqlCommand deleteCommand = new SqlCommand("DELETE FROM fructe WHERE Fid=@id;", conn);
                    
                    int child_id = Convert.ToInt32(dataGridViewChild.Rows[dataGridViewChild.CurrentCell.RowIndex].Cells[0].Value);
                    Console.WriteLine(child_id);
                    deleteCommand.Parameters.AddWithValue("@id", child_id); 

                    int deleteRowCount = deleteCommand.ExecuteNonQuery();
                    
                    childAdapter.SelectCommand.Connection = conn;
                    ds.Tables["fructe"].Clear();  // child table
                    childAdapter.Fill(ds, "fructe"); // child table
                    
                }
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
                throw;
            }
            
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            if (dataGridViewChild.SelectedCells.Count == 0)
            {
                MessageBox.Show("Select an element from child!");
                return;
            } else if (dataGridViewChild.SelectedCells.Count > 1)
            {
                MessageBox.Show("Select only an element from child!");
                return;
            }
            
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    conn.Open();
                    SqlCommand updateCommand = new SqlCommand("UPDATE fructe SET Denumire=@denumire, Culoare=@culoare, LunaOptimaCulegere=@luna, PretMediu=@pret" + 
                                                              "  WHERE Fid=@id;", conn);
                    
                    int child_id = Convert.ToInt32(dataGridViewChild.Rows[dataGridViewChild.CurrentCell.RowIndex].Cells[0].Value);
                    Console.WriteLine(child_id);
                    updateCommand.Parameters.AddWithValue("@id", child_id);
                    updateCommand.Parameters.AddWithValue("@denumire", textBoxParam1.Text);
                    // updateCommand.Parameters.AddWithValue("@date_val", dateTimePickerDate.Value);
                    updateCommand.Parameters.AddWithValue("@culoare", textBoxParam2.Text);
                    updateCommand.Parameters.AddWithValue("@luna", textBoxParam3.Text);
                    updateCommand.Parameters.AddWithValue("@pret", textBoxParam4.Text);

                    
                    int deleteRowCount = updateCommand.ExecuteNonQuery();
                    
                    childAdapter.SelectCommand.Connection = conn;
                    ds.Tables["Fructe"].Clear(); // child table
                    childAdapter.Fill(ds, "Fructe"); // child table

                    ClearInputFields();

                }
            }
            catch (Exception exception)
            {
                Console.WriteLine(exception);
                throw;
            }
        }

        private void ClearInputFields()
        {
            textBoxParam1.Clear();
            dateTimePickerDate.ResetText();
            textBoxParam2.Clear();
            textBoxParam3.Clear();
            textBoxParam4.Clear();
        }
        
    }
}