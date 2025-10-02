-- Tabela Usuario
CREATE TABLE Usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    peso DECIMAL(5,2) NOT NULL,
    altura DECIMAL(3,2) NOT NULL,
    idade INT NOT NULL,
    sexo VARCHAR(10) NOT NULL,
    meta_calorica DECIMAL(7,2) NOT NULL,
    tipo_usuario ENUM('USUARIO', 'ADMIN') DEFAULT 'USUARIO',
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela Perfil
CREATE TABLE Perfil (
    id_perfil INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    preferencias TEXT,
    configuracoes TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE
);

-- Tabela TabelaNutricional
CREATE TABLE TabelaNutricional (
    id_tabela INT PRIMARY KEY AUTO_INCREMENT,
    nome_alimento VARCHAR(100) NOT NULL,
    calorias DECIMAL(6,2) NOT NULL,
    carboidratos DECIMAL(6,2) NOT NULL,
    proteinas DECIMAL(6,2) NOT NULL,
    gorduras DECIMAL(6,2) NOT NULL,
    id_usuario_solicitante INT, -- usuário que solicitou o cadastro
    status_aprovacao ENUM('PENDENTE', 'APROVADO', 'RECUSADO') DEFAULT 'PENDENTE',
    observacoes_admin TEXT, -- observações do admin sobre aprovação/recusa
    data_aprovacao TIMESTAMP NULL,
    id_admin_aprovador INT, -- admin que aprovou/recusou
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario_solicitante) REFERENCES Usuario(id_usuario) ON DELETE SET NULL,
    FOREIGN KEY (id_admin_aprovador) REFERENCES Usuario(id_usuario) ON DELETE SET NULL
);

-- Tabela Exercicio
CREATE TABLE Exercicio (
    id_exercicio INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    duracao DECIMAL(5,2) NOT NULL, -- em minutos
    intensidade VARCHAR(50) NOT NULL,
    calorias_gastas DECIMAL(6,2) NOT NULL,
    data_exercicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE
);

-- Tabela Refeicao
CREATE TABLE Refeicao (
    id_refeicao INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    quantidade DECIMAL(6,2) NOT NULL,
    alimentos TEXT NOT NULL,
    horario DATETIME NOT NULL,
    calorias DECIMAL(6,2) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE
);

-- Tabela Receita
CREATE TABLE Receita (
    id_receita INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    nome VARCHAR(100) NOT NULL,
    ingredientes TEXT NOT NULL,
    calorias_totais DECIMAL(7,2) NOT NULL,
    modo_preparo TEXT,
    tempo_preparo INT, -- em minutos
    porcoes INT DEFAULT 1,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE SET NULL
);

-- Tabela RegistroCalorias
CREATE TABLE RegistroCalorias (
    id_registro INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    data DATE NOT NULL,
    calorias_consumidas DECIMAL(7,2) DEFAULT 0,
    calorias_gastas DECIMAL(7,2) DEFAULT 0,
    saldo_calorico DECIMAL(7,2) AS (calorias_consumidas - calorias_gastas) STORED,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    UNIQUE KEY unique_user_date (id_usuario, data)
);

-- Tabela Conquista
CREATE TABLE Conquista (
    id_conquista INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    criterio VARCHAR(200), -- critério para alcançar a conquista
    icone VARCHAR(100), -- caminho para o ícone da conquista
    pontos INT DEFAULT 0,
    ativa BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de relacionamento Usuario_Conquista (para conquistas alcançadas pelos usuários)
CREATE TABLE Usuario_Conquista (
    id_usuario_conquista INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_conquista INT NOT NULL,
    data_alcance DATETIME NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_conquista) REFERENCES Conquista(id_conquista) ON DELETE CASCADE,
    UNIQUE KEY unique_user_achievement (id_usuario, id_conquista)
);

-- Tabela para relacionar receitas com ingredientes da tabela nutricional
CREATE TABLE Receita_Ingrediente (
    id_receita_ingrediente INT PRIMARY KEY AUTO_INCREMENT,
    id_receita INT NOT NULL,
    id_tabela_nutricional INT NOT NULL,
    quantidade DECIMAL(6,2) NOT NULL, -- quantidade em gramas
    FOREIGN KEY (id_receita) REFERENCES Receita(id_receita) ON DELETE CASCADE,
    FOREIGN KEY (id_tabela_nutricional) REFERENCES TabelaNutricional(id_tabela) ON DELETE CASCADE
);

-- Tabela para relacionar refeições com alimentos da tabela nutricional
CREATE TABLE Refeicao_Alimento (
    id_refeicao_alimento INT PRIMARY KEY AUTO_INCREMENT,
    id_refeicao INT NOT NULL,
    id_tabela_nutricional INT NOT NULL,
    quantidade DECIMAL(6,2) NOT NULL, -- quantidade em gramas
    FOREIGN KEY (id_refeicao) REFERENCES Refeicao(id_refeicao) ON DELETE CASCADE,
    FOREIGN KEY (id_tabela_nutricional) REFERENCES TabelaNutricional(id_tabela) ON DELETE CASCADE
);