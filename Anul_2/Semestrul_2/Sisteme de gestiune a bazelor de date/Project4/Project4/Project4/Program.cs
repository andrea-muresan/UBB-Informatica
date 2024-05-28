using System;
using System.Data.SqlClient;
using System.Threading;

namespace Project4
{
  internal class Program
  {
        private static string connectionString = @"Server=LAPTOP-7V5C7GV\SQLEXPRESS; Database=AstronomicalEvents2; Integrated Security=true; TrustServerCertificate=true;";
        private static int noRetriesTh1 = 0;
        private static int noRetriesTh2 = 0;

        static void Main(string[] args)
        {
            printData();
            Thread th1 = new Thread(startThread1);
            Thread th2 = new Thread(startThread2);
            
            th1.Start();
            th2.Start();
            Thread.Sleep(20000);
            printData();
        }

        private static void printData()
        {
            Console.WriteLine("\n\nPERSON");
            printTable("person");
            Console.WriteLine("\n\nOBSERVATORY");
            printTable("observatory");
            
        }
        private static void printTable(String tableName)
        {
            string query = "SELECT * FROM " + tableName; 

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                SqlCommand command = new SqlCommand(query, connection);
                connection.Open();

                SqlDataReader reader = command.ExecuteReader();

                while (reader.Read())
                {
                    for (int i = 0; i < reader.FieldCount; i++)
                    {
                        Console.Write(reader[i] + "\t");
                    }
                    Console.WriteLine();
                }

                reader.Close();
            }
        }

        private static void startThread1()
        {
            Console.WriteLine("In thread 1");
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    conn.Open();
                    SqlCommand cmd = new SqlCommand("startThread1", conn);
                    cmd.CommandType = System.Data.CommandType.StoredProcedure;
                    cmd.ExecuteNonQuery();
                    Console.WriteLine("Exit thread 1");
                }
            }
            catch(SqlException ex)
            {
                if (ex.Number == 1205)
                {
                    Console.WriteLine("Deadlock victim - thread 1");
                    noRetriesTh1++;
                    if (noRetriesTh1 == 3)
                        Console.WriteLine("Thread 1 - aborted.");
                    else
                    {
                        Console.WriteLine("Retry startThread1");
                        startThread1();
                    }
                }
                else
                    Console.WriteLine("Database error in th1: " + ex.Message);
            }
        }

        private static void startThread2()
        {
            Console.WriteLine("In thread 2");
            try
            {
                using (SqlConnection conn = new SqlConnection(connectionString))
                {
                    conn.Open();
                    SqlCommand cmd = new SqlCommand("startThread2", conn);
                    cmd.CommandType = System.Data.CommandType.StoredProcedure;
                    cmd.ExecuteNonQuery();
                    Console.WriteLine("Exit thread 2");
                }
            }
            catch (SqlException ex)
            {
                if (ex.Number == 1205)
                {
                    Console.WriteLine("Deadlock victim - thread 2");
                    noRetriesTh2++;
                    if (noRetriesTh2 == 3)
                        Console.WriteLine("Thread 2 - aborted.");
                    else
                    {
                        Console.WriteLine("Retry startThread2");
                        startThread2();
                    }
                }
                else
                    Console.WriteLine("Database error in th2: " + ex.Message);
            }
        }
  }
}