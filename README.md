# Task Manager with JavaFX

Este é um projeto JavaFX estruturado com Maven para gerenciamento de tarefas. O projeto utiliza variáveis de ambiente para configuração segura de banco de dados e a biblioteca ControlsFX para componentes visuais avançados.

---

## 🛠️ Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:
* **Java SDK 21 (LTS)**
* **Maven 3.8.8** ou superior
* **JavaFX SDK 21** (Instruções de download abaixo)
* **MySQL Server** (ou acesso ao banco indicado no `.env`)

---

## ⚙️ 1. Configuração do Arquivo de Variáveis de Ambiente (`.env`)

Para que a aplicação se conecte corretamente ao banco de dados, você precisa criar um arquivo chamado `.env` na **raiz do projeto**.

Abra o terminal na pasta raiz do projeto e execute o comando correspondente ao seu sistema operacional para gerar o arquivo rapidamente:

### Linux / macOS
```bash
cat <<EOF > .env
DB_URL=jdbc:mysql://localhost:3306/capacita_directory
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
EOF
```

### Windows (Prompt de Comando - CMD)
```
(
echo DB_URL=jdbc:mysql://localhost:3306/capacita_directory
echo DB_USER=seu_usuario
echo DB_PASSWORD=sua_senha
) > .env
```

### Windows (PowerShell)
```
@'
DB_URL=jdbc:mysql://localhost:3306/capacita_directory
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
'@ | Out-File -Encoding UTF8 .env
```

*Nota: Após criar o arquivo, lembre-se de substituir seu_usuario e sua_senha pelas suas credenciais locais do MySQL.*

---

## 🚀 2. Como Baixar e Configurar o JavaFX 21

Como o Java moderno não vem com o JavaFX integrado, você precisa baixar o SDK correspondente ao seu sistema operacional:

1. Acesse o site oficial do OpenJFX: [gluonhq.com/products/javafx](https://gluonhq.com/products/javafx/)
2. Baixe a versão JavaFX 21 (LTS) compatível com a sua arquitetura (x64 ou AArch64/M1/M2) e escolha o tipo SDK.
3. Extraia o arquivo .zip ou .tar.gz em um diretório de sua preferência (ex: `/opt/javafx-sdk-21` no Linux ou `C:\javafx-sdk-21` no Windows).

---

## 📦 3. Dependência Adicional: ControlsFX

Como visto no arquivo `module-info.java`, o projeto requer o módulo `org.controlsfx.controls`. Sendo um projeto gerenciado por Maven, a biblioteca será baixada automaticamente assim que o projeto for importado e indexado na sua IDE preferida.

Veja abaixo como garantir que o ambiente reconheça a dependência em cada IDE:

### 🔹 IntelliJ IDEA
1. Abra o IntelliJ e selecione Open para abrir a pasta raiz do projeto.
2. O IntelliJ detectará o arquivo `pom.xml`. Clique em Open as Project.
3. Caso a indexação não comece sozinha, clique com o botão direito na raiz do projeto, vá em Maven -> Reload Project.
4. Certifique-se de que a JDK do projeto está apontada para a versão 21 em *File > Project Structure > Project*.

### 🔹 VS Code
1. Certifique-se de ter o pacote de extensões **Extension Pack for Java** instalado.
2. Abra a pasta raiz do projeto no VS Code.
3. No canto inferior direito, o Java Language Server começará a importar o projeto Maven.
4. Se necessário, abra o painel do Maven na barra lateral esquerda, clique com o botão direito no projeto e selecione Reload.

### 🔹 Eclipse
1. Vá em *File > Import...*
2. Selecione *Maven > Existing Maven Projects* e clique em Next.
3. Navegue até a pasta raiz do projeto (onde está o `pom.xml`) e clique em Finish.
4. O Eclipse baixará o ControlsFX automaticamente. Se der erro de compilação, clique com o botão direito no projeto -> *Maven -> Update Project...* e marque *Force Update of Snapshots/Releases*.

### 🔹 NetBeans
1. Vá em *File > Open Project*.
2. O NetBeans reconhecerá o ícone do Maven na pasta do projeto automaticamente. Selecione-o e clique em Open Project.
3. O download das dependências começará em segundo plano. Você pode forçar clicando com o botão direito no projeto e selecionando Clean and Build.

---

## 🏃 Como Executar a Aplicação

Com o `.env` configurado e as dependências baixadas, execute o seguinte comando na raiz do projeto para iniciar a aplicação via Maven:

```bash
mvn clean javafx:run
```
