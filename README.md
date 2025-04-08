# 🛍️ Sistema de Gestão de Produtos e Vendas

Um sistema completo para gerenciamento de estoque e vendas com JavaFX e armazenamento em CSV.

## 📥 Instalação

1. **Clone o repositório**:

2. **Execute a classe Application ou o arquivo Maven**

Tela de Gestão de Produtos: 
![image](https://github.com/user-attachments/assets/3a6f779a-cbc0-46d0-af63-bb01da0051aa)
Tela de Vendas:
![image](https://github.com/user-attachments/assets/3b092f78-4d75-438b-8d3b-11193db95d0b)

⚙️ Funcionalidades
🔧 Produtos
Adicionar - Cadastra novos produtos

Editar - Altera produtos existentes

Remover - Exclui produtos do sistema

Buscar - Localiza por código

💰 Vendas
Add Item - Insere produtos na venda

Editar - Modifica itens da venda

Calcular - Computa troco

Finalizar - Conclui venda e atualiza estoque

3. **Estrutura do Arquivo**

   📦gestao-produtos
┣ 📂src
┃ ┣ 📂main
┃ ┃ ┣ 📂java
┃ ┃ ┃ ┣ 📂com/example/sla
┃ ┃ ┃ ┃ ┣ 📂Controller
┃ ┃ ┃ ┃ ┃ ┣ 📜MainController.java
┃ ┃ ┃ ┃ ┃ ┣ 📜VendaController.java
┃ ┃ ┃ ┃ ┣ 📂Entity
┃ ┃ ┃ ┃ ┃ ┣ 📜Produto.java
┃ ┃ ┃ ┃ ┣ 📂util
┃ ┃ ┃ ┃ ┃ ┣ 📜CSVUtil.java
┃ ┃ ┣ 📂resources
┃ ┃ ┃ ┣ 📂com/example/sla
┃ ┃ ┃ ┃ ┣ 📜main_view.fxml
┃ ┃ ┃ ┃ ┣ 📜venda_view.fxml
┣ 📜pom.xml
┣ 📜produtos.csv
┣ 📜README.md

