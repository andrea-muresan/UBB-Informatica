namespace Project1
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

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
            this.buttonAdd = new System.Windows.Forms.Button();
            this.buttonUpdate = new System.Windows.Forms.Button();
            this.buttonDelete = new System.Windows.Forms.Button();
            this.dataGridViewParent = new System.Windows.Forms.DataGridView();
            this.dataGridViewChild = new System.Windows.Forms.DataGridView();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.panel = new System.Windows.Forms.Panel();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParent)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewChild)).BeginInit();
            this.SuspendLayout();
            // 
            // buttonAdd
            // 
            this.buttonAdd.Font = new System.Drawing.Font("Goudy Old Style", 10F);
            this.buttonAdd.Location = new System.Drawing.Point(762, 485);
            this.buttonAdd.Name = "buttonAdd";
            this.buttonAdd.Size = new System.Drawing.Size(109, 42);
            this.buttonAdd.TabIndex = 10;
            this.buttonAdd.Text = "Add";
            this.buttonAdd.UseVisualStyleBackColor = true;
            this.buttonAdd.Click += new System.EventHandler(this.buttonAdd_Click);
            // 
            // buttonUpdate
            // 
            this.buttonUpdate.Font = new System.Drawing.Font("Goudy Old Style", 10F);
            this.buttonUpdate.Location = new System.Drawing.Point(927, 485);
            this.buttonUpdate.Name = "buttonUpdate";
            this.buttonUpdate.Size = new System.Drawing.Size(109, 42);
            this.buttonUpdate.TabIndex = 11;
            this.buttonUpdate.Text = "Update";
            this.buttonUpdate.UseVisualStyleBackColor = true;
            this.buttonUpdate.Click += new System.EventHandler(this.buttonUpdate_Click);
            // 
            // buttonDelete
            // 
            this.buttonDelete.Font = new System.Drawing.Font("Goudy Old Style", 10F);
            this.buttonDelete.Location = new System.Drawing.Point(1073, 485);
            this.buttonDelete.Name = "buttonDelete";
            this.buttonDelete.Size = new System.Drawing.Size(109, 42);
            this.buttonDelete.TabIndex = 12;
            this.buttonDelete.Text = "Delete";
            this.buttonDelete.UseVisualStyleBackColor = true;
            this.buttonDelete.Click += new System.EventHandler(this.buttonDelete_Click);
            // 
            // dataGridViewParent
            // 
            this.dataGridViewParent.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewParent.Location = new System.Drawing.Point(25, 48);
            this.dataGridViewParent.Name = "dataGridViewParent";
            this.dataGridViewParent.RowHeadersWidth = 62;
            this.dataGridViewParent.RowTemplate.Height = 28;
            this.dataGridViewParent.Size = new System.Drawing.Size(716, 267);
            this.dataGridViewParent.TabIndex = 14;
            this.dataGridViewParent.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridViewParent_CellContentClick);
            // 
            // dataGridViewChild
            // 
            this.dataGridViewChild.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewChild.Location = new System.Drawing.Point(25, 363);
            this.dataGridViewChild.Name = "dataGridViewChild";
            this.dataGridViewChild.RowHeadersWidth = 62;
            this.dataGridViewChild.RowTemplate.Height = 28;
            this.dataGridViewChild.Size = new System.Drawing.Size(716, 314);
            this.dataGridViewChild.TabIndex = 15;
            // 
            // label6
            // 
            this.label6.Location = new System.Drawing.Point(25, 22);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(96, 23);
            this.label6.TabIndex = 16;
            this.label6.Text = "Parent:";
            // 
            // label7
            // 
            this.label7.Location = new System.Drawing.Point(25, 338);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(92, 22);
            this.label7.TabIndex = 17;
            this.label7.Text = "Child:";
            // 
            // panel
            // 
            this.panel.Location = new System.Drawing.Point(762, 51);
            this.panel.Name = "panel";
            this.panel.Size = new System.Drawing.Size(420, 416);
            this.panel.TabIndex = 18;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1209, 706);
            this.Controls.Add(this.panel);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.dataGridViewChild);
            this.Controls.Add(this.dataGridViewParent);
            this.Controls.Add(this.buttonDelete);
            this.Controls.Add(this.buttonUpdate);
            this.Controls.Add(this.buttonAdd);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParent)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewChild)).EndInit();
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.Panel panel;

        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;

        private System.Windows.Forms.DataGridView dataGridViewChild;

        private System.Windows.Forms.DataGridView dataGridViewParent;

        private System.Windows.Forms.Button buttonDelete;

        private System.Windows.Forms.Button buttonAdd;
        private System.Windows.Forms.Button buttonUpdate;

        #endregion
    }
}