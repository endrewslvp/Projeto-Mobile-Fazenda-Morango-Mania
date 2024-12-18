USE [MorangoManiaMobile]
GO
/****** Object:  Table [dbo].[Cadastro]    Script Date: 26/11/2024 22:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cadastro](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[NomeFull] [nvarchar](100) NOT NULL,
	[CPF] [nvarchar](14) NOT NULL,
	[Cargo] [nvarchar](100) NULL,
	[Email] [nvarchar](100) NULL,
	[Senha] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cargos]    Script Date: 26/11/2024 22:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cargos](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Nome] [nvarchar](100) NULL,
	[Descricao] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Clientes]    Script Date: 26/11/2024 22:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Clientes](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Nome] [nvarchar](100) NOT NULL,
	[Telefone] [nvarchar](20) NOT NULL,
	[Email] [nvarchar](100) NOT NULL,
	[cpf] [nvarchar](20) NOT NULL,
	[CEP] [nvarchar](20) NOT NULL,
	[Rua] [nvarchar](100) NOT NULL,
	[Numero] [nvarchar](20) NOT NULL,
	[Bairro] [nvarchar](100) NOT NULL,
	[senha] [nvarchar](255) NOT NULL,
	[Cidade] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Colaboradores]    Script Date: 26/11/2024 22:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Colaboradores](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Nome] [nvarchar](100) NULL,
	[CPF] [nvarchar](14) NULL,
	[Horario] [nvarchar](30) NULL,
	[Cargo] [nvarchar](30) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Ferramentas]    Script Date: 26/11/2024 22:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Ferramentas](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Nome] [nvarchar](100) NULL,
	[Descricao] [nvarchar](100) NULL,
	[Tipo] [nvarchar](100) NULL,
	[QtdFerramenta] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Fornecedores]    Script Date: 26/11/2024 22:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Fornecedores](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Nome] [nvarchar](100) NULL,
	[TipoFornecimento] [nvarchar](100) NULL,
	[Descricao] [nvarchar](200) NULL,
	[Telefone] [nvarchar](20) NULL,
	[Email] [nvarchar](100) NULL,
	[CNPJ] [nvarchar](20) NULL,
	[Bairro] [nvarchar](100) NULL,
	[Numero] [nvarchar](20) NULL,
	[Rua] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Nutrientes]    Script Date: 26/11/2024 22:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Nutrientes](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Nome] [nvarchar](100) NULL,
	[Validade] [date] NULL,
	[Quantidade] [int] NULL,
	[Descricao] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Produtos]    Script Date: 26/11/2024 22:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Produtos](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Nome] [nvarchar](100) NULL,
	[Preco] [decimal](18, 2) NULL,
	[Validade] [date] NULL,
	[QtdProduto] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sementes]    Script Date: 26/11/2024 22:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sementes](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Nome] [nvarchar](50) NOT NULL,
	[Quantidade] [int] NULL,
	[Descricao] [nvarchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Vendas]    Script Date: 26/11/2024 22:01:51 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Vendas](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[ID_Cliente] [int] NULL,
	[ID_Produto] [int] NULL,
	[Quantidade] [int] NULL,
	[TotalCompra] [decimal](10, 2) NULL,
	[CodigoCompra] [varchar](50) NULL,
	[MetodoPagamento] [nvarchar](25) NULL,
	[Endereco] [nvarchar](255) NOT NULL,
	[Situacao] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Clientes] ADD  DEFAULT ('Desconhecida') FOR [Cidade]
GO
ALTER TABLE [dbo].[Vendas] ADD  DEFAULT ('A preparar') FOR [Situacao]
GO
ALTER TABLE [dbo].[Vendas]  WITH CHECK ADD FOREIGN KEY([ID_Produto])
REFERENCES [dbo].[Produtos] ([Id])
GO
ALTER TABLE [dbo].[Vendas]  WITH CHECK ADD FOREIGN KEY([ID_Cliente])
REFERENCES [dbo].[Clientes] ([Id])
GO
