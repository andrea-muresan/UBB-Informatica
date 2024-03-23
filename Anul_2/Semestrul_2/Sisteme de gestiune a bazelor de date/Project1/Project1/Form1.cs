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
        private string connectionString = @"Server=LAPTOP-7V5C7GV\SQLEXPRESS; Database=AstronomicalEvents2; 
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
                    parentAdapter = new SqlDataAdapter("SELECT * FROM event", conn);
                    childAdapter = new SqlDataAdapter("SELECT * FROM image", conn);
                    parentAdapter.Fill(ds, "event");
                    childAdapter.Fill(ds, "image");
                    bsParent = new BindingSource();
                    bsParent.DataSource = ds.Tables["event"];
                    dataGridViewEvents.DataSource = bsParent;
                    DataColumn parentColumn = ds.Tables["event"].Columns["id"];
                    DataColumn childColumn = ds.Tables["image"].Columns["event_id"];

                    DataRelation relation = new DataRelation("FK_image_event", parentColumn, childColumn);
                    ds.Relations.Add(relation);
                    bsChild = new BindingSource();
                    bsChild.DataSource = bsParent;
                    bsChild.DataMember = "FK_image_event";
                    dataGridViewImages.DataSource = bsChild;

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
            if (dataGridViewEvents.SelectedCells.Count == 0)
            {
                MessageBox.Show("Select an event!");
                return;
            } else if (dataGridViewEvents.SelectedCells.Count > 1)
            {
                MessageBox.Show("Select only an event!");
                return;
            }
            
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    conn.Open();
                    SqlCommand insertCommand = new SqlCommand(
                        "INSERT INTO image (event_id, photographer, date_taken, image_url, city, country) " +
                        "VALUES (@event_val, @photographer_val, @date_val, @url_val, @city_val, @country_val);", conn);

                    int ev_id = Convert.ToInt32(dataGridViewEvents.Rows[dataGridViewEvents.CurrentCell.RowIndex].Cells[0].Value);
                    insertCommand.Parameters.AddWithValue("@event_val", ev_id);
                    insertCommand.Parameters.AddWithValue("@photographer_val", textBoxPhotographer.Text);
                    insertCommand.Parameters.AddWithValue("@date_val", dateTimePickerDate.Value);
                    insertCommand.Parameters.AddWithValue("@url_val", textBoxURL.Text);
                    insertCommand.Parameters.AddWithValue("@city_val", textBoxCity.Text);
                    insertCommand.Parameters.AddWithValue("@country_val", textBoxCountry.Text);

                    int insertRowCount = insertCommand.ExecuteNonQuery();
                    
                    childAdapter.SelectCommand.Connection = conn;
                    ds.Tables["image"].Clear();
                    childAdapter.Fill(ds, "image");

                    ClearInputFields();

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
            if (dataGridViewImages.SelectedCells.Count == 0)
            {
                MessageBox.Show("Select an image!");
                return;
            } else if (dataGridViewImages.SelectedCells.Count > 1)
            {
                MessageBox.Show("Select only an image!");
                return;
            }
            
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    conn.Open();
                    SqlCommand deleteCommand = new SqlCommand("DELETE FROM image WHERE id=@id;", conn);
                    
                    int im_id = Convert.ToInt32(dataGridViewImages.Rows[dataGridViewImages.CurrentCell.RowIndex].Cells[0].Value);
                    Console.WriteLine(im_id);
                    deleteCommand.Parameters.AddWithValue("@id", im_id);

                    int deleteRowCount = deleteCommand.ExecuteNonQuery();
                    
                    childAdapter.SelectCommand.Connection = conn;
                    ds.Tables["image"].Clear();
                    childAdapter.Fill(ds, "image");
                    
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
            if (dataGridViewImages.SelectedCells.Count == 0)
            {
                MessageBox.Show("Select an image!");
                return;
            } else if (dataGridViewImages.SelectedCells.Count > 1)
            {
                MessageBox.Show("Select only an image!");
                return;
            }
            
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    conn.Open();
                    SqlCommand updateCommand = new SqlCommand("UPDATE image " + 
                                                              "SET photographer=@photographer_val, date_taken=@date_val, image_url=@url_val, city=@city_val, country=@country_val " + 
                                                              "WHERE id=@id;", conn);
                    
                    int im_id = Convert.ToInt32(dataGridViewImages.Rows[dataGridViewImages.CurrentCell.RowIndex].Cells[0].Value);
                    Console.WriteLine(im_id);
                    updateCommand.Parameters.AddWithValue("@id", im_id);
                    updateCommand.Parameters.AddWithValue("@photographer_val", textBoxPhotographer.Text);
                    updateCommand.Parameters.AddWithValue("@date_val", dateTimePickerDate.Value);
                    updateCommand.Parameters.AddWithValue("@url_val", textBoxURL.Text);
                    updateCommand.Parameters.AddWithValue("@city_val", textBoxCity.Text);
                    updateCommand.Parameters.AddWithValue("@country_val", textBoxCountry.Text);
                    
                    int deleteRowCount = updateCommand.ExecuteNonQuery();
                    
                    childAdapter.SelectCommand.Connection = conn;
                    ds.Tables["image"].Clear();
                    childAdapter.Fill(ds, "image");

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
            textBoxPhotographer.Clear();
            dateTimePickerDate.ResetText();
            textBoxURL.Clear();
            textBoxCity.Clear();
            textBoxCountry.Clear();
        }
    }
}