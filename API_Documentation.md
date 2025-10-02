## Base URL
```
http://localhost:8080/
```

## Autenticação

### 1. Login de Usuário
**POST** `/auth/login`

**Request Body:**
```json
{
  "email": "usuario@email.com",
  "senha": "123456"
}
```

**Response (Sucesso):**
```json
{
  "id_usuario": 1,
  "success": true,
  "message": "Login realizado com sucesso"
}
```

**Response (Erro):**
```json
{
  "success": false,
  "error": {
    "code": "INVALID_CREDENTIALS",
    "message": "Email ou senha incorretos"
  }
}
```

## Usuários

### 1. Cadastro de Usuário
**POST** `/usuarios/cadastro`

**Request Body:**
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456",
  "peso": 75.5,
  "altura": 1.75,
  "idade": 30,
  "sexo": "M",
  "meta_calorica": 2000.0
}
```

**Response (Sucesso):**
```json
{
  "success": true,
  "message": "Usuário cadastrado com sucesso"
}
```

**Response (Erro):**
```json
{
  "success": false,
  "error": {
    "code": "EMAIL_ALREADY_EXISTS",
    "message": "Este email já está cadastrado"
  }
}
```

### 2. Obter Perfil do Usuário
**GET** `/usuarios/perfil?id={idusuario}`

**Response:**
```json
{
  "success": true,
  "data": {
    "id_usuario": 1,
    "nome": "João Silva",
    "email": "joao@email.com",
    "peso": 75.5,
    "altura": 1.75,
    "idade": 30,
    "sexo": "M",
    "meta_calorica": 2000.0,
    "tipo_usuario": "USUARIO",
    "ativo": true,
    "data_criacao": "2024-01-15T10:30:00Z"
  }
}
```

### 3. Atualizar Perfil do Usuário
**PUT** `/usuarios/perfil/id={idusuario}`

**Request Body:**
```json
{
  "nome": "João Silva Santos",
  "peso": 74.0,
  "altura": 1.75,
  "idade": 31,
  "meta_calorica": 1800.0
}
```

**Response:**
```json
{
  "success": true,
  "message": "Perfil atualizado com sucesso"
}
```

---

## Alimentos

### 1. Pesquisar Alimentos (Aprovados)
**GET** `/alimentos/pesquisar?nome={nome}`

**Response:**
```json
{
  "success": true,
  "data": {
    "alimentos": [
      {
        "id_tabela": 1,
        "nome_alimento": "Arroz branco cozido",
        "calorias": 130.0,
        "carboidratos": 28.0,
        "proteinas": 2.7,
        "gorduras": 0.3,
        "status_aprovacao": "APROVADO",
        "data_criacao": "2024-01-15T10:30:00Z"
      },
      {
        "id_tabela": 2,
        "nome_alimento": "Feijão preto cozido",
        "calorias": 77.0,
        "carboidratos": 14.0,
        "proteinas": 4.5,
        "gorduras": 0.5,
        "status_aprovacao": "APROVADO",
        "data_criacao": "2024-01-15T10:30:00Z"
      }
    ]
  }
}
```

### 2. Obter Detalhes de um Alimento
**GET** `/alimentos/{id}`

**Response:**
```json
{
  "success": true,
  "data": {
    "id_tabela": 1,
    "nome_alimento": "Arroz branco cozido",
    "calorias": 130.0,
    "carboidratos": 28.0,
    "proteinas": 2.7,
    "gorduras": 0.3,
    "status_aprovacao": "APROVADO",
    "data_criacao": "2024-01-15T10:30:00Z"
  }
}
```

### 3. Solicitar Cadastro de Novo Alimento
**POST** `/alimentos/solicitar`


**Request Body:**
```json
{
  "nome_alimento": "Quinoa cozida",
  "calorias": 120.0,
  "carboidratos": 22.0,
  "proteinas": 4.4,
  "gorduras": 1.9
}
```

**Response:**
```json
{
  "success": true,
  "message": "Solicitação de cadastro enviada para aprovação"
}
```

### 4. Listar Minhas Solicitações
**GET** `/alimentos/minhas-solicitacoes?status={status}`

**Query Parameters:**
- `status` (opcional): PENDENTE, APROVADO, RECUSADO

**Response:**
```json
{
  "success": true,
  "data": {
    "solicitacoes": [
      {
        "id_tabela": 15,
        "nome_alimento": "Quinoa cozida",
        "calorias": 120.0,
        "carboidratos": 22.0,
        "proteinas": 4.4,
        "gorduras": 1.9,
        "status_aprovacao": "PENDENTE",
        "observacoes_admin": null,
        "data_criacao": "2024-01-15T10:30:00Z",
        "data_aprovacao": null
      }
    ]
  }
}
```

---

## APIs Administrativas

### 1. Listar Solicitações Pendentes (Admin)
**GET** `/admin/alimentos/pendentes`

**Response:**
```json
{
  "success": true,
  "data": {
    "solicitacoes": [
      {
        "id_tabela": 15,
        "nome_alimento": "Quinoa cozida",
        "calorias": 120.0,
        "carboidratos": 22.0,
        "proteinas": 4.4,
        "gorduras": 1.9,
        "id_usuario_solicitante": 1,
        "nome_solicitante": "João Silva",
        "email_solicitante": "joao@email.com",
        "status_aprovacao": "PENDENTE",
        "data_criacao": "2024-01-15T10:30:00Z"
      }
    ]
  }
}
```

### 2. Aprovar/Recusar Solicitação (Admin)
**PUT** `/admin/alimentos/{id}/status`

**Headers:**
```
Authorization: Bearer {admin_token}
```

**Request Body:**
```json
{
  "status_aprovacao": "APROVADO", // ou "RECUSADO"
  "observacoes_admin": "Alimento aprovado conforme tabela TACO"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id_tabela": 15,
    "nome_alimento": "Quinoa cozida",
    "status_aprovacao": "APROVADO",
    "observacoes_admin": "Alimento aprovado conforme tabela TACO",
    "data_aprovacao": "2024-01-15T11:30:00Z",
    "id_admin_aprovador": 2
  },
  "message": "Status da solicitação atualizado com sucesso"
}
```

---

## 📊 Códigos de Erro Comuns

| Código | Descrição |
|--------|-----------|
| `INVALID_CREDENTIALS` | Email ou senha incorretos |
| `EMAIL_ALREADY_EXISTS` | Email já cadastrado |
| `USER_NOT_FOUND` | Usuário não encontrado |
| `ACCESS_DENIED` | Acesso negado |
| `ALIMENTO_NOT_FOUND` | Alimento não encontrado |

---
