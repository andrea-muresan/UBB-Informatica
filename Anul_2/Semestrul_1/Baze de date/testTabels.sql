USE AstronomicalEvents2
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[fk_test_run_tables_tables]') and OBJECTPROPERTY(id, N'is_foreign_key') = 1)
ALTER TABLE [test_run_tables] DROP CONSTRAINT fk_test_run_tables_tables
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[fk_test_tables_tables]') and OBJECTPROPERTY(id, N'is_foreign_key') = 1)
ALTER TABLE [test_tables] DROP CONSTRAINT fk_test_tables_tables
GO
 
if exists (select * from dbo.sysobjects where id = object_id(N'[fk_test_run_tables_test_runs]') and OBJECTPROPERTY(id, N'is_foreign_key') = 1)
ALTER TABLE [test_run_tables] DROP CONSTRAINT fk_test_run_tables_test_runs
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[fk_test_run_views_test_runs]') and OBJECTPROPERTY(id, N'is_foreign_key') = 1)
ALTER TABLE [test_run_views] DROP CONSTRAINT fk_test_run_views_test_runs
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[fk_test_tables_tests]') and OBJECTPROPERTY(id, N'is_foreign_key') = 1)
ALTER TABLE [test_tables] DROP CONSTRAINT fk_test_tables_tests
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[fk_test_views_tests]') and OBJECTPROPERTY(id, N'is_foreign_key') = 1)
ALTER TABLE [test_views] DROP CONSTRAINT fk_test_views_tests
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[fk_test_run_views_views]') and OBJECTPROPERTY(id, N'is_foreign_key') = 1)
ALTER TABLE [test_run_views] DROP CONSTRAINT fk_test_run_views_views
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[fk_test_views_views]') and OBJECTPROPERTY(id, N'is_foreign_key') = 1)
ALTER TABLE [test_views] DROP CONSTRAINT fk_test_views_views
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[tables]') and OBJECTPROPERTY(id, N'is_user_table') = 1)
drop table [tables]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[test_run_tables]') and OBJECTPROPERTY(id, N'is_user_table') = 1)
drop table [test_run_tables]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[test_run_views]') and OBJECTPROPERTY(id, N'is_user_table') = 1)
drop table [test_run_views]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[test_runs]') and OBJECTPROPERTY(id, N'is_user_table') = 1)
drop table [test_runs]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[test_tables]') and OBJECTPROPERTY(id, N'is_user_table') = 1)
drop table [test_tables]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[test_views]') and OBJECTPROPERTY(id, N'is_user_table') = 1)
drop table [test_views]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[tests]') and OBJECTPROPERTY(id, N'is_user_table') = 1)
drop table [tests]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[views]') and OBJECTPROPERTY(id, N'is_user_table') = 1)
drop table [views]
GO

CREATE TABLE [tables] (
	[table_id] [int] IDENTITY (1, 1) NOT NULL,
	[name] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [test_run_tables] (
	[test_run_id] [int] NOT NULL ,
	[table_id] [int] NOT NULL ,
	[start_at] [datetime] NOT NULL ,
	[end_at] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [test_run_views] (
	[test_run_id] [int] NOT NULL ,
	[view_id] [int] NOT NULL ,
	[start_at] [datetime] NOT NULL ,
	[end_at] [datetime] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [test_runs] (
	[test_run_id] [int] IDENTITY (1, 1) NOT NULL ,
	[description] [nvarchar] (2000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
	[start_at] [datetime] NULL ,
	[end_at] [datetime] NULL 
) ON [PRIMARY]

CREATE TABLE [test_tables] (
	[test_id] [int] NOT NULL ,
	[table_id] [int] NOT NULL ,
	[no_of_rows] [int] NOT NULL ,
	[position] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [test_views] (
	[test_id] [int] NOT NULL ,
	[view_id] [int] NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [tests] (
	[test_id] [int] IDENTITY (1, 1) NOT NULL ,
	[name] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 
) ON [PRIMARY]
GO

CREATE TABLE [views] (
	[view_id] [int] IDENTITY (1, 1) NOT NULL ,
	[name] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 
) ON [PRIMARY]
GO

ALTER TABLE [tables] WITH NOCHECK ADD 
	CONSTRAINT [pk_Tables] PRIMARY KEY  CLUSTERED 
	(
		[table_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [test_run_tables] WITH NOCHECK ADD 
	CONSTRAINT [pk_test_run_tables] PRIMARY KEY  CLUSTERED 
	(
		[test_run_id],
		[table_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [test_run_views] WITH NOCHECK ADD 
	CONSTRAINT [pk_test_run_views] PRIMARY KEY  CLUSTERED 
	(
		[test_run_id],
		[view_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [test_runs] WITH NOCHECK ADD 
	CONSTRAINT [pk_test_runs] PRIMARY KEY  CLUSTERED 
	(
		[test_run_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [test_tables] WITH NOCHECK ADD 
	CONSTRAINT [pk_test_tables] PRIMARY KEY  CLUSTERED 
	(
		[test_id],
		[table_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [test_views] WITH NOCHECK ADD 
	CONSTRAINT [pk_test_views] PRIMARY KEY  CLUSTERED 
	(
		[test_id],
		[view_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [tests] WITH NOCHECK ADD 
	CONSTRAINT [pk_tests] PRIMARY KEY  CLUSTERED 
	(
		[test_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [views] WITH NOCHECK ADD 
	CONSTRAINT [pk_views] PRIMARY KEY  CLUSTERED 
	(
		[view_id]
	)  ON [PRIMARY] 
GO

ALTER TABLE [test_run_tables] ADD 
	CONSTRAINT [fk_test_run_tables_Tables] FOREIGN KEY 
	(
		[table_id]
	) REFERENCES [tables] (
		[table_id]
	) ON DELETE CASCADE  ON UPDATE CASCADE ,
	CONSTRAINT [fk_test_run_tables_test_runs] FOREIGN KEY 
	(
		[test_run_id]
	) REFERENCES [test_runs] (
		[test_run_id]
	) ON DELETE CASCADE  ON UPDATE CASCADE 
GO

ALTER TABLE [test_run_views] ADD 
	CONSTRAINT [fk_test_run_views_test_runs] FOREIGN KEY 
	(
		[test_run_id]
	) REFERENCES [test_runs] (
		[test_run_id]
	) ON DELETE CASCADE  ON UPDATE CASCADE ,
	CONSTRAINT [fk_test_run_views_views] FOREIGN KEY 
	(
		[view_id]
	) REFERENCES [views] (
		[view_id]
	) ON DELETE CASCADE  ON UPDATE CASCADE 
GO

ALTER TABLE [test_tables] ADD 
	CONSTRAINT [fk_test_tables_tables] FOREIGN KEY 
	(
		[table_id]
	) REFERENCES [tables] (
		[table_id]
	) ON DELETE CASCADE  ON UPDATE CASCADE ,
	CONSTRAINT [fk_test_tables_tests] FOREIGN KEY 
	(
		[test_id]
	) REFERENCES [tests] (
		[test_id]
	) ON DELETE CASCADE  ON UPDATE CASCADE 
GO

ALTER TABLE [test_views] ADD 
	CONSTRAINT [fk_test_views_tests] FOREIGN KEY 
	(
		[test_id]
	) REFERENCES [tests] (
		[test_id]
	),
	CONSTRAINT [fk_test_views_views] FOREIGN KEY 
	(
		[view_id]
	) REFERENCES [views] (
		[view_id]
	)
GO


