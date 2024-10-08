# TBGA com OpenGL em JAVA

Este projeto é uma aplicação gráfica em Java utilizando OpenGL para trabalho de computação gráfica.

## Estrutura do Projeto

- **Camera.java**: Gerencia a movimentação e rotação da câmera no ambiente 3D.
- **Main.java**: Classe principal que inicializa o contexto OpenGL e controla o loop principal de renderização.
- **ModelLoader.java**: Responsável por carregar modelos 3D para a cena.
- **VAO.java**: Gerencia os VAOs que armazenam os dados dos vértices para o pipeline gráfico.
- **Shader.java**: Responsável por carregar e compilar os shaders do pipeline gráfico.
- **fragment_shader.glsl** e **vertex_shader.glsl**: Arquivos de código-fonte dos shaders do pipeline gráfico.
- **vertex_shader.glsl** e **fragment_shader.glsl**: Arquivos de código-fonte dos shaders do pipeline gráfico.

## Pré-requisitos

- Java 17 ou superior
- GLFW (Gerenciador de janelas)
- LWJGL (Biblioteca Java para integração com OpenGL)
- Assimp (Modelo 3D)
- vecmath (Manipulação de vetores)
 
### Bibliotecas Utilizadas

- [GLFW](https://www.glfw.org/)
- [GLAD](https://glad.dav1d.de/)
- [LWJGL](https://www.lwjgl.org/) - Biblioteca Java para integração com OpenGL

## Instalação

1. Clone o repositório
2. Instale as dependências
3. Compile o projeto
4. Execute o projeto

## Funcionalidades

- **Movimentação da Câmera**: Use o teclado para mover a câmera pela cena 3D e o mouse para controlar a rotação.
- **Carregamento de Modelos 3D**: Carrega modelos em formato OBJ e os renderiza na cena.
- **VAO Management**: Utiliza VAOs para armazenar e gerenciar dados de vértices.